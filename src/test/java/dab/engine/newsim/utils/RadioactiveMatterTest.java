package dab.engine.newsim.utils;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author bjw523
 */

public class RadioactiveMatterTest {
    
    RadioactiveMatter matter;
    double initialTemperature;
    
    public RadioactiveMatterTest() {
        matter = null;
        initialTemperature = 0;
    }
    
    @Before
    public void setUp() {
        matter = new RadioactiveMatter(new Kilograms(1000));
        initialTemperature = matter.getTemperature();
    }
    
    @Test
    public void testSameTemperature() {
        matter.equilibrateTemperature(1, initialTemperature);
        assertEquals(initialTemperature, matter.getTemperature(), 0.0001);
    }
    @Test
    public void testLowerTemperature() {
        matter.equilibrateTemperature(1, initialTemperature - 100);
        assertEquals(initialTemperature - 1, matter.getTemperature(), 0.0001);
    }
    
    @Test
    public void testTransferSpeed() {
        for (double i = -1; i <= 1; i += 0.1) {
            matter = new RadioactiveMatter(new Kilograms(1000));
            matter.equilibrateTemperature(Math.abs(i), initialTemperature + i);
            assertEquals(initialTemperature + i, matter.getTemperature(), 0.0001);
        }
    }
    
    @Test
    public void testHigherTemperature() {
        matter.equilibrateTemperature(1, initialTemperature + 100);
        assertEquals(initialTemperature + 1, matter.getTemperature(), 0.0001);
    }
    
    @Test
    public void testSpecificHeat() {
        matter.addEnergy(RadioactiveMatter.SPECIFIC_HEAT * 1000); // 2kj
        assertEquals(initialTemperature + 1, matter.getTemperature(), 0.0001);
    }
    
    @Test
    public void testTemperatureIncrease() {
        matter.addEnergy(matter.getSpecificHeat() * 5 * 1000);
        assertEquals(initialTemperature + 5, matter.getTemperature(), 0.0001);
    }
}
