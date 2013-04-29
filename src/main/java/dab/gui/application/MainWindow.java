/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.application;

import dab.engine.simulator.Simulator;
import dab.gui.intro.DaIntro;
import dab.gui.mainpanels.DaMMenu;
import dab.gui.mainpanels.GameInterface;
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

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        
        
        // create the menu
        menu = new DaMMenu(this);
        
    }

    public void showIntro() {
        DaIntro intro = new DaIntro(this);
        changeToPanel(intro);
    }

    public void showMenu() {
        changeToPanel(menu);
    }

    public void startSinglePlayer() {
        Simulator sim = new Simulator();
        sim.setUsername("willy-wanka");
        startSinglePlayer(sim);
        
    }
    
    public void startSinglePlayer(Simulator sim) {
        changeToPanel(new GameInterface(this, sim, true));
    }
    
    public void close() {
        setVisible(false);
        dispose();
    }

    public void startTwoPlayer() {
        //BunnyInterface bi = new BunnyInterface();
        Simulator sim = new Simulator();
       // Environment env = new Environment(getWidth(), getHeight());
       // hitboundsController = new HitBoundsController();
       // BunnyController bc = new BunnyController(env, hitboundsController, new Point(100, 100));
        
        
        //TwoPlayerScreen tps = new TwoPlayerScreen(bc, env, sim);
        
        //changeToPanel(tps);
        changeToPanel(new GameInterface(this, sim, false));
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
}