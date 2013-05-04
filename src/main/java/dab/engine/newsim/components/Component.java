/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.Matter;

/**
 *
 * @author eduard
 */
public abstract class Component {
    
    @JsonProperty
    protected Component outputComponent = null;
    
    @JsonProperty
    protected boolean debugMode = false;
    
    public Component getOutputComponent() {
        return outputComponent;
    }
    
    public void setOutputComponent(Component c) {
        this.outputComponent = c;
    }
    
    public boolean toggleDebugMode() {
        debugMode = !debugMode;
        return debugMode;
    }
    
    protected abstract HydraulicState getHydroState();
    
    protected abstract void receiveMatter(Matter m);
    
    protected void send(Matter m) {
        if (outputComponent == null) {
            throw new RuntimeException("Must first set output component before sending matter.");
        } else {
            outputComponent.receiveMatter(m);
        }
    }
}
