/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

/**
 *
 * @author eduard
 */

import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    MainWindow mainWindow;
    
    
    public DaMMenu(MainWindow mw) {
        this.mainWindow = mw;
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        JButton new_game = new JButton(new ImageIcon("resources/menu/NewGameButton.png") {});           
        new_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                Simulator sim = new Simulator();
                sim.setUsername("muah");
                mainWindow.startSinglePlayer(sim);
            }
        });

        JButton two_player = new JButton("two player");
        two_player.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                
                mainWindow.startTwoPlayer();
            }
        });
        
        
        JButton options = new JButton("options");
        options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                
                mainWindow.showOptions();
            }
        });
        
        
       
        
        
        JButton exit_game = new JButton("exit");
        exit_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
   
        
        setBackground(Color.BLACK);
        setVisible(true);
        setBackground(Color.BLACK);
        //Dimension dimension = new Dimension((this.getWidth()/2) - (new_game.WIDTH/2), 0);
        //add(Box.createRigidArea(dimension));
        //this.setAlignmentX(this.CENTER_ALIGNMENT);
        //new_game.setAlignmentX(CENTER_ALIGNMENT);
        add(new_game);
        add(two_player);
        add(options);
        add(exit_game);
    }
}
