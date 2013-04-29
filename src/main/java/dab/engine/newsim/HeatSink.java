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
    private static final int DEFAULT_PARTICLES = 30000; // around 0.6 kg of steam
    private static final double DELTA_RATIO = 0.01;      // how much to change the temperature each step
    private double temperature;
    
    public HeatSink() {
        temperature = Reactor.ROOM_TEMP;
    }
    
    private Steam getCoolantSteam() {
        return new Steam(temperature, DEFAULT_PARTICLES);
    }
    
    // FIXME: magic number
    public void step() {
        temperature -= (temperature - Reactor.ROOM_TEMP - 50) * DELTA_RATIO;
    }
    
    public Steam combine(Steam toBeCooled) {
        int coolantParticles = getCoolantSteam().getParticleNr();
        toBeCooled.add(getCoolantSteam()); // combine
      
        temperature = toBeCooled.getTemperature(); // set temperature to combined steam
        toBeCooled.remove(coolantParticles);       // remove as many particles as we added
        
        return toBeCooled;
    }
}
