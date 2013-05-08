/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public enum Difficulty {
    EASY, MEDIUIM, HARD;
    public double asDouble() {
        if (this == EASY) {
            return 1;
        } else if (this == MEDIUIM) {
            return 2;
        } else {
            return 3;
        }
    }
}
