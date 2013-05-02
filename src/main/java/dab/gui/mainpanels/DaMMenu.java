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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * The panel to be used as a background for mainMenu
 */
public class DaMMenu extends JPanel implements KeyListener, MouseListener{
    private MainMenu mainMenu;
    private MainWindow mw;
    
    
    public DaMMenu(MainWindow mw) {        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));      
        this.mw = mw;
        mainMenu = new MainMenu(mw, this);
        DaMMenu.this.add(mainMenu);        
        
        setBackground(Color.BLACK);
        setVisible(true); 
        addKeyListener(this);
        addMouseListener(this);
    }
    
    public void showMenu() {       
        mainMenu.show(this, 300, 300);
        DaMMenu.this.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
         showMenu();   
    }

    @Override
    public void keyPressed(KeyEvent e) {
  
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        showMenu();  
        System.out.println("click");
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
