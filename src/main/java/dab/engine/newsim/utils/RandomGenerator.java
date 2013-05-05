/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Random;

/**
 *
 * @author eduard
 */
public class RandomGenerator {
    @JsonProperty
    private Random generator;
    
    public RandomGenerator() {
        generator = new Random();
    }
    
    public void setSeed(long seed) {
        generator.setSeed(seed);
    }
    
    public int rollInt(int minInt, int maxInt) {
        return generator.nextInt(maxInt - minInt + 1) + minInt;
    }
    
    public boolean rollTrueFalse(double probability) {
        return (generator.nextDouble() < probability);
    }
    
    public double rollDouble() {
        return generator.nextDouble();
    }
    
}
