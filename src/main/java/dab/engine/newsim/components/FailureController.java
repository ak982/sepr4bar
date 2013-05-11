/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.simulator.CannotRepairException;
import dab.engine.utilities.Percentage;

/**
 *
 * @author eduard
 */
public class FailureController {
    private static final int THE_BIGGEST_DAMAGE = 20;
    private static final int THE_MAX_DAMAGE = 15;
    
    @JsonProperty
    private int damage;
    
    @JsonProperty
    private double maxDamage;  //this should be different for two player mode
    
    @JsonProperty
    private double damageIncrease;

    /**
     * Constructor for the FailureController. Sets default percentage to 0 and a
     * normal FailureState
     */
    public FailureController() {
        //Initialize to a normal state
        damage = 0;
    }

    public void setDamageValues(int maxDamage, int damageIncrease) {
        this.maxDamage = maxDamage;
        this.damageIncrease = damageIncrease;
    }

    public int getDamage() {
        return damage;
    }

    public void fixDamage() {
        if (hasFailed()) {
            damage--;
            if (damage <= 0) {
                if(maxDamage<THE_MAX_DAMAGE) {
                    maxDamage += damageIncrease;
                }
                damage = 0;
            }
        }
    }
    
    public void repair() {
        damage = 0;
    }

    /**
     * @return hasFailed boolean
     */
    public boolean hasFailed() {
        return (damage > 0);
    }

    /**
     * set hasFailed to true
     */
    public void fail() {
        if (!hasFailed())
            fail(0);
    }

    public void fail(int amount) {
        damage = (int) (damage + amount + maxDamage);
        if(damage>=THE_BIGGEST_DAMAGE) {
            damage = THE_BIGGEST_DAMAGE;
        }
        System.out.println("damage" + damage);
    }    
}
