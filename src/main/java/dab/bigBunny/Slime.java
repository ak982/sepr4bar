/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import java.awt.Point;

/**
 *
 * @author eduard
 */
public class Slime extends TemporaryObject {
    
    private int initialTimeToLive;
    
    public Slime(int x, int y, int ttl) {
        this(new Point(x, y), ttl);
    }
    
    public Slime(Point p, int ttl) {
        super(p, ttl);
        initialTimeToLive = ttl;
    } 
    
    /**
     * @return a double from 0 (old) to 1 (new) \\
     *         depending on how old the slime is.
     */
    public float getFreshness() {
        return (float)remainingTimeToLive / (float)initialTimeToLive;
    }
    
    public String toString() {
        return String.format("fresh: %f, X: %f, Y: %f", getFreshness(), location.getX(), location.getY());
    }
}
