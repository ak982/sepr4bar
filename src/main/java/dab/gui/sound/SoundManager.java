/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.sound;

/**
 *
 * @author eduard
 */
public class SoundManager {
    Sounds music;
    private SoundManager() {
        music = new Sounds("resources/music/backgroundSound.wav", true);
    }
    
}
