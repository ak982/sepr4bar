/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;
/**
 *
 * @author eduard
 */
public class Condenser extends Container {

    private HeatSink heatSink;
    
    public Condenser(double volume) {
        super(
                new Water(Constants.ROOM_TEMP, new Kilograms((volume / 10) * Constants.WATER_NORMAL_DENSITY)),
                new Steam(Constants.ROOM_TEMP, (int)((9 * volume / 10) * Constants.NORMAL_STEAM_PARTICLES_PER_VOLUME)),
                volume);
        heatSink = new HeatSink();
    }
    
    public void step() {
        // update heatsink
        //heatSink.step();
        
        // cool steam down
        heatSink.combine(steam);
        
        // if pressure is smaller than atmosferic one, then we don't condense anything,
        // otherwise we condense such that we reach the boiling point at that pressure
        if (getPressure() > Constants.ATMOSPHERIC_PRESSURE) {
            double boilingPoint = Water.getBoilingTemperature(getPressure());
            if (boilingPoint > steam.getTemperature()) { // remove steam such that it equalizes to the boiling point
                double newSteamPressure = Math.max(Water.getBoilingPressure(steam.getTemperature()), Constants.ATMOSPHERIC_PRESSURE);
                int newQuantity = steam.getParticlesAtState(newSteamPressure, getCompressibleVolume());
                int deltaQuantity = steam.getParticleNr() - newQuantity;
                steam.remove(deltaQuantity);
                water.add(new Water(steam.getTemperature(), deltaQuantity));          
            }
        }
        
        /*
        // equalize
        HydraulicState otherHydro = getOutputComponent().getHydroState();
        double equalPressure = getEqualizedPressure(otherHydro);
        
        // if the resulting pressure is greater it means
        // notice how we check the bottom pressure, because that is where we have
        // our outputport
        if (equalPressure < getBottomPressure()) { // send stuff
            int qty = steam.getParticlesAtState(equalPressure, getCompressibleVolume());
            int deltaAmmount = steam.getParticleNr() - qty;
            
            outputComponent.receiveMatter(new Water(steam.getTemperature(), deltaAmmount));
            water.remove(deltaAmmount);
            
        }
        */
        
        double otherPressure = getHydroState().getPressure();
        double ourPressure   = getBottomPressure();
        if (otherPressure < ourPressure) {
            int deltaQty = Math.min(water.getParticleNr(), (int)((ourPressure - otherPressure)));
            water.remove(deltaQty);
            outputComponent.receiveMatter(new Water(water.getTemperature(), deltaQty));
        }
    }
    
    @Override
    protected void receiveMatter(Matter m) {
        steam.add((Steam)m);
    }

    @Override
    protected HydraulicState getHydroState() {
        return new HydraulicState(getPressure(), getCompressibleVolume());
    }
    
}
