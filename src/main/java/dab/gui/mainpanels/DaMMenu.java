/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

/**
 *
 * @author eduard
 */

import dab.gui.application.MainWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


/**
 * The panel to be used as a background for mainMenu
 */
public class DaMMenu extends JPanel {
    private MainMenu mainMenu;
    private MainWindow mw;
    
    
    public DaMMenu(MainWindow mw) {        
        setLayout(new GridBagLayout());      
        this.mw = mw;
        setBackground(Color.BLACK);
        setVisible(true); 
        
       
        
        mainMenu = new MainMenu(mw, this);
        add(mainMenu);     
        //mainMenu.setAlignmentX(CENTER_ALIGNMENT);
        //mainMenu.setAlignmentY(CENTER_ALIGNMENT);
       
        
    }
  
    
}
