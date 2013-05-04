/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author Aiste
 */
public class GameOverMenu extends MenuHandler implements KeyListener{
    
    private boolean playerOneLost;
    private ImageIcon icon;
    private MainWindow mainWindow;
    private JLayeredPane invoker;
    private String text;
    
    public GameOverMenu(MainWindow mw, final JLayeredPane invoker,boolean playerOneMode, boolean playerOneLost, String power){
      super(invoker);
        this.mainWindow = mw; 
      this.invoker = invoker;
        
       setLayout(new BoxLayout(this,BoxLayout.X_AXIS)); 
        
        this.playerOneLost  = playerOneLost; 
        if(playerOneLost) {
            icon = new ImageIcon("resources/endGame.gif");
        } else {
            ///Use the one for dying bunny!!!!!!!!
            icon = new ImageIcon("resources/endGame.gif");   
        }
       
        Image img = icon.getImage();
        //load the game over gif and scale it to fit in the game over dialog
        Image newimg = img.getScaledInstance(330, 300, java.awt.Image.SCALE_DEFAULT);
        ImageIcon newIcon = new ImageIcon(newimg);
        JLabel image = new JLabel(newIcon);
       
       
        add(image);
        add(Box.createRigidArea(new Dimension(10,0)));
 
        addKeyListener(this);
        requestFocus();
        
        
        if(playerOneMode){
            text = "The Reactor has failed, " + mainWindow.getUserName() + "! You generated "
                + power + " of power.";
        } else if (playerOneLost){
            text = "The Reactor has not survived the bunny attacks! " + mainWindow.getUserName() + ", you generated "
                + power + " of power.";    
        }
        
        JLabel forTheText = new JLabel("<html><p>" +text + "</p></html>");        
               
       // forTheText.setPreferredSize(new Dimension(50, 50));
       // forTheText.setMaximumSize(new Dimension(50, 50));
       // forTheText.setMinimumSize(new Dimension(50, 50));
        //forTheText.setText("The Reactor has failed, " + mainWindow.getUserName() + "! \n" + "You generated "
        //        + power + " of power." + "\n");
        add(forTheText, BorderLayout.EAST);
         setBounds(200, 200, 600, 300);
        
            /*
        ImageIcon icon = new ImageIcon("resources/endGame.gif");
        Image img = icon.getImage();
        // load the game over gif and scale it to fit in the game over dialog
        Image newimg = img.getScaledInstance(330, 300, java.awt.Image.SCALE_DEFAULT);
        ImageIcon newIcon = new ImageIcon(newimg);
        //create an option pane for the game over dialog
        final JOptionPane optionPane = new JOptionPane(
                "The Reactor has failed, " + simulator.getUsername() + "! \n" + "You generated "
                + simulator.energyGenerated() + " of power." + "\n"
                + "Would you like to start a new game?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION, newIcon);
        //create a new gameOver dialog, passing the option pane to it
         
                

       */
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        mainWindow.changeMenu(new MainMenu(mainWindow, invoker));
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
}
