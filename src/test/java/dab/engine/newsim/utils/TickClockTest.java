/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eduard
 */
public class TickClockTest {
    
    TickClock clock;
    private double delta = 0.00001;
    
    public TickClockTest() {
        clock = new TickClock(10);
    }
 
    private void passTime(double seconds) {
        for (int i = 0; i < Constants.TICKS_PER_SECOND * seconds; ++i) {
            clock.tick();
        }
    }
    
    @Before
    public void setUp() {
        clock = new TickClock(10);
    }

    @Test
    public void testTick() {
        assertEquals(10, clock.getRemainingTime(), delta);
        passTime(5);
        assertEquals(5, clock.getRemainingTime(), delta);
        passTime(6);
        assertEquals(0, clock.getRemainingTime(), delta);
    }

    @Test
    public void testResetRemainingTime() {
        clock.resetRemainingTime(5);
        assertEquals(5, clock.getRemainingTime(), delta);
        passTime(4);
        assertEquals(1, clock.getRemainingTime(), delta);
    }

    @Test
    public void testGetLastInterval() {
        assertEquals(10, clock.getLastInterval(), delta);
        passTime(20);
        assertEquals(10, clock.getLastInterval(), delta);
        clock.resetRemainingTime(5);
        assertEquals(5, clock.getLastInterval(), delta);
        passTime(20);
        assertEquals(5, clock.getLastInterval(), delta);
        
    }
}