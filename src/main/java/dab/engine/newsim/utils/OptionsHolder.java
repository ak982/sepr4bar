/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

import dab.gui.sound.Sounds;

/**
 * Singleton class
 * @author eduard
 */
public final class OptionsHolder {
    private static OptionsHolder instance = new OptionsHolder();
    
    public static OptionsHolder getInstance() {
        return OptionsHolder.instance;
    }
    
    private Difficulty difficulty;
    private boolean soundOn;
    private boolean godModeOn;
    private Sounds music;
    
    private OptionsHolder() {
        difficulty = Difficulty.EASY;
        soundOn = true;
        godModeOn = false;
        music = new Sounds("resources/music/backgroundSound.wav", true);
        music.start();
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;    
        restartSound();
        music.mute(!soundOn);
    }
    
    public void restartSound(){
        if(soundOn){
            music.stopIt();
        }
    }

    public boolean isGodModeOn() {
        return godModeOn;
    }

    public void setGodModeOn(boolean godModeOn) {
        this.godModeOn = godModeOn;
    }
}
