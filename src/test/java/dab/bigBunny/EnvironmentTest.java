/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package dab.bigBunny;

import java.awt.Point;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Alex
*/
public class EnvironmentTest {
    
    public EnvironmentTest() {
    }

    /**
* Test of step method, of class Environment.
*/
    @Test
    public void testStepCreatesSlimes() {
        Environment instance = new Environment();
        instance.setBounds(1024,1024);
        
        for(int i = 0; i < 2000; i++){
                instance.step();
        }
        
        assertTrue(instance.getSlimes().size() > 0);
   
    }

    /**
* Test of intersectWithSlime method, of class Environment.
*/
    @Test
    public void testIntersectWithSlime() {
        Point p = new Point(100,100);
        int radius = 5;
        Environment instance = new Environment();
        instance.setBounds(1024,1024);
        
        
        Slime expResult = new Slime(100, 100, 30, 0.0);
        
        instance.getSlimes().add(expResult);
        Slime result = instance.intersectWithSlime(p, radius);
        assertEquals(expResult, result);
    }
}