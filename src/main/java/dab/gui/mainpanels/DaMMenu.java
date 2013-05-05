
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


/**
 * The panel to be used as a background for menus before the 
 * game starts
 */
public class DaMMenu extends JLayeredPane {
    private MainMenu mainMenu;
    private MainWindow mw;
    
    
    public DaMMenu(MainWindow mw) {        
        //setLayout(new GridBagLayout());      
       // setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); 
        this.mw = mw;
       
        setVisible(true); 
        
        mainMenu = new MainMenu(mw, this);
        mw.changeMenu(mainMenu);       
    }

}
