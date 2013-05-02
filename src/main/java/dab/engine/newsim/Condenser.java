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
    
    public Condenser(double volume, double area) {
        super(
                new Water(Constants.ROOM_TEMP, new Kilograms((volume / 10) * Constants.WATER_NORMAL_DENSITY)),
                new Steam(Constants.ROOM_TEMP, (int)((9 * volume / 10) * Constants.NORMAL_STEAM_PARTICLES_PER_VOLUME)),
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
        /*
         * otherPres + (dm * g) / otherA = ourPres - (dm * g) / ourA
         * dm = deltaP / g * (1 / otherA + 1 / ourA)
         *    = dp * A1 * A2 / (g * (A1 + A2))
         *    = dp * areaFactor / g
         */
        
        for (int i = 0; i < 10; ++i) {
            if (outputComponent.getHydroState().getPressure() < getBottomPressure()) {
                Water deltaWater = new Water(water.getTemperature(), Math.min(8 * water.getParticlesPerKilo(), water.getParticleNr()));
                System.out.println("Before: " + outputComponent.getHydroState().getPressure() + " " + getBottomPressure());
                send(deltaWater);
                water.remove(deltaWater.getParticleNr());
                System.out.println("After: " + outputComponent.getHydroState().getPressure() + " " + getBottomPressure());
                
                
            } else {
                break;
            }
            /*ContainerHydroState otherComponent = (ContainerHydroState) outputComponent.getHydroState();
            double deltaPressure = getBottomPressure() - otherComponent.getPressure(); // our pressure minus the other one
            if (deltaPressure > 1000) { // FIXME: MAGIC NUMBER
                double areaFactor = area * otherComponent.getArea() / (area + otherComponent.getArea());
                double deltaMass = deltaPressure * areaFactor / Constants.GRAVITATIONAL_ACCELERATION;
                deltaMass *= 0.8; // slight attenuation, we don't want it to overshoot
                int deltaQty = Math.min(water.getParticleNr(), (int) (water.getParticlesPerKilo() * deltaMass));
                if (debugMode) {
                    System.out.println("other pressure: " + otherComponent.getPressure());
                    System.out.println("our pressure: " + getBottomPressure());
                    System.out.println("delta mass : " + deltaMass);
                }
                water.remove(deltaQty);
                outputComponent.receiveMatter(new Water(water.getTemperature(), deltaQty));
                
                if (debugMode) {
                    System.out.println("after " + i);
                    System.out.println("other pressure: " + outputComponent.getHydroState().getPressure());
                    System.out.println("our pressure: " + getBottomPressure());
                }
                
            }*/
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
