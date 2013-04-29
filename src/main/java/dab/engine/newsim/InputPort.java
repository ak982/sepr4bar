/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class InputPort extends Port {
    Component owner;
    
    public InputPort(Component c) {
        this.owner = c;
    }
    
    @Override
    public HydraulicState getHydroState() {
        return owner.getHydroState();
    }
}
