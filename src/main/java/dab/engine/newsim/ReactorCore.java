/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.utilities.Percentage;

/**
 * used in power generation but also keep stuff like temperature. can cause a
 * meltdown!
 *
 * @author eduard
 */
public class ReactorCore {

    private static final double MIN_POWER = 1000;
    private static final double MAX_POWER = 10000;
    // transfer ease is just how much of the rod's temperature is going to DROP
    // every second, if the rods are fully submersed, their temperature will be an averaged with that of water's
    private static final double MAXIMUM_TRANSFER_EASE_PER_TICK = 0.5 / Constants.TICKS_PER_SECOND;
    private double controlRodPosition;
    private RadioactiveMatter fuelRods;

    public ReactorCore() {
        controlRodPosition = 0;
        fuelRods = new RadioactiveMatter(new Kilograms(1));
    }

    public double getEnergyPerTick(Ratio submersed) {
        return submersed.getValue() * (MIN_POWER + (MAX_POWER - MIN_POWER) * controlRodPosition) / Constants.TICKS_PER_SECOND;
    }

    // assume specific energy of 2kj/kgK, calculate temperature increase
    // allow cooling down of submersed part
    // calculate rodTemperature as weighted average between submersed and nonsubmersed
    public void step(Ratio submersed, Water water) {
        Ratio exposed = new Ratio(submersed.getOppositeValue());
        // if rods aren't fully submerged then they cool down less
        double transferEase = submersed.getValue() * MAXIMUM_TRANSFER_EASE_PER_TICK;
        Ratio transferDifficulty = new Ratio(1 - transferEase);

        // any exposed fuelRods are heated
        fuelRods.addEnergy(getEnergyPerTick(exposed));

        // submersed fuelRods are cooled down (by the water surrounding them)
        fuelRods.equilibrateTemperature(transferDifficulty, water.getTemperature());

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