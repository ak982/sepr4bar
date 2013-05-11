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
public class TemporaryObjectListTest {

    /**
* Test of step method, of class TemporaryObjectList.
*/
    @Test
    public void testRemoveDeadObjectOnStep() {
        TemporaryObjectList instance = new TemporaryObjectList();
        int size = instance.size();
        instance.add(new TemporaryObjectImpl(new Point(50,50), 1));
        instance.step();
        assertEquals(size, instance.size());
    }
    
    @Test
    public void testAddObject(){
        TemporaryObjectList instance = new TemporaryObjectList();
        int exp = instance.size() + 1;
        instance.add(new TemporaryObjectImpl(new Point(50,50), 1));
        assertEquals(exp, instance.size());
    }
    
    public class TemporaryObjectImpl extends TemporaryObject {

        public TemporaryObjectImpl(Point point, int time) {
            super(point, time);
        }
    }
}