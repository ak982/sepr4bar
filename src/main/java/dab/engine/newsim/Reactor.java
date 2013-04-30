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
    
    Percentage rodPosition;
    
    public Reactor(double volume) {
        super(
                new Water(Constants.ROOM_TEMP + 40, new Kilograms((6 * volume / 10) * Constants.WATER_NORMAL_DENSITY)),
                new Steam(Constants.ROOM_TEMP, (int) ((4 * volume / 10) * Constants.NORMAL_STEAM_PARTICLES_PER_VOLUME)),
                volume);
        rodPosition = new Percentage(30);
    }

    public void moveControlRods(Percentage extracted) {
        this.rodPosition = extracted;
    }

    public Percentage controlRodPosition() {
        return rodPosition;
    }
    
    // from 1000 (1MW) to 1001000 kj per second (1GW)
    private double getEnergyGenerated() {
        return ((rodPosition.ratio() * 10) * 1000 + 1000) / Constants.TICKS_PER_SECOND;
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
