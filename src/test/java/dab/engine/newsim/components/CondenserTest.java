package dab.engine.newsim.components;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import dab.engine.newsim.utils.Steam;

/**
 *
 * @author bjw523
 */
public class CondenserTest {
    @Test
    public void testReceiveMatter() {
        Condenser condenser = new Condenser("testCondenser", 100, 100);
        Steam steam = condenser.getSteam();
        condenser.receiveMatter(new Steam(100, 10));
        assertTrue(steam.getMass() < condenser.getSteam().getMass());
    }
}
