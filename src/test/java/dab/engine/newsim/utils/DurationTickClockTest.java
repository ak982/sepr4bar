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
public class DurationTickClockTest {
    
    DurationTickClock clock;
    private double delta = 0.0001;
    
    public DurationTickClockTest() {
        clock = new DurationTickClock(10, 5);
    }
    
    private void passTime(double seconds) {
        for (int i = 0; i < Constants.TICKS_PER_SECOND * seconds; ++i) {
            clock.tick();
        }
    }
    
    @Before
    public void setup() {
        clock = new DurationTickClock(10, 5);
    }

    @Test
    public void testTick() {
        clock = new DurationTickClock(10, 5);
        passTime(1);
        assertEquals(4, clock.getTimeLeftActive(), delta);
        passTime(5);
        assertEquals(0, clock.getTimeLeftActive(), delta);
    }

    @Test
    public void testResetTimeLeftActive() {
        
        clock.resetTimeLeftActive(11);
        assertEquals(11, clock.getTimeLeftActive(), delta);
        passTime(10);
        assertEquals(1, clock.getTimeLeftActive(), delta);
    }

    @Test
    public void testGetLastActiveTime() {
        assertEquals(5, clock.getLastActiveTime(), delta);
        passTime(20);
        assertEquals(5, clock.getLastActiveTime(), delta);
        clock.resetTimeLeftActive(2);
        assertEquals(2, clock.getLastActiveTime(), delta);
        passTime(10);
        assertEquals(2, clock.getLastActiveTime(), delta);
        
    }
}