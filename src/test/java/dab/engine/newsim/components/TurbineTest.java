package dab.engine.newsim.components;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import dab.engine.newsim.utils.Steam;

/**
 *
 * @author bjw523
 */
public class TurbineTest {
    @Test (expected = RuntimeException.class)
    public void testHasFailed() {
        Turbine turbine = new Turbine("turbine");
        turbine.fail(1);
        turbine.receiveMatter(new Steam(100, 30000));
    }
    
    /*@Test
    public void testEnergyGenerated() {
        Turbine turbine = new Turbine("turbine");
        double tolerance = 0.01;
        Steam steam = new Steam(100, 30000);
        turbine.receiveMatter(steam);
        assertEquals(steam.getMass()*100, turbine.outputPower(), tolerance);
    }*/
    
    @Test
    public void testReducePower() {
        Turbine turbine = new Turbine("turbine");
        double reduction = 10; double tolerance = 0.01;
        double energyGenerated = turbine.outputPower();
        turbine.reducePower(reduction);
        assertEquals(energyGenerated - reduction, turbine.outputPower(), tolerance);
    }
}
