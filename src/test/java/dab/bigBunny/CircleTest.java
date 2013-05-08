/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package dab.bigBunny;

import java.awt.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Alex
*/
public class CircleTest {
    
    public CircleTest() {
    }
    
    /**
* Test of getHittableBounds method, of class Circle.
* Should return coordinates that correspond to those of the circle with a
* plus radius gap on every side.
*/
    @Test
    public void testGetHittableBounds() {
        int radius = 5;
        Circle instance = new Circle(null, 50, 50, 10, 10);
        Rectangle expResult = new Rectangle(45, 45, 20, 20);
        Rectangle result = instance.getHittableBounds(radius);
        assertEquals(expResult, result);
    }
}