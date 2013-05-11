/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that keeps track of time (in seconds), used in failure model.
 * Measures time in seconds.
 * The client must make sure to reset the clock one it reaches 0
 * @author eduard
 */

public class TickClock {
    protected double SECONDS_PER_TICK = 1.0 / Constants.TICKS_PER_SECOND;
    
    @JsonProperty
    private int ticksUntilNextEvent;
    
    @JsonProperty
    private int lastInterval;
    
    protected TickClock() {
        
    }
    
    public TickClock(double initialTimer) {
        ticksUntilNextEvent = (int)(initialTimer * Constants.TICKS_PER_SECOND);
        lastInterval = ticksUntilNextEvent;
    }
    
    /**
     * Notify the clock that a tick has occurred.
     */
    public void tick() {
        ticksUntilNextEvent = Math.max(0, ticksUntilNextEvent - 1);
    }
    
    public double getRemainingTime() {
        return ticksUntilNextEvent * SECONDS_PER_TICK;
    }
    
    /**
     * 
     * @return if the remaining time is zero (the alarm clock is isRinging)
     */
    public boolean isRinging() {
        return ticksUntilNextEvent == 0;
    }
    
    public void resetRemainingTime(double time) {
        ticksUntilNextEvent = (int)(time * Constants.TICKS_PER_SECOND);
        lastInterval = ticksUntilNextEvent;
    }
    
    public double getLastInterval() {
        return lastInterval * SECONDS_PER_TICK;
    }
}
