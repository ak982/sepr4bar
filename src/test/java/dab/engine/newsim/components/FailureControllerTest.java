package dab.engine.newsim.components;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author bjw523
 */
public class FailureControllerTest { 
    
    @Test
    public void testFixDamage() {
        FailureController failureC = new FailureController();
        failureC.fail(2);
        failureC.fixDamage();
        assertEquals(1, failureC.getDamage());
    }
    
    @Test
    public void testRepair() {
        FailureController failureC = new FailureController();
        failureC.repair();
        assertTrue(failureC.getDamage() == 0);
    }
    
    @Test
    public void testRepairFailed() {
        FailureController failureC = new FailureController();
        failureC.fail(2);
        failureC.repair();
        assertEquals(0, failureC.getDamage());
    }
    
    @Test
    public void testFail() {
        FailureController failureC = new FailureController();
        int maxDamage = 5;
        failureC.setDamageValues(maxDamage, 1);
        failureC.fail();
        assertEquals(maxDamage, failureC.getDamage());
    }
    
    @Test
    public void testFailAmount() {
        FailureController failureC = new FailureController();
        int maxDamage = 5; int damage = 2;
        failureC.setDamageValues(maxDamage, 1);
        failureC.fail(damage);
        assertEquals(maxDamage + damage, failureC.getDamage());
    }
}
