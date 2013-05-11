/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.newsim.utils.OptionsHolder;
import dab.engine.simulator.Simulator;
import dab.gui.gamepanel.GamePanel;
import dab.gui.gamepanel.UIComponent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author eduard
 */
public class SinglePlayerPanel extends GamePanel implements MouseListener{
    SinglePlayerSimulator simulator;
    
    public SinglePlayerPanel(SinglePlayerSimulator sim) {
        super(sim);
        addMouseListener(this);
       // simulator = sim;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
                
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if(OptionsHolder.getInstance().isGodModeOn()) {
            for (UIComponent uc : uiComponents) {         
                if(uc.getBounds().contains(e.getLocationOnScreen())){
                    uc.getComponent().fail(1);
                }
            }
        }          
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
