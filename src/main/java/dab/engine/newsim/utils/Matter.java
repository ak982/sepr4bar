/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Note about particleNr: pV = NRT. Considering Avogadro's number (number of
 * atoms per mol) as 600. R then becomes 1.38 * 10^(-2) so, quantities remain
 * expressed in moles, it's just that R is now 10^21 times bigger
 *
 * @author eduard
 */
public abstract class Matter {

    @JsonProperty
    protected double temperature;
    
    @JsonProperty
    protected int particleNr;

    protected Matter() {
        this.temperature = 0;
        this.particleNr = 0;
    }
    
    protected Matter(double temperature, Kilograms mass) {
        this.temperature = temperature;
        this.particleNr = (int) (mass.getValue() * getParticlesPerKilo());
    }

    protected Matter(double temperature, int particleNr) {
        this.temperature = temperature;
        this.particleNr = particleNr;
    }

    /**
     * Adds more matter, also calculates new mixed temperature (weighted
     * average)
     *
     * @return this object for easier chaining of objects
     */
    protected void add(Matter x) {
        int newParticleNr = particleNr + x.getParticleNr();
        if (newParticleNr != 0) {
            double newTemperature = (particleNr * temperature + x.getParticleNr() * x.getTemperature()) / newParticleNr;
            particleNr = newParticleNr;
            temperature = newTemperature;
        }
    }
    
    public double calculateTemperatureEqulibrium(Matter x) {
        return (particleNr * temperature + x.getParticleNr() * x.getTemperature()) / (particleNr + x.getParticleNr());
    }

    public void remove(int particlesRemoved) {
        if (this.particleNr < particlesRemoved) {
            throw new RuntimeException("Can not remove more than there is");
        } else {
            this.particleNr -= particlesRemoved;
        }
    }

    public int getParticleNr() {
        return particleNr;
    }

    public final double getMoles() {
        return (double) getParticleNr() / (double) Constants.AVOGADRO_NUMBER;
    }

    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    protected void addEnergy(double energy) {
        // specific heat = ammount of *energy* required to raise it by 1 degree / mole = energy / (mol * temp)
        // specificHeat = Energy / (kg * K)
        // K(temp) = Energy / (kg * specificH)
        if (getMass() < 0.0001) {
            throw new RuntimeException("Can not add energy to nothing");
        }
        temperature += energy / (getMass() * getSpecificHeat());
    }

    public double energyNeededToTemperature(double temperature) {
        //neededEnergy = (boilingPtAtPressure - temperature.inKelvin()) * waterMass.inKilograms() * specificHeatOfWater;
        // E = Temp * mass * specificHeat
        return (temperature - getTemperature()) * getMass() * getSpecificHeat();
    }

    abstract double getSpecificHeat();

    public final double getParticlesPerKilo() {
        return Constants.AVOGADRO_NUMBER / getMolarMass();
    }

    public final double getMass() {
        return getMoles() * getMolarMass();
    }

    public abstract double getMolarMass();
    
    @Override
    public String toString() {
        return String.format("M: %f\tT: %f\t", getMass(), getTemperature());
    }
}
