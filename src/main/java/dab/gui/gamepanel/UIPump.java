/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.gamepanel;

import dab.engine.newsim.interfaces.PumpView;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Aiste
 */
public class UIPump extends UIComponent{
    private Image workingImage;
    
    public UIPump(GamePanel parent, PumpView component, Point location, String stdImgPath, String dmgImgPath, String workingImgPath){
        super(parent, component, location, stdImgPath, dmgImgPath);
        this.workingImage = new ImageIcon(GamePanel.class.getResource(workingImgPath)).getImage();
                
    }
    
    @Override
    public PumpView getComponent() {
        return (PumpView)component;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (component.hasFailed()) {
            g.drawImage(damagedImage, 0, 0, this);
        } else if(getComponent().getStatus()){
            g.drawImage(workingImage, 0, 0, this);
        } else {
            g.drawImage(standardImage, 0, 0, this);
        }
    }
}
