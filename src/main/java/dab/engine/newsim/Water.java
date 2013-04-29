/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class Water extends Matter {

    
    public static final double NORMAL_BOILING_POINT = 100;
    public static final double ATMOSFERIC_PRESSURE = 100000;
    public static final double MOLAR_MASS = 0.018; // kg / mol
    public static final double NORMAL_DENSITY = 1000; // kg / m3
    public static final double LATENT_HEAT = 2260; // kjoules
   
    public Water(double temperature, double mass) {
        super(temperature, mass);
    }
    
    @Override
    public double getMolarMass() {
        return MOLAR_MASS;
    }
    
    // approximate function
    private double getBoilingPoint(double pressure) {
        return NORMAL_BOILING_POINT + 10 * Math.log(pressure / ATMOSFERIC_PRESSURE );
    }
    
    // approximate function in kg / m^3
    private double getDensity() {
        return Math.max(NORMAL_DENSITY, NORMAL_DENSITY - (getTemperature() - NORMAL_BOILING_POINT));
    }
    
    public double getVolume() {
        return getMass() / getDensity();
    }
    
    public double energyNeededToBoil(double pressure) {
        //neededEnergy = (boilingPtAtPressure - temperature.inKelvin()) * waterMass.inKilograms() * specificHeatOfWater;
        // E = Temp * Mol * specificHeat
        return (getBoilingPoint(pressure) - getTemperature()) * getParticleNr() * getSpecificHeat();
    }
    
    // latent heat = ammount of energy required to transform 
    // L = E / m; m = E / L
    public Steam addEnergy(double energy, double pressure) {
        double energyNeeded = energyNeededToBoil(pressure);
        if (energyNeeded > energy) {
            super.addEnergy(energy);
            return new Steam(0, 0);
        } else {
            double deltaEnergy = energy - energyNeeded;
            super.addEnergy(energyNeeded);
            double steamMass = deltaEnergy / LATENT_HEAT;
            Steam generated = new Steam(getTemperature(), steamMass);
            remove(generated.getParticleNr());
            return generated;
        }
    }
    
    @Override
    double getSpecificHeat() {
        return 4.2;
    }
    
}
