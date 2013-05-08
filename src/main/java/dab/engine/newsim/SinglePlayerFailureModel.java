/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dab.engine.newsim.interfaces.FailableObject;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.DurationTickClock;
import dab.engine.newsim.utils.RandomBuffer;
import dab.engine.newsim.utils.TickClock;
import dab.engine.simulator.FailMode;
import dab.engine.simulator.GameOverException;
import dab.engine.simulator.SoftFailReport;
import dab.engine.simulator.UserCommands;

/**
 *
 * @author eduard
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SinglePlayerFailureModel extends FailureModel {

    protected final static int MAX_DAMAGE_SINGLE = 5;
    protected final static int DAMAGE_INCREASE_SINGLE = 1;
    protected final static int INITIAL_HARDWARE_FAIL_INTERVAL = 15;
    protected final static int MINIMUM_HARDWARE_FAIL_INTERVAL = 4;
    protected final static double DEFAULT_TIMER_DECREASE = 0.9;
    protected final static double DEFAULT_SOFTWARE_FAIL_INTERVAL = 30;
    protected final static double MINIMUM_SOFTWARE_FAIL_INTERVAL = 10;
    protected final static double DEFAULT_SOFTWARE_FAIL_DURATION = 5;
    //A component will have a 1 in 50 chance of failing (per second)
    //private final static double DEFAULT_FAIL_CHANCE = 1.0 / 25.0;
    
    @JsonProperty
    TickClock hardwareTimer;
    
    @JsonProperty
    DurationTickClock softwareTimer;
    
    @JsonProperty
    private FailMode softwareFailType = FailMode.WORKING;

    // loaded manually
    private RandomBuffer<FailableObject> failList = null;
    
    private SinglePlayerFailureModel() {
        super();
    }
    
    public SinglePlayerFailureModel(PowerPlant plant) {
        super(plant);
        setDamagesToComponents(MAX_DAMAGE_SINGLE, DAMAGE_INCREASE_SINGLE);
        failList = new RandomBuffer<>(plant.getFailableComponents());
        hardwareTimer = new TickClock(INITIAL_HARDWARE_FAIL_INTERVAL);
        softwareTimer = new DurationTickClock(DEFAULT_SOFTWARE_FAIL_INTERVAL, DEFAULT_SOFTWARE_FAIL_DURATION);
    }

    public void afterLoad() {
        powerPlant.resetConnections();
        failList = new RandomBuffer<>(powerPlant.getFailableComponents());
        
    }
    
    protected double getTimerDecrease() {
        return 0.9 - (getDifficultyModifier() - 1) / 10;
    }
    
    @Override
    public void step() throws GameOverException {
        final double SECONDS_PER_TICK = 1.0 / Constants.TICKS_PER_SECOND;
        super.step();
        hardwareTimer.tick();
        softwareTimer.tick();
        
        if (hardwareTimer.getRemainingTime() <= 0) {
            // fail next component in line
            failList.get().getFailureController().fail();
            hardwareTimer.resetRemainingTime(hardwareTimer.getLastInterval() * getTimerDecrease());
            if (hardwareTimer.getRemainingTime() < MINIMUM_HARDWARE_FAIL_INTERVAL) {
                hardwareTimer.resetRemainingTime(MINIMUM_HARDWARE_FAIL_INTERVAL);
            }
        }
        
        if (softwareTimer.getTimeLeftActive() <= 0) {
            softwareFailType = FailMode.WORKING;
        }
        
        if (softwareTimer.getRemainingTime() <= 0) {
            // generate software failure
            double roll = dice.rollDouble();
            if (roll < 0.8) {
                softwareFailType = FailMode.UNRESPONSIVE;
            } else {
                softwareFailType = FailMode.INCORRECT;
            }
            
            softwareTimer.resetTimeLeftActive(DEFAULT_SOFTWARE_FAIL_DURATION * getDifficultyModifier());
            softwareTimer.resetRemainingTime(softwareTimer.getLastInterval() * getTimerDecrease());
            
            if (softwareTimer.getRemainingTime() < MINIMUM_SOFTWARE_FAIL_INTERVAL) {
                softwareTimer.resetRemainingTime(MINIMUM_SOFTWARE_FAIL_INTERVAL);
            }           
        }
        
    }
    
    @Override
    public SoftFailReport generateSoftwareReport(UserCommands targetCommand, double targetParameter) {
        if (softwareFailType == FailMode.UNRESPONSIVE) {
            //Unresponive commands do nothing
            return new SoftFailReport(FailMode.UNRESPONSIVE, targetCommand, targetParameter);
        } else if (softwareFailType == FailMode.INCORRECT) {

            //Incorrect commands behave as another command
            UserCommands actualCommand = UserCommands.randomCommand();
            double actualParameter = generateFailedParameter(actualCommand);
            //doFailedCommand(actualCommand, actualParameter);
            return new SoftFailReport(FailMode.INCORRECT, targetCommand, targetParameter, actualCommand, actualParameter);


        } else {
            return new SoftFailReport(FailMode.WORKING, targetCommand, targetParameter);
        }
    }
    
    @Override
    protected FailMode getSoftwareFailureMode() {
        return softwareFailType;   
    }
    
}
