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
    protected double area, height;
    
    public Container(Water water, Steam steam, double area, double height) {
        this.water  = water;
        this.steam  = steam;
        this.area   = area;
        this.height = height;
    }
    
    public double getPressure() {
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
     *
     * FIXME: using volume as the height (assumes that the area is 1 m^2).
     * @return the pressure at the bottom of the container (steam pressure + hydrostatic pressure)
     */
    public double getBottomPressure() {
        return getPressure() + water.getHydrostaticPressure(area);
    }
    
    @Override
    public String toString() {
        return String.format("BP: %f\t%s%s", getBottomPressure(), steam.toString(), water.toString());
    }
}
