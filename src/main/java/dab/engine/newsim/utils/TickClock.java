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
    private double timeUntilNextEvent;
    
    @JsonProperty
    private double lastInterval;
    
    public TickClock(double initialTimer) {
        timeUntilNextEvent = initialTimer;
        lastInterval = timeUntilNextEvent;
    }
    
    /**
     * Notify the clock that a tick has occurred.
     */
    public void tick() {
        timeUntilNextEvent = Math.max(0, timeUntilNextEvent - SECONDS_PER_TICK);
    }
    
    public double getRemainingTime() {
        return timeUntilNextEvent;
    }
    
    public void resetRemainingTime(double time) {
        lastInterval = time;
        timeUntilNextEvent = time;
    }
    
    public double getLastInterval() {
        return lastInterval;
    }
}
