/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.interfaces;

import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public interface ReactorView extends ContainerView, FailableComponentView {
    
    public void moveControlRods(Percentage extracted);
    public Percentage controlRodPosition();
    public Percentage targetRodPosition();
    //public boolean discardingWater();
    
    public Temperature coreTemperature();
    public void quench();
   
}
