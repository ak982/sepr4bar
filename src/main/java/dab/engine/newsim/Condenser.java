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

    private static final double INITIAL_WATER_RATIO = 0.1;
    private HeatSink heatSink;
    
    public Condenser(double volume, double area) {
        super(
                new Water(Constants.ROOM_TEMP, new Kilograms((volume * INITIAL_WATER_RATIO) * Constants.NORMAL_DENSITY_WATER)),
                new Steam(Constants.ROOM_TEMP, (int)((volume * (1 - INITIAL_WATER_RATIO)) * Constants.NORMAL_PARTICLES_PER_VOLUME_STEAM)),
                area,
                volume / area);
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
         * otherPres + (dm * g) / otherA = ourPres - (dm * g) / ourA
         * dm = deltaP / g * (1 / otherA + 1 / ourA)
         *    = dp * A1 * A2 / (g * (A1 + A2))
         *    = dp * areaFactor / g
         */
        // send water until we have equalized
        for (int i = 0; i < 10; ++i) {
            if (outputComponent.getHydroState().getPressure() < getBottomPressure()) {
                Water deltaWater = new Water(water.getTemperature(), Math.min(3 * water.getParticlesPerKilo(), water.getParticleNr()));
                //System.out.println("C: Before: " + outputComponent.getHydroState().getPressure() + " " + getBottomPressure());
                send(deltaWater);
                water.remove(deltaWater.getParticleNr());
                //System.out.println("C: After: " + outputComponent.getHydroState().getPressure() + " " + getBottomPressure());
                
                
            } else {
                break;
            }
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
    
    @Override
    public String toString() {
        return "C: " + super.toString();
    }
}
