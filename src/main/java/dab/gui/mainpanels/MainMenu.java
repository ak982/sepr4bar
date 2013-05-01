/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

/**
 *
 * @author Aiste
 */
public class MainMenu extends JPopupMenu{
    private MainWindow mainWindow;
    
    
    
    public MainMenu(MainWindow mw) {
        this.mainWindow = mw;
        
        
        JButton new_game = new JButton(new ImageIcon("resources/menu/NewGameButton.png") {});           
        new_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                Simulator sim = new Simulator();
                sim.setUsername("muah");
                mainWindow.startSinglePlayer(sim);
                setVisible(false);
            }
        });

        JButton two_player = new JButton("two player");
        two_player.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){                
                mainWindow.startTwoPlayer();
                setVisible(false);
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
   
        add(new_game);
        add(two_player);
        add(options);
        add(exit_game);
        
    }
}
