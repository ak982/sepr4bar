/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class HeatSink {
    private static final int STEAM_PARTICLES = 30000; // around 0.6 kg of steam
    private static final int WATER_PARTICLES = 120000;
    private static final double DELTA_RATIO = 0.01;      // how much to change the temperature each step
    private double temperature;
    
    public HeatSink() {
        temperature = Constants.ROOM_TEMP;
    }
    
    private Steam getCoolantSteam() {
        return new Steam(temperature, STEAM_PARTICLES);
    }
    
    // FIXME: magic number
    public void step() {
        temperature -= (temperature - Constants.ROOM_TEMP) * DELTA_RATIO;
    }
    
    private Matter getCoolant(Matter toBeCooled) {
        if (toBeCooled instanceof Steam) {
            return new Steam(temperature, STEAM_PARTICLES);
        } else if (toBeCooled instanceof Water) {
            return new Water(temperature, WATER_PARTICLES);
        } else {
            throw new RuntimeException("Unknown matter type");
        }
    }
    
    public Matter combine(Matter toBeCooled) {
        
        step();
        Matter coolant = getCoolant(toBeCooled);
        toBeCooled.add(coolant); // combine
      
        // NOTE: it's actually a good thing because the temperature varies quite a bit (when cooling down steam and water)
        temperature = toBeCooled.getTemperature(); // set temperature to combined steam
        toBeCooled.remove(coolant.getParticleNr());       // remove as many particles as we added
        
        return toBeCooled;
    }
}
