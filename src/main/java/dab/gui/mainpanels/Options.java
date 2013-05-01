/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

/**
 *
 * @author Aiste
 */
public class Options extends JPopupMenu{
    private MainWindow mainWindow;
    private final int EASY = 1;
    private final int NORMAL = 2;
    private final int HARD = 3;

    public Options(MainWindow mw) {
        this.mainWindow = mw;
        
        JButton easy = new JButton("easy");
        easy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(EASY);
            }
        });
        
        JButton normal = new JButton("normal");
        normal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(NORMAL);
            }
        });
        
        JButton hard = new JButton("hard");
        hard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(HARD);
            }
        });
        
        JButton sound = new JButton("sound");
        sound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setMusic();
            }
        });
           
        JButton help = new JButton("help");
         help.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
               mainWindow.changeMenu(new HelpScreen(mainWindow));
            }
        });
         
        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.changeMenu(new MainMenu(mainWindow));
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
