/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.RadioactiveMatter;
import dab.engine.newsim.utils.Ratio;
import dab.engine.newsim.utils.Water;
import dab.engine.newsim.utils.Kilograms;
import dab.engine.simulator.GameOverException;
import dab.engine.utilities.Percentage;

/**
 * used in power generation but also keep stuff like temperature. can cause a
 * meltdown!
 *
 * @author eduard
 */
public class ReactorCore {

    protected static final double MIN_POWER =  10000;
    protected static final double MAX_POWER = 500000;
    // transfer ease is just how much of the rod's temperature is going to DROP
    // every second, if the rods are fully submersed, their temperature will be an averaged with that of water's
    //protected static final double MAXIMUM_TRANSFER_EASE_PER_TICK = 0.5 / Constants.TICKS_PER_SECOND;
    protected static final double COOLING_SPEED_PER_TICK = 3.0 / Constants.TICKS_PER_SECOND;
    protected static final double MELTING_TEMPERATURE = 700;
    
    
    @JsonProperty
    private double controlRodPosition;
    
    @JsonProperty
    private RadioactiveMatter fuelRods;

    public ReactorCore() {
        controlRodPosition = 0;
        fuelRods = new RadioactiveMatter(new Kilograms(1000));
    }

    public double getEnergyPerTick(Ratio submersed) {
        return submersed.getValue() * (MIN_POWER + (MAX_POWER - MIN_POWER) * controlRodPosition) / Constants.TICKS_PER_SECOND;
    }

    // assume specific energy of 2kj/kgK, calculate temperature increase
    // allow cooling down of submersed part
    // calculate rodTemperature as weighted average between submersed and nonsubmersed
    public void step(Ratio submersed, Water water) throws GameOverException {

        // any exposed fuelRods are heated
        fuelRods.addEnergy(getEnergyPerTick(submersed.getOppositeRatio()));

        // submersed fuelRods are cooled down (by the water surrounding them)
        fuelRods.equilibrateTemperature(COOLING_SPEED_PER_TICK * submersed.getValue(), water.getTemperature());
        
        if (fuelRods.getTemperature() > MELTING_TEMPERATURE) {
            throw new GameOverException("core meltdown");
        }

    }

    public void setRodPosition(Ratio pos) {
        controlRodPosition = pos.getValue();
    }

    public double getRodPosition() {
        return controlRodPosition;
    }
    
    public double getTemperature() {
        return fuelRods.getTemperature();
    }
}
