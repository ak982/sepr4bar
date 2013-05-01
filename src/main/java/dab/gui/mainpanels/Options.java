/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Aiste
 */
public class Options extends JPanel{
    MainWindow mainWindow;
    
    
    
    public Options(MainWindow mw) {
        this.mainWindow = mw;
     
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        JButton easy = new JButton("easy");
        easy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(1);
            }
        });
        
        JButton normal = new JButton("normal");
        normal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(2);
            }
        });
        
        JButton hard = new JButton("hard");
        hard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(3);
            }
        });
        
        JButton sound = new JButton("sound");
        sound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setMusic();
            }
        });
        
        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.showMenu();
            }
        });
        
        JButton help = new JButton("help");
         help.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
               mainWindow.showHelp();

            }
        });
        
      add(help);   
      add(easy);
      add(normal);
      add(hard);
      add(sound);
      add(back);
      
        
    }
    
    
    
    
    
    
    
    
}
