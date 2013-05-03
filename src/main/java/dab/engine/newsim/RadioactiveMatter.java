/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class RadioactiveMatter extends Matter {

    public RadioactiveMatter(Kilograms mass) {
        super(Constants.ROOM_TEMP, mass);
    }
    
    // see http://www.engineeringtoolbox.com/specific-heat-solids-d_154.html
    // if you're curious, uranium has a very small one, so we didn't want to use it.
    @Override
    double getSpecificHeat() {
        return 2;
    }

    // the chemistry geeks will note this as being the actual value for uranium
    //  not really used unfortunately
    @Override
    public double getMolarMass() {
        return 238;
    }
    
    public void equilibrateTemperature(Ratio transferDifficulty, double otherTemp) {
        temperature = temperature * transferDifficulty.getValue() + otherTemp * transferDifficulty.getOppositeValue();
    }
    
    
}
