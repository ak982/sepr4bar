/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.application;

import dab.engine.simulator.Simulator;
import dab.gui.intro.DaIntro;
import dab.gui.mainpanels.DaMMenu;
import dab.gui.mainpanels.GameInterface;
import dab.gui.mainpanels.HelpScreen;
import dab.gui.mainpanels.Options;
import java.awt.Component;
import javax.swing.JFrame;


/**
 *
 * @author eduard
 */
public class MainWindow extends JFrame {
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow mw = new MainWindow();
                mw.setExtendedState(MainWindow.MAXIMIZED_BOTH);
                mw.setVisible(true);

                mw.showIntro();

            }
        });
    }
    private DaMMenu menu;
    private Component currentComponent = null;
    private Options options;
    private HelpScreen helpScreen;
    private int difficulty;
    private boolean music;
    private Simulator simulator;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        
        difficulty = 1;
        // create the menu
        menu = new DaMMenu(this);
        options = new Options(this);
        helpScreen = new HelpScreen(this);
        music = true;
        
    }

    public void showIntro() {
        DaIntro intro = new DaIntro(this);
        changeToPanel(intro);
    }

    public void showMenu() {
        changeToPanel(menu);
        menu.showMenu();
    }
    
    public void showOptions() {
        changeToPanel(options);
    }

      public void showHelp() {
          
        changeToPanel(helpScreen);
    }
    
    public void startSinglePlayer() {
        simulator = new Simulator();
        simulator.setUsername("willy-wanka");
        startSinglePlayer(simulator);        
    }
    
    public void startSinglePlayer(Simulator sim) {
        changeToPanel(new GameInterface(this, sim, true));
    }
    
    public void close() {
        setVisible(false);
        dispose();
    }

    public void startTwoPlayer() {
        simulator = new Simulator();
        changeToPanel(new GameInterface(this, simulator, false));
    }

    private void changeToPanel(Component p) {
        if (currentComponent != null) {
            currentComponent.setVisible(false);
            getContentPane().remove(currentComponent);
        }
        getContentPane().add(p);
        currentComponent = p;
        p.setVisible(true);
        
        // put the new component in focus
        p.requestFocusInWindow();

    }
    
    public void setDifficulty (int i) {      
        if(simulator != null){   
            simulator.setDifficulty(i);
        }
        difficulty = i;       
    }
    
    public int getDifficulty(){
        return difficulty;
    }
    
    public void setMusic() {
        music = !music;
    }
    
    public boolean getMusic(){
        return music;
    }
}