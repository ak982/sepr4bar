/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 * Note about particleNr: pV = NRT. Considering Avogadro's number (number of
 * atoms per mol) as 600. R then becomes 1.38 * 10^(-2) so, quantities remain
 * expressed in moles, it's just that R is now 10^21 times bigger
 *
 * @author eduard
 */
public abstract class Matter {

    protected double temperature;
    protected int particleNr;

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
    protected Matter add(Matter x) {
        int newParticleNr = particleNr + x.getParticleNr();
        double newTemperature = (particleNr * temperature + x.getParticleNr() * x.getTemperature()) / newParticleNr;
        particleNr = newParticleNr;
        temperature = newTemperature;
        return this;
    }

    public Matter remove(int particlesRemoved) {
        if (this.particleNr < particlesRemoved) {
            throw new RuntimeException("Can not remove more than there is");
        } else {
            this.particleNr -= particlesRemoved;
            return this;
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

    protected Matter addEnergy(double energy) {
        // specific heat = ammount of *energy* required to raise it by 1 degree / mole = energy / (mol * temp)
        // specificHeat = Energy / (kg * K)
        // K(temp) = Energy / (kg * specificH)
        if (getMass() < 0.0001) {
            throw new RuntimeException("Can not add energy to nothing");
        }
        temperature += energy / (getMass() * getSpecificHeat());
        return this;
    }

    public double energyNeededToTemperature(double temperature) {
        //neededEnergy = (boilingPtAtPressure - temperature.inKelvin()) * waterMass.inKilograms() * specificHeatOfWater;
        // E = Temp * mass * specificHeat
        return (temperature - getTemperature()) * getMass() * getSpecificHeat();
    }

    abstract double getSpecificHeat();

    public final int getParticlesPerKilo() {
        return (int) (Constants.AVOGADRO_NUMBER / getMolarMass());
    }

    public final double getMass() {
        return getMoles() * getMolarMass();
    }

    public abstract double getMolarMass();
    
    @Override
    public String toString() {
        return String.format("PNo: %d\tM: %f\tT: %f\t", getParticleNr(), getMass(), getTemperature());
    }
}
