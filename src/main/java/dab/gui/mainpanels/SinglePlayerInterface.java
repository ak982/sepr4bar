/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import dab.gui.gamepanel.GamePanel;

/**
 *
 * @author Aiste
 */
public class SinglePlayerInterface extends GameInterface{
   
    private GamePanel gamePanel;
    private Simulator simulator;
    private MainWindow mainWindow; 

    public SinglePlayerInterface(MainWindow mainWindow, Simulator simulator) {
        super(mainWindow, simulator);
        this.mainWindow = mainWindow;
        this.simulator = simulator;
        gamePanel = new SinglePlayerPanel(simulator); 
        super.setupPanels();
    }
 
    @Override
    protected GamePanel getGamePanel() {
        return gamePanel;
    }

    @Override
    protected void showGameOverMenu() {
    mainWindow.changeMenu(new GameOverMenu(mainWindow, getGamePanel(),
                true, true, simulator.energyGenerated().toString()));  
    }
    
    
}
