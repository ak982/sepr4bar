/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.Matter;

/**
 *
 * @author eduard
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public abstract class Component {
    
    @JsonProperty
    protected Component outputComponent = null;
    
    @JsonProperty
    protected String name;
    
    @JsonProperty
    protected boolean debugMode = false;
    
    protected Component(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
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
