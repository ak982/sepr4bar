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
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * The panel to be used as a background for mainMenu
 */
public class DaMMenu extends JPanel {
    MainMenu mainMenu;
    
    
    public DaMMenu(MainWindow mw) {        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));      
        mainMenu = new MainMenu(mw);
        add(mainMenu);        
        setBackground(Color.BLACK);
        setVisible(true);      
    }
    
    public void showMenu() {
        mainMenu.show(DaMMenu.this, 300, 300);
    }
}
