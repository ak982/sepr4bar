/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import com.sun.org.apache.xpath.internal.axes.WalkerFactory;

/**
 *
 * @author eduard
 */
public class Steam extends Matter {

    public Steam(double temp, double mass) {
        super(temp, mass);
    }
    
     /**
     * Normally specific heat changes with temperature, 
     * for our model, it remains constant
     * @return magic number
     */
    @Override
    double getSpecificHeat() {
        return 2;
    }
    
    // pV = NKT
    public double getPressure(double volume) {
        return (getParticleNr() * getTemperature() *  BOLTZMAN_CONSTANT) / volume;
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
        super.remove(particleNr);
        return this;
    }

    @Override
    public double getMolarMass() {
        return Water.MOLAR_MASS;
    }
    

    
}
