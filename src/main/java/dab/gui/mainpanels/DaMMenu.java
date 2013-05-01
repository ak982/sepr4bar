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
 * The main menu for the game, has new game, load game, two_player and quit buttons.
 * New game points onto an instance of Player.
 * Load Game displays a menu of existing save games, and loads a selected game.
 * Help ...
 * Quit exits the JRE.
 * 
 * @author Team Haddock
 *
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
