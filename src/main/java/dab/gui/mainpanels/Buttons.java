/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import javax.swing.JButton;

/**
 *
 * @author Aiste
 */
public class Buttons extends JButton{
    
    public Buttons(String location){
        JButton button = new JButton(location);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
    }
}
