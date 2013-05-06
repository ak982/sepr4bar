/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.FailableComponentView;
import dab.engine.newsim.interfaces.ValveView;
import dab.engine.newsim.utils.BlockedHydroState;
import dab.engine.newsim.utils.HydraulicState;
import dab.engine.newsim.utils.Matter;

/**
 * simplest component of them all
 * @author eduard
 */
public class Valve extends Component implements ValveView, FailableComponentView {

    @JsonProperty
    private boolean isOpen;
    
    public Valve(String name) {
        super(name);
        this.isOpen = true;
    }
    
    @Override
    protected HydraulicState getHydroState() {
        if (getOpen() == false) {
            return new BlockedHydroState();
        } else {
            return outputComponent.getHydroState();
        }
    }

    @Override
    protected void receiveMatter(Matter m) {
        if (getOpen() == false) {
            throw new RuntimeException("Valve is blocked can not receive any matter");
        } else {
            outputComponent.receiveMatter(m);
        }
    }

    @Override
    public boolean getOpen() {
        return isOpen;
    }

    @Override
    public void setOpen(boolean openStatus) {
        isOpen = openStatus;
    }

    @Override
    public boolean hasFailed() {
        return false;
    }

    @Override
    public void fixDamage() {
        ;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void fail(int dmg) {
        ;
    }
    
}
