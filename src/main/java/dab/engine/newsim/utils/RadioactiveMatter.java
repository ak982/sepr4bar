/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public class RadioactiveMatter extends Matter {

    public final static double SPECIFIC_HEAT = 5;
    
    protected RadioactiveMatter() {
        super();
    }
    
    public RadioactiveMatter(Kilograms mass) {
        super(Constants.ROOM_TEMP, mass);
    }
    
    // see http://www.engineeringtoolbox.com/specific-heat-solids-d_154.html
    // if you're curious, uranium has a very small one, so we didn't want to use it.
    @Override
    double getSpecificHeat() {
        return SPECIFIC_HEAT;
    }

    
    // the chemistry geeks will note this as being the actual value for uranium
    //  not really used unfortunately
    @Override
    public double getMolarMass() {
        return 238;
    }

    @Override
    public void addEnergy(double energy) {
        super.addEnergy(energy);
    }
    
    public void equilibrateTemperature(double speed, double otherTemp) {
        double difference = temperature - otherTemp;
        double change = Math.min(speed, Math.abs(difference));
        if (difference > 0) { // we are hoter, therefore cooldown
            temperature -= change;
        } else {
            temperature += change;
        }
    }
    
    
}
