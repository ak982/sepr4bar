/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.CondenserView;
import dab.engine.newsim.interfaces.FailableObject;
import dab.engine.newsim.interfaces.PumpView;
import dab.engine.newsim.utils.BlockedHydroState;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.Water;
import dab.engine.newsim.utils.Kilograms;
import dab.engine.newsim.utils.Matter;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public class Condenser extends Container implements FailableObject, CondenserView {

    private static final double INITIAL_WATER_RATIO = 0.3;
    
    @JsonProperty
    private HeatSink heatSink;
    @JsonProperty
    private FailureController failController;
    
    protected Condenser() {
        
    }
    
    public Condenser(String name, double volume, double area) {
        super(
                name,
                new Water(Constants.ROOM_TEMP, new Kilograms((volume * INITIAL_WATER_RATIO) * Constants.NORMAL_DENSITY_WATER)),
                new Steam(Constants.ROOM_TEMP, (int)((volume * (1 - INITIAL_WATER_RATIO)) * Constants.NORMAL_PARTICLES_PER_VOLUME_STEAM)),
                area,
                volume / area);
        heatSink = new HeatSink(name + " Cooling Pump");
        failController = new FailureController();
    }
    
    @Override
    public double getWaterMass() {
        return water.getMass();
    }
    
    public HeatSink getHeatSink() {
        return heatSink;
    }
    
    private void condenseSteam() {
            // if pressure is smaller than atmosferic one, then we don't condense anything,
            // otherwise we condense such that we reach the boiling point at that pressure
            if (getPressure()> Constants.ATMOSPHERIC_PRESSURE) {
                double boilingPoint = Water.getBoilingTemperature(getPressure());
                if (boilingPoint > steam.getTemperature()) { // remove steam such that it equalizes to the boiling point
                    double newSteamPressure = Math.max(Water.getBoilingPressure(steam.getTemperature()), Constants.ATMOSPHERIC_PRESSURE);
                    

                    int newQuantity = steam.getParticlesAtState(newSteamPressure, getCompressibleVolume());
                    int deltaQuantity = steam.getParticleNr() - newQuantity;
                    if (deltaQuantity < 0) {
                        System.out.println("BAD");
                    }
                    steam.remove(deltaQuantity);
                    getWater().add(new Water(steam.getTemperature(), deltaQuantity));
                }
            }        
    }
    
    private void equalizePressure() {
         /*
         * otherPres + (dm * g) / otherA = ourPres - (dm * g) / ourA
         * dm = deltaP / g * (1 / otherA + 1 / ourA)
         *    = dp * A1 * A2 / (g * (A1 + A2))
         *    = dp * areaFactor / g
         * 
         * send water until we have equalized, note that we do this, even if we have failed
         */
        for (int i = 0; i < 10; ++i) {
            HydraulicState hs = outputComponent.getHydroState();
            if (hs instanceof BlockedHydroState) { // we can't send anything
                break;
            } else {
                if (hs.getPressure() < getBottomPressure()) { // if our pressure is larger than the other one
                    Water deltaWater = new Water(getWater().getTemperature(), Math.min((int)(3 * getWater().getParticlesPerKilo()), getWater().getParticleNr()));
                    //System.out.println("C: Before: " + outputComponent.getHydroState().pressure() + " " + getBottomPressure());
                    send(deltaWater); // send 3 kg of water
                    getWater().remove(deltaWater.getParticleNr());
                    //System.out.println("C: After: " + outputComponent.getHydroState().pressure() + " " + getBottomPressure());
                } else {
                    // residual flow
                    /*Water deltaWater = new Water(getWater().getTemperature(), new Kilograms(Math.min(0.01, getWaterMass())));
                    send(deltaWater);
                    getWater().remove(deltaWater.getParticleNr());*/
                }
            }
        }       
    }
    
    public void step() {
        // update heatsink
        heatSink.step();

        if (!hasFailed()) {
            // use heatsink to cooldown stuff
            heatSink.coolSteam(steam);
            heatSink.coolWater(water);
            condenseSteam();
        }
        equalizePressure();
    }
    
    @Override
    protected void receiveMatter(Matter m) {
        steam.add((Steam)m);
    }

    @Override
    protected HydraulicState getHydroState() {
        return new HydraulicState(getPressure(), getCompressibleVolume());
    }
    
    @Override
    public String toString() {
        return "C: " + super.toString();
    }

    //<editor-fold desc="Just implemented interfaces, nothing fancy">
    @Override
    public FailureController getFailureController() {
        return failController;
    }

    @Override
    public PumpView getCoolantPump() {
        return heatSink;
    }

    @Override
    public boolean hasFailed() {
        return failController.hasFailed();
    }

    @Override
    public void fixDamage() {
        failController.fixDamage();
    }

    @Override
    public int getDamage() {
        return failController.getDamage();
    }
   
    @Override
    public void fail(int dmg) {
        failController.fail(dmg);
    }
    //</editor-fold>
}
