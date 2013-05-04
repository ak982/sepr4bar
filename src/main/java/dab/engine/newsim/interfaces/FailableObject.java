/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.interfaces;

import dab.engine.newsim.components.FailureController;

/**
 *
 * @author eduard
 */
public interface FailableObject {
    public FailureController getFailureController();
}
