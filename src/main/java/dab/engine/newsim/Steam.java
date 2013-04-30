/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;


/**
 *
 * @author eduard
 */
public class Steam extends Matter {
    
    public Steam(double temp, Kilograms mass) {
        super(temp, mass);
    }
    
    public Steam(double temp, int nrParticles) {
        super(temp, nrParticles);
    }
    
     /**
     * Normally specific heat changes with temperature, 
     * for our model, it remains constant
     * @return magic number
     */
    @Override
    double getSpecificHeat() {
        return Constants.SPECIFIC_HEAT_STEAM;
    }
    
    // pV = NKT
    public double getPressure(double volume) {
        return (getParticleNr() * getTemperature() *  Constants.BOLTZMAN_CONSTANT) / volume;
    }
    
    public int getParticlesAtState(double pressure, double volume) {
        return (int)((pressure * volume) / (Constants.BOLTZMAN_CONSTANT * getTemperature()));
    }
    
    @Override
    public Steam addEnergy(double energy) {
        super.addEnergy(energy);
        return this;
    }
    
    public Water removeEnergy(double energy) {
        throw new UnsupportedOperationException();
    }
    
    public Steam add(Steam s) {
        super.add(s);
        return this;
    }
    
    @Override
    public Steam remove(int qty) {
        super.remove(qty);
        return this;
    }

    @Override
    public double getMolarMass() {
        return Constants.MOLAR_MASS;
    }
    

    
}
