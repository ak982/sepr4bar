package dab.engine.newsim.utils;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 *
 * @author bjw523
 */
public class AverageBufferTest {
    @Test
    public void testAddOneElement() {
        AverageBuffer buffer = new AverageBuffer(1);
        buffer.addElement(4);
        assertEquals(4, buffer.getAverage());
    }
    
    @Test
    public void testAddMultipleElements() {
        AverageBuffer buffer = new AverageBuffer(3);
        buffer.addElement(4);
        buffer.addElement(6);
        buffer.addElement(5);
        assertEquals(5, buffer.getAverage());
    }
    
    @Test
    public void testAddCircularElements() {
        AverageBuffer buffer = new AverageBuffer(3);
        buffer.addElement(7);
        buffer.addElement(4);
        buffer.addElement(6);
        buffer.addElement(5);
        assertEquals(5, buffer.getAverage());
    }
}
