/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.application;

import dab.engine.simulator.Simulator;
import dab.gui.intro.DaIntro;
import dab.gui.mainpanels.DaMMenu;
import dab.gui.mainpanels.GameInterface;
import dab.gui.mainpanels.MenuHandler;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


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
   // private DaMMenu menu;
    private Component currentComponent = null;
    private int difficulty;
    private final int DEFAULT_DIFFICULTY =1;    //to check with the one in options
    private boolean music;
    private Simulator simulator = null;
    private GameInterface gameInterface = null;
    private JPanel currentMenu = null;
    private String oldUserName=null; 
    private String oldUserName2=null;
    private boolean gameOver;
    
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        gameOver = false;
        difficulty = DEFAULT_DIFFICULTY;
        // create the menu
        
        music = true;      
    }
    
    public void showIntro() {
        DaIntro intro = new DaIntro(this);
        changeToPanel(intro);
    }

    public void showMenu() {
        DaMMenu menu = new DaMMenu(this);
        changeToPanel(menu);      
    }
    
    public void startGame(Simulator sim, boolean onePlayerMode) {
        simulator = sim;
        oldUserName = simulator.getUsername();
        if(!onePlayerMode&&simulator.getUsername2()!=null){
            oldUserName2 = simulator.getUsername2(); 
        }
        gameInterface = new GameInterface(this, sim, onePlayerMode);
        simulator.setDifficulty(difficulty);
        simulator.setPlayerMode(onePlayerMode);
        changeToPanel(gameInterface);
    }
    
    public void close() {
        setVisible(false);
        dispose();
    }

    public void changeToPanel(Component p) {
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
    
    public void changeMenu(MenuHandler p){
        if (currentMenu != null) {
            currentMenu.setVisible(false);
            p.getInvoker().remove(currentMenu);
        }
      
        
       //p.setLayout(new BoxLayout(invoker,BoxLayout.Y_AXIS)); 
        p.getInvoker().add(p, JLayeredPane.POPUP_LAYER);
        currentMenu = p;
        p.setVisible(true);         
        p.requestFocus();
    }
    
    public void removeMenu(JPanel p, JLayeredPane invoker){
        p.setVisible(false);
        invoker.remove(p);
        currentMenu = null;
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
        if(gameInterface!=null) {
            gameInterface.handleMusic();
        }
    }
    
    public boolean getMusic(){
        return music;
    }
    
    public void stopMusic(){
        if(gameInterface != null) {
            gameInterface.stopMusic();
        }
    }
    
    public void resume() {
        gameInterface.resume();
    }
    
    public String getUserName(){
        return oldUserName;
    }
    
    public String getUserName2(){
        return oldUserName2;
    }
    
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    
    public boolean getGameOver(){
        return gameOver;
    }
    
}