/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
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
    private int difficulty;
    private boolean music;
    
    
    public Options(MainWindow mw) {
        this.mainWindow = mw;
        difficulty = 1;
        music = true;
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        JButton easy = new JButton("easy");
        easy.setContentAreaFilled(true);
        easy.setBorderPainted(true);
        easy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                difficulty = 1;
            }
        });
        
        JButton normal = new JButton("normal");
        normal.setContentAreaFilled(true);
        normal.setBorderPainted(true);
        normal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                difficulty = 2;
            }
        });
        
        JButton hard = new JButton("hard");
        hard.setContentAreaFilled(true);
        hard.setBorderPainted(true);
        hard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                difficulty = 3;
            }
        });
        
         JButton sound = new JButton("sound");
        sound.setContentAreaFilled(true);
        sound.setBorderPainted(true);
        sound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                music = !music;
            }
        });
        
      add(easy);
      add(normal);
      add(hard);
      add(sound);
        
    }
    
    public boolean sound() {
        return music;
    }
    
    
    
    
    
    
    
}
