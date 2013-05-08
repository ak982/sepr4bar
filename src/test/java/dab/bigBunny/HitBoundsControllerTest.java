/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.newsim.components.Turbine;
import dab.engine.newsim.components.Pump;
import dab.engine.newsim.interfaces.FailableComponentView;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ajh555
 */
public class HitBoundsControllerTest {
    
    HitBoundsController instance;
    
    public HitBoundsControllerTest() {
    }
    
    @Before
    public void setUp() {
        instance = new HitBoundsController();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of addHitableComponent method, of class HitBoundsController.
     */
    @Test
    public void testAddHitableComponentPump() {
        Pump pumpInstance = new Pump("test", 100);
        int endOfList = instance.getHittableComponents().size();
        instance.addHitableComponent(pumpInstance, 200, 200, 100, 100);
        
        assertTrue(instance.getHittableComponents().get(endOfList) instanceof Circle);
    }
    
    /**
     * Test of addHitableComponent method, of class HitBoundsController.
     */
    @Test
    public void testAddHitableComponentOther() {
        Turbine turbineInstance = new Turbine("test");
        int endOfList = instance.getHittableComponents().size();
        instance.addHitableComponent(turbineInstance, 200, 200, 100, 100);
        
        assertTrue(instance.getHittableComponents().get(endOfList) instanceof RecCircle);
    }
}
