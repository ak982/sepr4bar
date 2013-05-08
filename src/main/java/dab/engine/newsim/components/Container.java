/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.ContainerView;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.Water;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public abstract class Container extends Component implements ContainerView  {
    
    @JsonProperty
    protected Water water;
    
    @JsonProperty
    protected Steam steam;
    
    @JsonProperty
    protected double area, height;
    
    protected Container() {
        super();
    }
    
    public Container(String name, Water water, Steam steam, double area, double height) {
        super(name);
        this.water  = water;
        this.steam  = steam;
        this.area   = area;
        this.height = height;
    }
    
    protected double getPressure() {
        return steam.getPressure(getCompressibleVolume());
    }
    
    public Water getWater() {
        return water;
    }
    
    public Steam getSteam() {
        return steam;
    }
    
    public double getTotalVolume() {
        return height * area;
    }
    
    public double getWaterLevelRatio() {
        return getWater().getVolume() / getTotalVolume();
    }
    
    public double getCompressibleVolume() {
        double vol = getTotalVolume() - water.getVolume();
        if (vol < 0)
            throw new RuntimeException("Can not have negative volumes");
        return vol;
    }
    
    public double getTemperature() {
        return steam.getTemperature();
    }
    
    /**
     * @return the pressure at the bottom of the container (steam pressure + hydrostatic pressure)
     */
    @Override
    public double getBottomPressure() {
        return getPressure() + water.getHydrostaticPressure(area);
    }
    
    @Override
    public String toString() {
        return String.format("P: %f\tBP: %f\tCV%%%f: \t%s%s", getPressure(), getBottomPressure(), getCompressibleVolume() / getTotalVolume(), steam.toString(), water.toString());
    }

    //<editor-fold desc="Implemented interfaces">
    @Override
    public Percentage waterLevel() {
        return new Percentage(getWaterLevelRatio() * 100);
    }

    @Override
    public Temperature temperature() {
        return new Temperature(getTemperature());
    }

    @Override
    public Pressure pressure() {
        return new Pressure(getPressure());
    }
    //</editor-fold>

}
