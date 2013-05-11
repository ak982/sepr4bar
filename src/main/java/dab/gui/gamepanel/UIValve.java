/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.gamepanel;

import dab.engine.newsim.interfaces.FailableComponentView;
import dab.engine.newsim.interfaces.ValveView;
//import dab.engine.simulator.views.ValveView;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Aiste
 */
public class UIValve extends UIComponent{
    public UIValve(GamePanel parent, FailableComponentView component, Point location, String stdImgPath, String dmgImgPath){
        super(parent, component, location, stdImgPath, dmgImgPath);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (((ValveView)component).getOpen()) {
            g.drawImage(damagedImage, 0, 0, this);
        } else {
            g.drawImage(standardImage, 0, 0, this);
        }
    }
    
}
