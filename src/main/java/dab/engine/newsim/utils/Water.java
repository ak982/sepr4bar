/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public class Water extends Matter {
   
    public Water(double temperature, Kilograms mass) {
        super(temperature, mass);
    }
    
    public Water(double temperature, int particleNr) {
        super(temperature, particleNr);
    }
    
    @Override
    public double getMolarMass() {
        return Constants.MOLAR_MASS_WATER;
    }
    
    // approximate function
    public static double getBoilingTemperature(double pressure) {
        double atmospheres = pressure / Constants.ATMOSPHERIC_PRESSURE;
        if (pressure < Constants.ATMOSPHERIC_PRESSURE) {
            return Constants.NORMAL_BOILING_POINT + 20 * Math.log( atmospheres );
        } else {
            return Constants.NORMAL_BOILING_POINT + 100 * Math.log10(atmospheres);
        } 
        
    }
    
    // inverse of previous function
    public static double getBoilingPressure(double temperature) {
        if (temperature < Constants.NORMAL_BOILING_POINT) {
            return Constants.ATMOSPHERIC_PRESSURE * Math.exp((temperature - Constants.NORMAL_BOILING_POINT) / 20);
        } else {
            return Constants.ATMOSPHERIC_PRESSURE * Math.pow(10, (temperature - Constants.NORMAL_BOILING_POINT) / 100);
        }
    }
    
    // approximate function in kg / m^3
    // see http://www.engineeringtoolbox.com/water-thermal-properties-d_162.html
    // for details
    public static double getDensityAt(double temperature) {
        final double BOILING_DENSITY = Constants.NORMAL_DENSITY_WATER - (Constants.NORMAL_BOILING_POINT - Constants.NORMAL_FREEZING_POINT) / 2;
        if (temperature < Constants.NORMAL_BOILING_POINT) {
            if (temperature < Constants.NORMAL_FREEZING_POINT) {
                return Constants.NORMAL_DENSITY_WATER;
            } else {
                return Constants.NORMAL_DENSITY_WATER - (temperature - Constants.NORMAL_FREEZING_POINT) / 2;
            }
        } else {
            return BOILING_DENSITY - (temperature - Constants.NORMAL_BOILING_POINT);
        }
    }
    
    public double getDensity() {
        return getDensityAt(getTemperature());
    }
    
    public double getVolume() {
        return getMass() / getDensity();
    }
    
    public double energyNeededToBoil(double pressure) {
        return energyNeededToTemperature(getBoilingTemperature(pressure));
    }
    
    public void add(Water w) {
        super.add(w);
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
            double steamMass = deltaEnergy / Constants.LATENT_HEAT_WATER;
            Steam generated = new Steam(getTemperature(), new Kilograms(steamMass));
            int deltaQty = Math.min(getParticleNr(), generated.getParticleNr());
            remove(deltaQty);
            return new Steam(getTemperature(), deltaQty);
        }
    }
    
    /*
     * pressure at bottom if this water was put in a cilinder with a certain area
     */
    public double getHydrostaticPressure(double area) {
        return getDensity() * Constants.GRAVITATIONAL_ACCELERATION * (getVolume() / area);
    }
    
    @Override
    double getSpecificHeat() {
        return Constants.SPECIFIC_HEAT_WATER;
    }
    
    @Override
    public String toString() {
        return "Wtr: " + super.toString();
    }
    
}
