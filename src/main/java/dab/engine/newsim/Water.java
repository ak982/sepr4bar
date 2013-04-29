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

    public static final double NORMAL_FREEZING_POINT = 273.15;
    public static final double NORMAL_BOILING_POINT = NORMAL_FREEZING_POINT + 100;
    public static final double ATMOSPHERIC_PRESSURE = 100000;
    public static final double MOLAR_MASS = 0.018; // kg / mol
    public static final double NORMAL_DENSITY = 1000; // kg / m3
    public static final double LATENT_HEAT = 2260; // kjoules
    public static final double GRAVITATIONAL_ACCELERATION = 9.8;
   
    public Water(double temperature, Kilograms mass) {
        super(temperature, mass);
    }
    
    public Water(double temperature, int particleNr) {
        super(temperature, particleNr);
    }
    
    @Override
    public double getMolarMass() {
        return MOLAR_MASS;
    }
    
    // approximate function
    public static double getBoilingTemperature(double pressure) {
        double atmospheres = pressure / ATMOSPHERIC_PRESSURE;
        if (pressure < ATMOSPHERIC_PRESSURE) {
            return NORMAL_BOILING_POINT + 20 * Math.log( atmospheres );
        } else {
            return NORMAL_BOILING_POINT + 100 * Math.log10(atmospheres);
        } 
        
    }
    
    // inverse of previous function
    public static double getBoilingPressure(double temperature) {
        if (temperature < NORMAL_BOILING_POINT) {
            return ATMOSPHERIC_PRESSURE * Math.exp((temperature - NORMAL_BOILING_POINT) / 20);
        } else {
            return ATMOSPHERIC_PRESSURE * Math.pow(10, (temperature - NORMAL_BOILING_POINT) / 100);
        }
    }
    
    // approximate function in kg / m^3
    // see http://www.engineeringtoolbox.com/water-thermal-properties-d_162.html
    // for details
    private double getDensity() {
        final double BOILING_DENSITY = NORMAL_DENSITY - (NORMAL_BOILING_POINT - NORMAL_FREEZING_POINT) / 2;
        if (getTemperature() < NORMAL_BOILING_POINT) {
            if (getTemperature() < NORMAL_FREEZING_POINT) {
                return NORMAL_DENSITY;
            } else {
                return NORMAL_DENSITY - (getTemperature() - NORMAL_FREEZING_POINT) / 2;
            }
        } else {
            return BOILING_DENSITY - (getTemperature() - NORMAL_BOILING_POINT);
        }
    }
    
    public double getVolume() {
        return getMass() / getDensity();
    }
    
    public double energyNeededToBoil(double pressure) {
        return energyNeededToTemperature(getBoilingTemperature(pressure));
    }
    
    // latent heat = ammount of energy required to transform 
    // L = E / m; m = E / L
    // return the ammount of steam created after energy has beeen added
    public Steam addEnergy(double energy, double pressure) {
        double energyNeeded = energyNeededToBoil(pressure);
        if (energyNeeded > energy) {
            super.addEnergy(energy);
            return new Steam(0, 0);
        } else {
            double deltaEnergy = energy - energyNeeded;
            super.addEnergy(energyNeeded);
            double steamMass = deltaEnergy / LATENT_HEAT;
            Steam generated = new Steam(getTemperature(), new Kilograms(steamMass));
            int deltaQty = Math.min(getParticleNr(), generated.getParticleNr());
            remove(deltaQty);
            return new Steam(getTemperature(), deltaQty);
        }
    }
    
    /*
     * 
     */
    protected double getHydrostaticPressure(double area) {
        return getDensity() * GRAVITATIONAL_ACCELERATION * (getVolume() / area);
    }
    
    @Override
    double getSpecificHeat() {
        return 4.2;
    }
    
}
