/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package dab.bigBunny;

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
public class SlimeTest {
    
    public SlimeTest() {
    }
   

    /**
* Test of getFreshness method, of class Slime.
*/
    @Test
    public void testGetFreshness() {
        Slime instance = new Slime(50, 50, 30, 0.0);
        double expResult = 1.0;
        double result = instance.getFreshness();
        assertEquals(expResult, result, 0.00);
    }
    
    /**
* Test of toString method, of class Slime.
     * TODO test for rotation? current toString function doesn't do this; intentional?
*/
    @Test
    public void testToString() {
        Slime instance = new Slime(50, 50, 30, 0.0);
        String expResult = "fresh: 1.000000, X: 50.000000, Y: 50.000000";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}