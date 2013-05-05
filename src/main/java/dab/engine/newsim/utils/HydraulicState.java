/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author eduard
 */
public class HydraulicState {
    private double pressure, volume;
    
    public HydraulicState(double pressure, double volume) {
        this.pressure = pressure;
        this.volume   = volume;
    }

    
    public double getPressure() {
        return pressure;
    }
    
    public double getCompressibleVolume() {
        return volume;
    }
}
