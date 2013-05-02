/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

/**
 *
 * @author Aiste
 */
public class Options extends JPanel{
    private MainWindow mainWindow;
    private final int EASY = 1;
    private final int NORMAL = 2;
    private final int HARD = 3;
    private JPanel invoker;
    private JToggleButton easy, normal, hard;
    

    public Options(MainWindow mw, final JPanel invoker) {
        this.mainWindow = mw;
        this.invoker = invoker;
        
        
        easy = new JToggleButton("easy");
        normal = new JToggleButton("normal");
        hard = new JToggleButton("hard");
        setSelected();
        
        easy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(EASY);
                setSelected();              
            }
        });
        
        
        normal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(NORMAL);
                setSelected(); 
            }
        });
        
        
        hard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.setDifficulty(HARD);
                setSelected(); 
            }
        });
        
        JToggleButton sound = new JToggleButton("sound");
        sound.setSelected(mainWindow.getMusic());
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
               mainWindow.changeMenu(new HelpScreen(mainWindow, invoker), invoker);
            }
        });
         
        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                mainWindow.changeMenu(new MainMenu(mainWindow, invoker), invoker );
            }
        });
        
      add(help);   
      add(easy);
      add(normal);
      add(hard);
      add(sound);
      add(back);
     
     }
      
    private void setSelected(){
      easy.setSelected(false);
      normal.setSelected(false);
      hard.setSelected(false);
      if(mainWindow.getDifficulty()==EASY){
          easy.setSelected(true);
      } else if (mainWindow.getDifficulty() == NORMAL){
          normal.setSelected(true);
      } else {
          hard.setSelected(true);
      }
      
    }
    
}
