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


    private static final double INIT_WATER_TEMP = Constants.ROOM_TEMP + 40;
    private static final double EXCESSWATER_THRESHOLD = 0.8; // always keep the water level below 80% of the volume
    private static final double INITIAL_WATER_RATIO = 0.5;
    private static final double CORE_MIN_HEIGHT_RATIO = 0.1;
    private static final double CORE_MAX_HEIGHT_RATIO = 0.2;
    
    ReactorCore core;

    public Reactor(double volume, double area) {
        super(
                new Water(INIT_WATER_TEMP, new Kilograms((volume * INITIAL_WATER_RATIO) * Water.getDensityAt(INIT_WATER_TEMP))),
                new Steam(Constants.ROOM_TEMP, (int) ((volume * (1 - INITIAL_WATER_RATIO)) * Constants.NORMAL_PARTICLES_PER_VOLUME_STEAM)),
                area,
                volume / area);
        core = new ReactorCore();
    }

    public void moveControlRods(Percentage extracted) {
        core.setRodPosition(new Ratio(extracted.ratio()));
    }

    public Percentage controlRodPosition() {
        return new Percentage(core.getRodPosition());
    }

    // calculate the equilibrium pressure of the steam in this container and the one described by hydroState
    private double getEqualizedPressure(HydraulicState hydroValue) {
        double v1 = getCompressibleVolume(), v2 = hydroValue.getCompressibleVolume();
        double p1 = getPressure(), p2 = hydroValue.getPressure();
        return (v1 * p1 + v2 * p2) / (v1 + v2);
    }
    
    public double getWaterLevelRatio() {
        return water.getVolume() / getTotalVolume();
    }
    
    private Ratio getCoreSubmersedLevel() {
        final double CORE_HEIGHT_RATIO = CORE_MAX_HEIGHT_RATIO - CORE_MIN_HEIGHT_RATIO;
        if (getWaterLevelRatio() < CORE_MAX_HEIGHT_RATIO) {
            if (getWaterLevelRatio() > CORE_MIN_HEIGHT_RATIO) {
                return Ratio.convertFrom((getWaterLevelRatio() - CORE_MIN_HEIGHT_RATIO) / CORE_HEIGHT_RATIO);
            } else {
                return Ratio.CONSTANT_0;
            }
        } else {
            return Ratio.CONSTANT_1;
        }
    }

    public double getCoreTemp() {
        return core.getTemperature();
    }
    
    public void step() {
        // heatup water, convert some of it into steam
        steam.add(water.addEnergy(core.getEnergyPerTick(getCoreSubmersedLevel()), steam.getPressure(getCompressibleVolume())));

        // heatup the core (only in case of low water level. eg. not fully submersed)
        core.step(getCoreSubmersedLevel(), water);
        
        discardExcessWater();
        equalizePressure();

    }

    private void discardExcessWater() {
        if (water.getVolume() > getTotalVolume() * EXCESSWATER_THRESHOLD) {
            double dm = water.getDensity() * (water.getVolume() - EXCESSWATER_THRESHOLD * getTotalVolume());
            System.out.println("Too much water in reactor! Discarding " + dm + " kg");

            water.remove((int) (water.getParticlesPerKilo() * dm));
        }
    }

    private void equalizePressure() {
        for (int i = 1; i <= 5; ++i) {
            //System.out.println(i);
            // equalize

            HydraulicState otherHydro = getOutputComponent().getHydroState();
            double equalPressure = getEqualizedPressure(otherHydro);

            // if the resulting pressure is greater it means
            // stuff should arrive at us, but that is not allowed (send stuff only one way)
            // (equivalent of a valve basically)
            if (equalPressure < getPressure()) { // send stuff
                int qty = steam.getParticlesAtState(equalPressure, getCompressibleVolume());
                int deltaAmmount = steam.getParticleNr() - qty;
                System.out.println(String.format("R: Before P: %f\tOP: %f", getPressure(), equalPressure));
                outputComponent.receiveMatter(new Steam(steam.getTemperature(), deltaAmmount));
                steam.remove(deltaAmmount);
                System.out.println(String.format("R: After P: %f\tOP: %f", getPressure(), getEqualizedPressure(getOutputComponent().getHydroState())));
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
        //equalizePressure();
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
