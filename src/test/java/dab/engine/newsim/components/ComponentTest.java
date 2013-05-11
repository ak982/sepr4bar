package dab.engine.newsim.components;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.Water;

/**
 *
 * @author bjw523
 */
public class ComponentTest {
    
    @Test
    public void testSendSteam() {
        Condenser outputComponent = new Condenser("testCondenser1", 100, 100);
        Condenser component = new Condenser("testCondenser2", 100, 100);
        Steam steam = outputComponent.getSteam(); 
        component.setOutputComponent(outputComponent);
        component.send(new Steam(100, 30000));
        assertNotSame(steam.getMass(), outputComponent.getSteam().getMass());
    }
    @Test
    public void testSendWater() {
        Reactor outputComponent = new Reactor("testReactor1", 100, 100);
        Reactor component = new Reactor("testReactor1", 100, 100);
        Water water = outputComponent.getWater(); 
        component.setOutputComponent(outputComponent);
        component.send(new Water(100, 30000));
        assertNotSame(water.getMass(), outputComponent.getWater().getMass());
    }
}
