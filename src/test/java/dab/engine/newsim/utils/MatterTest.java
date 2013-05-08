package dab.engine.newsim.utils;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 *
 * @author bjw523
 */
public class MatterTest {
    
    @Test
    public void testAdd() {
        int particleNr = 2; int newParticleNr = 4;
        Water matter = new Water(100, particleNr);
        Water newMatter = new Water(100, newParticleNr);
        matter.add(newMatter);
        assertEquals(particleNr + newParticleNr, matter.getParticleNr());
    }
    
    @Test
    public void testRemoveLST() {
        int particleNr = 4; int newParticleNr = 2;
        Water matter = new Water(100, particleNr);
        matter.remove(newParticleNr);
        assertEquals(particleNr - newParticleNr, matter.getParticleNr());
    }
    
    @Test (expected = RuntimeException.class)
    public void testRemoveGRT() {
        int particleNr = 2; int newParticleNr = 4;
        Water matter = new Water(100, particleNr);
        matter.remove(newParticleNr);
    }
    
    @Test (expected = RuntimeException.class)
    public void testAddEnergySmallMass() {
        Kilograms kilograms = new Kilograms(0.00001);
        Water matter = new Water(300, kilograms);
        matter.addEnergy(1);
    }
    
    public void testAddEnergy() {
        Kilograms kilograms = new Kilograms(10);
        double temperature = 100;
        double energy = 10;
        Water matter = new Water(temperature, kilograms);
        matter.addEnergy(energy);
        assertEquals(temperature + energy / (kilograms.getValue() * 4.2), matter.getTemperature());
    }
}
