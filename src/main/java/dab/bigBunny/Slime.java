/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author eduard
 */
public class Slime extends TemporaryObject {
    
    private static final int radius = 50;
    private int initialTimeToLive;
    
    public Slime(int x, int y, int ttl) {
        this(new Point(x, y), ttl);
    }
    
    public Slime(Point p, int ttl) {
        super(p, ttl);
        initialTimeToLive = ttl;
    }
    
    public int   getRadius()   { return radius; }
    
    
    /**
     * @return a double from 0 (old) to 1 (new) \\
     *         depending on how old the slime is.
     */
    public double getFreshness() {
        return (double)remainingTimeToLive / (double)initialTimeToLive;
    }
    
    public String toString() {
        return String.format("fresh: %f, X: %f, Y: %f", getFreshness(), location.getX(), location.getY());
    }
}
