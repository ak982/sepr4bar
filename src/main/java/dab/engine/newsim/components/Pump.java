/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.PumpView;
import dab.engine.newsim.utils.BlockedHydroState;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.ContainerHydroState;
import dab.engine.newsim.utils.Matter;

/**
 *
 * @author eduard
 */
public class Pump extends FailableComponent implements PumpView {
    @JsonProperty
    private double power;
    
    @JsonProperty
    private boolean status;
    
    public Pump(String name, double power) {
        super(name);
        this.power = power;
    }
    
    public double getPower() {
        if (hasFailed()) {
            return 0;
        } else {
            return power;
        }
    }
    
    public void setPower(double power) {
        this.power = power;
    }
    
    @Override
    protected HydraulicState getHydroState() {
        if (getStatus() == false || getOutputComponent().getHydroState() instanceof BlockedHydroState) {
            return new BlockedHydroState();
        } else {
            ContainerHydroState chs = (ContainerHydroState) getOutputComponent().getHydroState();
            return new ContainerHydroState(chs.getPressure() - getPower(), chs.getCompressibleVolume(), chs.getArea());
        }
    }

    @Override
    protected void receiveMatter(Matter m) {
        outputComponent.receiveMatter(m);
    }

    @Override
    public void setStatus(boolean newStatus) {
        this.status = newStatus;
    }

    @Override
    public boolean getStatus() {
        return this.status;
    }
    
    
}
