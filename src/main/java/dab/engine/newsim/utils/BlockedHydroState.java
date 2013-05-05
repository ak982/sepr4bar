/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public class BlockedHydroState extends HydraulicState {
    public BlockedHydroState() {
        super(Double.POSITIVE_INFINITY, 0);
    }
    
}
