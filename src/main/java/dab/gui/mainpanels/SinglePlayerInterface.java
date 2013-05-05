/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.engine.newsim.AbstractSimulator;
import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import dab.gui.auxpanels.ControlPanel;
import dab.gui.auxpanels.InfoPanel;
import dab.gui.auxpanels.ObamaPanel;
import dab.gui.auxpanels.SinglePlayerObama;
import dab.gui.gamepanel.GamePanel;

/**
 *
 * @author Aiste
 */
public class SinglePlayerInterface extends GameInterface{
   
    private SinglePlayerPanel gamePanel;    
    private SinglePlayerSimulator simulator;
    private SinglePlayerObama obamaPanel;
    private InfoPanel infoPanel;
    private ControlPanel buttonPanel;

    public SinglePlayerInterface(MainWindow mainWindow, SinglePlayerSimulator simulator) {
        super(mainWindow);
        this.simulator = simulator;
        gamePanel = new SinglePlayerPanel(simulator); 
        obamaPanel = new SinglePlayerObama(simulator);
        buttonPanel = new ControlPanel(simulator);
        infoPanel   = new InfoPanel(simulator);
        super.setupPanes();
        start();
    }

    @Override
    protected void showGameOverMenu() {
        mainWindow.changeMenu(new GameOverMenu(mainWindow, getGamePanel(),
                true, true, simulator.energyGenerated().toString()));
    }

    @Override
    protected AbstractSimulator getSimulator() {
        return simulator;
    }

    @Override
    protected SinglePlayerPanel getGamePanel() {
        return gamePanel;
    }
    
    @Override
    protected SinglePlayerObama getObamaPanel() {
        return obamaPanel;
    }

    @Override
    protected ControlPanel getButtonPanel() {
        return buttonPanel;
    }
    
    @Override
    protected InfoPanel getInfoPanel() {
        return infoPanel;
    }
    
}
