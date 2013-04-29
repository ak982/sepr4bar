/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.CannotRepairException;
import dab.engine.simulator.views.FailableComponentView;

/**
 *
 * @author eduard
 */
public abstract class Component implements FailableComponentView{
    InputPort  inputPort;
    OutputPort outputPort;
    
    Component() {
        inputPort = new InputPort(this);
        outputPort = new OutputPort();
    }
    
    public InputPort getInputPort() {
        return inputPort;
    }
    
    public OutputPort getOutputPort() {
        return outputPort;
    }
    
    public boolean hasFailed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void fixDamage() throws CannotRepairException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getDamage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    protected abstract HydraulicState getHydroState();
}
