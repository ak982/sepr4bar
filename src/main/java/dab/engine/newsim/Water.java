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
   
    public Water(double temperature, Kilograms mass) {
        super(temperature, mass);
    }
    
    public Water(double temperature, int particleNr) {
        super(temperature, particleNr);
    }
    
    @Override
    public double getMolarMass() {
        return Constants.MOLAR_MASS;
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
    public double getDensity() {
        final double BOILING_DENSITY = Constants.WATER_NORMAL_DENSITY - (Constants.NORMAL_BOILING_POINT - Constants.NORMAL_FREEZING_POINT) / 2;
        if (getTemperature() < Constants.NORMAL_BOILING_POINT) {
            if (getTemperature() < Constants.NORMAL_FREEZING_POINT) {
                return Constants.WATER_NORMAL_DENSITY;
            } else {
                return Constants.WATER_NORMAL_DENSITY - (getTemperature() - Constants.NORMAL_FREEZING_POINT) / 2;
            }
        } else {
            return BOILING_DENSITY - (getTemperature() - Constants.NORMAL_BOILING_POINT);
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
            double steamMass = deltaEnergy / Constants.LATENT_HEAT;
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
