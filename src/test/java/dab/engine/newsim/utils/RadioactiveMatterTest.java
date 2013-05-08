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
  /*  @Test
    public void testSameTemperature() {
        RadioactiveMatter matter = new RadioactiveMatter(new Kilograms(10));
        double otherTemperature = 300; double temperature = matter.getTemperature();
        Ratio ratio = new Ratio(0.7);
        matter.equilibrateTemperature(ratio, otherTemperature);
        assertEquals(300, matter.getTemperature());
    }*/
    @Test
    public void testLowerTemperature() {
        RadioactiveMatter matter = new RadioactiveMatter(new Kilograms(10));
        double otherTemperature = 100; double temperature = matter.getTemperature();
        Ratio ratio = new Ratio(0.7);
        matter.equilibrateTemperature(ratio, otherTemperature);
        assertTrue(temperature > matter.getTemperature());
    }
    
    @Test
    public void testHigherTemperature() {
        RadioactiveMatter matter = new RadioactiveMatter(new Kilograms(10));
        double otherTemperature = 400; double temperature = matter.getTemperature();
        Ratio ratio = new Ratio(0.7);
        matter.equilibrateTemperature(ratio, otherTemperature);
        assertTrue(temperature < matter.getTemperature());
    }
}
