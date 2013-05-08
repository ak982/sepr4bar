/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package dab.bigBunny;

import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Alex
*/
public class TemporaryObjectTest {
    
    public TemporaryObjectTest() {
    }

    /**
* Test of step method, of class TemporaryObject.
* Should be dead if time was 1.
*/
    @Test
        public void testStepAboveZero() {
        int i = 1;
        TemporaryObject instance = new TemporaryObjectImpl(new Point(50,50), i);
        instance.step();
        assertEquals(instance.remainingTimeToLive, i - 1);
    }
    
    /**
* Test of step method, of class TemporaryObject.
* Should be alive if time was greater than 1.
*/
    @Test
    public void testStepAtZero() {
        int i = 1;
        TemporaryObject instance = new TemporaryObjectImpl(new Point(50,50), i);
        instance.step();
        instance.step();
        assertEquals(instance.remainingTimeToLive, 0);
    }

    public class TemporaryObjectImpl extends TemporaryObject {

        public TemporaryObjectImpl(Point point, int time) {
            super(point, time);
        }
    }
}