/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author eduard
 */
public class HydraulicState {
    private double pressure, volume, temperature;
    
    public HydraulicState(double pressure, double volume) {
        this(pressure, volume, Double.NaN);
    }
    
    public HydraulicState(double pressure, double volume, double temperature) {
        this.pressure = pressure;
        this.volume   = volume;
        this.temperature = temperature;
    }
    
    public double getPressure() {
        return pressure;
    }
    
    public double getCompressibleVolume() {
        return volume;
    }
    
    public double getTemperature() {
        if (Double.isNaN(temperature)) {
            throw new RuntimeException(new OperationNotSupportedException());
        } else {
            return temperature;
        }
    }
}
