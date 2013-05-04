/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.UserCommands;

/**
 *
 * @author eduard
 */
public class DualPlayerFailureModel extends FailureModel {
    
    private final static int MAX_DAMAGE_DUAL = 1;    
    private final static int DAMAGE_INCREASE_DUAL = 2;
    
    //in two player mode failures have about 3 times less chance to occur randomly
    private final static double DEFAULT_FAIL_CHANCE = 1 / 1500;
    
    public DualPlayerFailureModel(PowerPlant plant) {
        super(plant);
        setDamagesToComponents(MAX_DAMAGE_DUAL, DAMAGE_INCREASE_DUAL);
    }
    
    @Override
    protected double getHardwareFailChance() {
        return getDifficultyModifier() * DEFAULT_FAIL_CHANCE;
    }

    @Override
    public boolean softFailCheck(UserCommands targetCommand, double targetParameter) {
        //Check if the bunny has initiated a SF from Bunnys' environment
           /*
            if(environment.getSoftwareFailure) {
                return false;
            } else {
                
            }
            */
            return true;
    }
}
