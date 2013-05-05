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
public interface CondenserView extends FailableComponentView, ContainerView {
    public PumpView getCoolantPump();
    
}
