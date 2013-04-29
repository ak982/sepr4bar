/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.CannotRepairException;
import dab.engine.simulator.views.ReactorView;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public class Reactor extends Container {

    public static final double ROOM_TEMP = 300;
    
    Percentage rodPosition;
    
    public Reactor(double volume) {
        super(new Water(ROOM_TEMP, new Kilograms(100)), new Steam(ROOM_TEMP, new Kilograms(1)), volume);
        rodPosition = new Percentage(0);
    }

    public void moveControlRods(Percentage extracted) {
        this.rodPosition = extracted;
    }

    public Percentage controlRodPosition() {
        return rodPosition;
    }
    
    private double getEnergyGenerated() {
        return rodPosition.ratio() * 1000 + 100;
    }
    
    public void step() {
        // heatup water, convert some of it into steam
        steam.add(water.addEnergy(getEnergyGenerated(), steam.getPressure(getCompressibleVolume())));
        
        // equalize
        HydraulicState otherHydro = getOutputComponent().getHydroState();
        double equalPressure = getEqualizedPressure(otherHydro);
        
        // if the resulting pressure is greater it means
        // stuff should arrive at us, but that is not allowed (send stuff only one way)
        // (equivalent of a valve basically)
        if (equalPressure < getPressure()) { // send stuff
            int qty = steam.getParticlesAtState(equalPressure, getCompressibleVolume());
            int deltaAmmount = steam.getParticleNr() - qty;
            
            outputComponent.receiveMatter(new Steam(steam.getTemperature(), deltaAmmount));
            steam.remove(deltaAmmount);
        }
        
    }
    
    @Override
    protected void receiveMatter(Matter m) {
        water.add((Water)m);        
    }

    @Override
    protected HydraulicState getHydroState() {
        return new HydraulicState(getBottomPressure(), getCompressibleVolume());
    }
    
}
