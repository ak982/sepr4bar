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
    private int timeLeftActive;
    
    @JsonProperty
    private int lastActiveTime;
    
    public DurationTickClock(double initialTimer, double initialDuration) {
        super(initialTimer);
        this.timeLeftActive = (int)(initialDuration * Constants.TICKS_PER_SECOND);
        this.lastActiveTime = timeLeftActive;
    }
    
    @Override
    public void tick() {
        super.tick();
        timeLeftActive = Math.max(0, timeLeftActive - 1);
    }
    
    public double getTimeLeftActive() {
        return timeLeftActive * SECONDS_PER_TICK;
    }
    
    public void resetTimeLeftActive(double timeLeftActive) {
        this.timeLeftActive = (int)(timeLeftActive * Constants.TICKS_PER_SECOND);
        this.lastActiveTime = this.timeLeftActive;
    }
    
    public double getLastActiveTime() {
        return lastActiveTime * SECONDS_PER_TICK;
    }
    
    public boolean isActive() {
        return timeLeftActive != 0;
    }
}
