/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.PumpView;
import dab.engine.newsim.utils.BlockedHydroState;
import dab.engine.newsim.utils.Constants;
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
    
    protected Pump() {
        super();
    }
    
    public Pump(String name, double power) {
        super(name);
        this.power = power;
        this.status = true;
    }
    
    public double getPower() {
        if (hasFailed() || getStatus() == false) {
            return 0;
        } else {
            return power;
        }
    }
    
    public void setPower(double power) {
        this.power = power;
    }
    
    private double calculateApparentPressure(double pressure) {
        return pressure - Math.max((pressure - Constants.ATMOSPHERIC_PRESSURE) * getPower(), 0);
    }
    
    @Override
    protected HydraulicState getHydroState() {
        if (getOutputComponent().getHydroState() instanceof BlockedHydroState) {
            return new BlockedHydroState();
        } else {
            ContainerHydroState chs = (ContainerHydroState) getOutputComponent().getHydroState();
            return new ContainerHydroState(calculateApparentPressure(chs.getPressure()), chs.getCompressibleVolume(), chs.getArea());
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
