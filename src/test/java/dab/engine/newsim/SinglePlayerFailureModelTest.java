/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import org.junit.Before;
import org.junit.Test;
import dab.engine.newsim.PowerPlant;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.Difficulty;
import dab.engine.newsim.utils.OptionsHolder;
import dab.engine.simulator.GameOverException;
import static org.junit.Assert.*;

/**
 *
 * @author eduard
 */
public class SinglePlayerFailureModelTest {
    private double delta = 0.0001;
    SinglePlayerFailureModel failureModel;
    

    public SinglePlayerFailureModelTest() {
        failureModel = new SinglePlayerFailureModel(new PowerPlant());
    }
   
    private void passTime(double seconds) throws GameOverException {
        for (int i = 0; i < Constants.TICKS_PER_SECOND * seconds; ++i) {
            failureModel.step();
        }
    }
    
    @Before
    public void setUp() {
        //OptionsHolder.getInstance().setGodModeOn(true);
        failureModel = new SinglePlayerFailureModel(new PowerPlant());
        OptionsHolder.getInstance().setDifficulty(Difficulty.EASY);
        // so that we don't get game over exceptions
        OptionsHolder.getInstance().setGodModeOn(true);
        
    }

    @Test
    public void testTimerDecreaseValue() {
        OptionsHolder.getInstance().setDifficulty(Difficulty.EASY);
        assertEquals(failureModel.getTimerDecrease(), SinglePlayerFailureModel.DEFAULT_TIMER_DECREASE, delta);
        
        OptionsHolder.getInstance().setDifficulty(Difficulty.MEDIUIM);
        assertEquals(failureModel.getTimerDecrease(), SinglePlayerFailureModel.DEFAULT_TIMER_DECREASE - 0.1, delta);
        
        OptionsHolder.getInstance().setDifficulty(Difficulty.HARD);
        assertEquals(failureModel.getTimerDecrease(), SinglePlayerFailureModel.DEFAULT_TIMER_DECREASE - 0.2, delta);
    }
    
    @Test
    public void testHardWareFailureTimes() throws GameOverException {
        assertEquals(SinglePlayerFailureModel.INITIAL_HARDWARE_FAIL_INTERVAL, failureModel.hardwareTimer.getRemainingTime(), delta);
        passTime(SinglePlayerFailureModel.INITIAL_HARDWARE_FAIL_INTERVAL);
        assertTrue(failureModel.hardwareTimer.getRemainingTime() > 0);
        assertTrue(failureModel.hardwareTimer.getRemainingTime() < SinglePlayerFailureModel.INITIAL_HARDWARE_FAIL_INTERVAL);
        assertEquals(SinglePlayerFailureModel.INITIAL_HARDWARE_FAIL_INTERVAL * failureModel.getTimerDecrease(), failureModel.hardwareTimer.getRemainingTime(), 1);
    }
    
    @Test
    public void testSoftwareFailuers() throws GameOverException {
        assertEquals(SinglePlayerFailureModel.DEFAULT_SOFTWARE_FAIL_INTERVAL, failureModel.softwareTimer.getRemainingTime(), delta);
        assertFalse(failureModel.softwareTimer.isActive());
        passTime(SinglePlayerFailureModel.DEFAULT_SOFTWARE_FAIL_INTERVAL);
        assertTrue(failureModel.softwareTimer.isActive());
        passTime(failureModel.softwareTimer.getTimeLeftActive());
        assertFalse(failureModel.softwareTimer.isActive());
    }
    
    
}
