/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public class AverageBuffer {
    private double[] list;
    int size, index, sum;
    public AverageBuffer(int size) {
        list = new double[size];
        this.size = size;
        index = 0;
        sum = 0;
    }
    
    public void addElement(double x) {
        sum -= list[index];
        sum += x;
        list[index] = x;
        index++;
        if (index == size) {
            index = 0;
        }
    }
    
    public double getAverage() {
        return sum / size;
    }
}
