/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.FailableComponentView;
import dab.engine.newsim.interfaces.FailableObject;

/**
 *
 * @author eduard
 */
public abstract class FailableComponent extends Component implements FailableObject, FailableComponentView {

    @JsonProperty
    private FailureController failController;
    
    public FailableComponent() {
        failController = new FailureController();
    }
    
    @Override
    public boolean hasFailed() {
        return failController.hasFailed();
    }

    @Override
    public void fixDamage() {
        failController.fixDamage();
    }

    @Override
    public int getDamage() {
        return failController.getDamage();
    }

    @Override
    public void fail(int i) {
        failController.fail(i);
    }
    
    @Override
    public FailureController getFailureController() {
        return failController;
    }
    
}
