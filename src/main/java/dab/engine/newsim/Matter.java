/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 * Note about particleNr:
 * pV = NRT. Considering Avogadro's number (number of atoms per mol) as 600.
 * R then becomes 1.38 * 10^(-2)
 * so, quantities remain expressed in moles, it's just that R is now 10^21 times bigger
 * @author eduard
 */
public abstract class Matter {
    protected double temperature;
    protected int    particleNr;
    public static final int AVOGADRO_NUMBER = 600;
    public static final double GAS_CONSTANT_R = 8.314;
    public static final double BOLTZMAN_CONSTANT = GAS_CONSTANT_R / AVOGADRO_NUMBER;
    
    protected Matter(double temperature, double mass) {
        this.temperature = temperature;
        this.particleNr  = (int)(mass * getParticlesPerKilo());
    }
    
    /**
     * Adds more matter, also calculates new mixed temperature (weighted average)
     * @return this object for easier chaining of objects
     */
    protected Matter add(Matter x) {
        int newParticleNr = particleNr + x.getParticleNr();
        double newTemperature = (particleNr * temperature + x.getParticleNr() * x.getTemperature()) / newParticleNr;
        particleNr = newParticleNr;
        temperature = newTemperature;
        return this;
    }
    
    public Matter remove(int particles) {
        if (this.particleNr < particles) {
            throw new RuntimeException("Can not remove more than there is");
        }
        else {
            this.particleNr -= particles;
        }
        return this;
    }
    
    public int getParticleNr() {
        return particleNr;
    }
    
    public final double getMoles() {
        return (double)getParticleNr() / (double)AVOGADRO_NUMBER;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    protected Matter addEnergy(double energy) {
        // specific heat = ammount of *energy* required to raise it by 1 degree / mole = energy / (mol * temp)
        // specificHeat = Energy / (mol * K)
        // K(temp) = Energy / (mol * specificH)
        temperature += energy / (getMoles() * getSpecificHeat());
        return this;
    }
    
    abstract double getSpecificHeat();
    
    public final int getParticlesPerKilo() {
        return (int)(AVOGADRO_NUMBER / getMolarMass());
    }
            
    public final double getMass() {
        return getMoles() * getMolarMass();
    }
    
    public abstract double getMolarMass();
    
    
}
