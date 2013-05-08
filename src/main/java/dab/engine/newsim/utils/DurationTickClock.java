/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This class implements the concept of having an on-going event
 * @author eduard
 */
public class DurationTickClock extends TickClock {
    @JsonProperty
    private double timeLeftActive;
    
    @JsonProperty
    private double lastActiveTime;
    
    public DurationTickClock(double initialTimer, double initialDuration) {
        super(initialTimer);
        this.timeLeftActive = initialDuration;
        this.lastActiveTime = initialDuration;
    }
    
    @Override
    public void tick() {
        super.tick();
        timeLeftActive = Math.max(0, timeLeftActive - SECONDS_PER_TICK);
    }
    
    public double getTimeLeftActive() {
        return timeLeftActive;
    }
    
    public void resetTimeLeftActive(double timeLeftActive) {
        lastActiveTime = timeLeftActive;
        this.timeLeftActive = timeLeftActive;
    }
    
    public double getLastActiveTime() {
        return lastActiveTime;
    }
}
