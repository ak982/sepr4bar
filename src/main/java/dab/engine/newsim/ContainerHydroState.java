/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class ContainerHydroState extends HydraulicState {
    double area;
    public ContainerHydroState(double pressure, double compressibleVolume, double area) {
        super(pressure, compressibleVolume);
        this.area = area;
    }
    
    public double getArea() {
        return area;
    }
}
