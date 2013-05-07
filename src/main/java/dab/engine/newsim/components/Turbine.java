/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.TurbineView;
import dab.engine.newsim.utils.BlockedHydroState;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.Matter;

/**
 *
 * @author eduard
 */
public class Turbine extends FailableComponent implements TurbineView {
    
    @JsonProperty
    double totalEnergyGenerated;
    @JsonProperty
    Steam receivedLastStep;
    
    public Turbine(String name) {
        super(name);
        totalEnergyGenerated = 0;
        receivedLastStep = new Steam(0, 0);
    }
    
    private double convertReceivedSteamToEnergy() {
        return receivedLastStep.getMass() * 100;
    }
    
    public void step() {
       //System.out.println(receivedLastStep.getMass());
       totalEnergyGenerated += convertReceivedSteamToEnergy();
       receivedLastStep.remove(receivedLastStep.getParticleNr());
    }
    
    @Override
    protected HydraulicState getHydroState() {
        if (hasFailed() || outputComponent.getHydroState() instanceof BlockedHydroState) {
            return new BlockedHydroState();
        } else {
            return outputComponent.getHydroState();
        }
    }

    @Override
    protected void receiveMatter(Matter m) {
        if (hasFailed()) throw new RuntimeException("turbine failed, shouldn't receive any steam");
        receivedLastStep.add((Steam)m);
        // even maybe remove some of the actual energy?
        outputComponent.receiveMatter(m);
    }

    @Override
    public double outputPower() {
        return totalEnergyGenerated;
    }

    @Override
    public void reducePower( double gunshotReduction) {
        totalEnergyGenerated -= gunshotReduction;
    }

}
