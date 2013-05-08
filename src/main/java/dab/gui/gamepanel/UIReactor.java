/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.gamepanel;

import dab.engine.newsim.interfaces.ReactorView;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

public class UIReactor extends UIComponent {
    protected Image rodsImage;
    private ReactorView component;

    public UIReactor(GamePanel parent, ReactorView component, Point location, String stdImgPath, String dmgImgPath, String controlRods) {
        super(parent, component, location, stdImgPath, dmgImgPath);
        
        this.rodsImage  = new ImageIcon(GamePanel.class.getResource(controlRods)).getImage();
        this.component = component;
        
        
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rodsPosition = (int)((component.controlRodPosition()).points()*0.6);
        System.out.println("rods " + rodsPosition);
        g.drawImage(rodsImage,0 ,-rodsPosition, this);
    }
    
}
