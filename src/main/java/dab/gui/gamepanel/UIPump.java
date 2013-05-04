/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.gamepanel;

import dab.engine.simulator.views.FailableComponentView;
import dab.engine.simulator.views.PumpView;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Aiste
 */
public class UIPump extends UIComponent{
    private String workingImgPath;
    private Image workingImage;
    private PumpView component;
    
    public UIPump(GamePanel parent, PumpView component, Point location, String stdImgPath, String dmgImgPath, String workingImgPath){
        super(parent, component, location, stdImgPath, dmgImgPath);
        this.workingImage = new ImageIcon(GamePanel.class.getResource(workingImgPath)).getImage();
        this.component = component;
                
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (component.hasFailed()) {
            g.drawImage(damagedImage, 0, 0, this);
        } else if(component.getStatus()){
            g.drawImage(workingImage, 0, 0, this);
        } else {
            g.drawImage(standardImage, 0, 0, this);
        }
    }
}
