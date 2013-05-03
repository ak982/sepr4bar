/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 * class to hold ratio type - double value between 0 and 1.
 *
 * @author eduard
 */
public final class Ratio {

    public static final Ratio CONSTANT_1 = new Ratio(1);
    public static final Ratio CONSTANT_0 = new Ratio(0);

    public static Ratio convertFrom(double x) {
        return new Ratio(x);
    }
    private double value;

    public Ratio(double value) {
        if (value < 0 || value > 1) {
            throw new RuntimeException("Invalid ratio " + value + ". Must be between 0 and 1.");
        } else {
            this.value = value;
        }
    }

    public double getValue() {
        return value;
    }

    public double getOppositeValue() {
        return 1 - value;
    }
}
