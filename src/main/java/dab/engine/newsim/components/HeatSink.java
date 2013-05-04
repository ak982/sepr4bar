/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import dab.engine.newsim.interfaces.FailableObject;
import dab.engine.newsim.interfaces.PumpView;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.Water;
import javax.naming.Name;

/**
 *
 * @author eduard
 */
public class HeatSink implements FailableObject, PumpView {
    private static final int STEAM_PARTICLES = 30000; // around 0.6 kg of steam
    private static final int WATER_PARTICLES = 120000;
    private static final double COOLING_EASE = 0.2 / Constants.TICKS_PER_SECOND;      // how much to change the temperature each step
    
    @JsonProperty
    private double steamTemp, waterTemp;
    
    @JsonProperty
    private boolean isRunning;
    
    @JsonProperty
    private FailureController failController;
    
    @JsonProperty
    private String name;
   
    public HeatSink(String name) {
        steamTemp = Constants.ROOM_TEMP;
        waterTemp = Constants.ROOM_TEMP;
        failController = new FailureController();
        isRunning = true;
        this.name = name;
       
    }
    
    // FIXME: magic number
    public void step() {
        if (hasFailed() || getStatus() == false) {
            return;
        } else {
            steamTemp = steamTemp * (1 - COOLING_EASE) + Constants.ROOM_TEMP * COOLING_EASE;
            waterTemp = waterTemp * (1 - COOLING_EASE) + Constants.ROOM_TEMP * COOLING_EASE;
        }
    }
    
    
    public void coolWater(Water toBeCooled) {
        toBeCooled.add(new Water(waterTemp, WATER_PARTICLES));
        toBeCooled.remove(WATER_PARTICLES);
    }
    
    public void coolSteam(Steam toBeCooled) {
        toBeCooled.add(new Steam(steamTemp, STEAM_PARTICLES));
        toBeCooled.remove(STEAM_PARTICLES);
    }
    
    //<editor-fold desc="Implemented interfaces">
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public FailureController getFailureController() {
        return failController;
    }

    @Override
    public void setStatus(boolean newStatus) {
        isRunning = newStatus;
    }

    @Override
    public boolean getStatus() {
        return isRunning;
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
    public void fail(int dmg) {
        failController.fail(dmg);
    }
    //</editor-fold>
}
