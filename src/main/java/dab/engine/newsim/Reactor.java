/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.CannotRepairException;
import dab.engine.simulator.views.ReactorView;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public class Reactor extends Container {

    private static final double MIN_POWER = 1000;
    private static final double MAX_POWER = 1000000;
    Percentage rodPosition;

    public Reactor(double volume, double area) {
        super(
                new Water(Constants.ROOM_TEMP + 40, new Kilograms((6 * volume / 10) * Constants.WATER_NORMAL_DENSITY)),
                new Steam(Constants.ROOM_TEMP, (int) ((4 * volume / 10) * Constants.NORMAL_STEAM_PARTICLES_PER_VOLUME)),
                area,
                volume / area);
        rodPosition = new Percentage(80);
    }

    public void moveControlRods(Percentage extracted) {
        this.rodPosition = extracted;
    }

    public Percentage controlRodPosition() {
        return rodPosition;
    }

    // from 1000 (1MW) to 1001000 kj per second (1GW)
    private double getEnergyGenerated() {
        //return 100;
        return (MIN_POWER + (MAX_POWER - MIN_POWER) * rodPosition.ratio()) / Constants.TICKS_PER_SECOND;
    }

    // calculate the equilibrium pressure of the steam in this container and the one described by hydroState
    private double getEqualizedPressure(HydraulicState hydroValue) {
        double v1 = getCompressibleVolume(), v2 = hydroValue.getCompressibleVolume();
        double p1 = getPressure(), p2 = hydroValue.getPressure();
        return (v1 * p1 + v2 * p2) / (v1 + v2);
    }

    public void step() {
        // heatup water, convert some of it into steam
        steam.add(water.addEnergy(getEnergyGenerated(), steam.getPressure(getCompressibleVolume())));

        discardExcessWater();
        equalizePressure();

    }

    private void discardExcessWater() {
        if (water.getVolume() > getTotalVolume() * 0.9) {
            double dm = water.getDensity() * (water.getVolume() - 0.9 * getTotalVolume());
            System.out.println("Too much water in reactor! Discarding " + dm + " kg");

            water.remove((int) (water.getParticlesPerKilo() * dm));
        }
    }

    private void equalizePressure() {
        for (int i = 1; i <= 3; ++i) {
            System.out.println(i);
            // equalize

            HydraulicState otherHydro = getOutputComponent().getHydroState();
            double equalPressure = getEqualizedPressure(otherHydro);

            // if the resulting pressure is greater it means
            // stuff should arrive at us, but that is not allowed (send stuff only one way)
            // (equivalent of a valve basically)
            if (equalPressure + 10 < getPressure()) { // send stuff
                int qty = steam.getParticlesAtState(equalPressure, getCompressibleVolume());
                int deltaAmmount = steam.getParticleNr() - qty;
                outputComponent.receiveMatter(new Steam(steam.getTemperature(), deltaAmmount));
                steam.remove(deltaAmmount);
                //System.out.println(getPressure());
            } else {
                break;
            }
        }
    }

    /*
     * V - dv = 0.95*rV => dv = V - 0.95*rV;
     * dens = m / V => m = dens * v => dm = dens * dv
     */
    @Override
    protected void receiveMatter(Matter m) {
        water.add((Water) m);
        discardExcessWater();
        equalizePressure();
    }

    @Override
    protected HydraulicState getHydroState() {
        return new ContainerHydroState(getBottomPressure(), getCompressibleVolume(), area);
    }

    @Override
    public String toString() {
        return "R: " + super.toString();
    }
}
