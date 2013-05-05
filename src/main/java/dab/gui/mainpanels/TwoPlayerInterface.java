/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.bigBunny.BunnyController;
import dab.bigBunny.Environment;
import dab.bigBunny.HitBoundsController;
import dab.bigBunny.TwoPlayerScreen;
import dab.engine.newsim.TwoPlayerSimulator;
import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import dab.gui.gamepanel.GamePanel;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aiste
 */
public class TwoPlayerInterface extends GameInterface{
     
    private TwoPlayerScreen gamePanel;
     private BunnyController controller;
    private Environment environment;
    private HitBoundsController hitboundsController;
        private TwoPlayerSimulator simulator;
    private MainWindow mainWindow;
    
    public TwoPlayerInterface(MainWindow mainWindow, TwoPlayerSimulator simulator) {
        super(mainWindow, simulator);
            environment = new Environment();
            hitboundsController = new HitBoundsController();
            controller = new BunnyController(environment, hitboundsController, new Point(100, 100));           
            gamePanel = new TwoPlayerScreen(simulator, environment, hitboundsController, controller);
            this.mainWindow = mainWindow;
            this.simulator = simulator;
            super.setupPanels();
    }

    @Override
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    @Override
    protected void showGameOverMenu() {
        boolean playerTwoLost = controller.getHealth() <= 0;
        mainWindow.changeMenu(new GameOverMenu(mainWindow, getGamePanel(), false, playerTwoLost, simulator.energyGenerated().toString()));
    }

    @Override
    protected void step() {
        super.step();
        controller.step();
        environment.step();
        gamePanel.repaint();        
    }
    
     @Override
    public void keyTyped(KeyEvent e) {       
    }

    @Override
    public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.startForward();
                break;
            case KeyEvent.VK_LEFT:
                controller.startRotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controller.startRotateRight();
                break;
            case KeyEvent.VK_DOWN:
                controller.startBrake();
                break;
            case KeyEvent.VK_SPACE:
                environment.startSoftwareFailure();
                break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
         switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.stopForward();
                break;
            case KeyEvent.VK_LEFT:
                controller.stopRotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controller.stopRotateRight();
                break;
            case KeyEvent.VK_DOWN:
                controller.stopBrake();
                break;
    }
    }

}
