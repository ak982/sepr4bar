/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public abstract class Container extends Component  {
    protected Water water;
    protected Steam steam;
    protected double volume;
    
    public Container(Water water, Steam steam, double volume) {
        this.volume = volume;
        this.water = water;
        this.steam = steam;
    }
    
    public HydraulicState getHydroState() {
        return new HydraulicState(getPressure(), getCompressibleVolume(), getTemperature());
    }
    
    public double getPressure() {
        return steam.getPressure(getCompressibleVolume());
    }
    
    public double getCompressibleVolume() {
        return volume - water.getVolume();
    }
    
    public double getTemperature() {
        return steam.getTemperature();
    }
    
    protected double getEqualizedPressure(HydraulicState hydroValue) {
        double v1 = getCompressibleVolume(), v2 = hydroValue.getCompressibleVolume();
        double p1 = getPressure(), p2 = hydroValue.getPressure();
        return (v1 * p1 + v2 * p2) / (v1 + v2);
    }
    
}
