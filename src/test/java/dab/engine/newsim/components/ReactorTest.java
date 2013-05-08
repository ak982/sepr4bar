package dab.engine.newsim.components;

import dab.engine.simulator.GameOverException;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import dab.engine.utilities.Percentage;

/**
 *
 * @author bjw523
 */
public class ReactorTest {  
    @Test
    public void testQuenching() {
        Reactor reactor = new Reactor("reactor", 100, 100);
        reactor.quench();
        assertTrue(reactor.getHasBeenQuenched());
    }
    
    @Test
    public void testSetControlRods() {
        Reactor reactor = new Reactor("reactor", 100, 100);
        Percentage percentage = new Percentage(40);
        reactor.moveControlRods(percentage);
        assertEquals(percentage, reactor.getTargetRodPosition());
    }
    
    @Test
    public void testControlRodsMove() {
        Reactor reactor = new Reactor("reactor", 100, 100);
        Percentage percentage = new Percentage(40);
        reactor.moveControlRods(percentage);
        try {
        reactor.step();
        reactor.step();
        reactor.step();
        } catch (GameOverException e) {
            ;
        }
        assertTrue(reactor.controlRodPosition().points() > 0);
    }
}
