/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import com.fasterxml.jackson.core.JsonProcessingException;
import dab.engine.persistence.FileSystem;
import dab.engine.simulator.Simulator;
import dab.gui.application.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author Aiste
 */
public class MainMenu extends MenuHandler{
    private MainWindow mainWindow;
    private JLayeredPane invoker;
    
    public MainMenu(MainWindow mw, final JLayeredPane invoker) {
        
        super(invoker);
        
        this.mainWindow = mw;
        this.invoker = invoker;
        
           
        JButton new_game = new JButton(new ImageIcon("resources/menu/NewGameButton.png") {});         
        new_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
               startGame(true);
            }
        });

        JButton two_player = new JButton("two player");
        two_player.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){                
                startGame(false);
            }
        });
        
       
        JButton save_menu = new JButton();
        save_menu.setOpaque(true);
        save_menu.setIconTextGap(0);
        save_menu.setContentAreaFilled(false);
        save_menu.setIcon(new ImageIcon("resources/menu/saveButton.png"));
        save_menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainWindow.saveGame();
                    JOptionPane.showMessageDialog(null, "Game Saved");
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }

            }
        });
        
         
            JButton load_menu = new JButton();
            load_menu.setOpaque(true);
            load_menu.setIconTextGap(0);
            load_menu.setContentAreaFilled(false);
            load_menu.setIcon(new ImageIcon("resources/menu/LoadGameButton.png"));
            load_menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<String> saved_games_array = new ArrayList<String>();
                    String[] saved_games = FileSystem.listSaveGames();
                    for (String game : saved_games) {
                        String[] bits = game.split("\\.");
                        game = game.replace(".", "?");
                        Timestamp t = new Timestamp(Long.parseLong(bits[3]));
                        Date d = new Date(t.getTime());
                        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        //an array of the saved games in the format : username/date of save
                        saved_games_array.add(bits[2] + " " + date.format(d));
                    }
                    JButton save_menu = new JButton();
                    save_menu.setOpaque(true);
                    save_menu.setIconTextGap(0);
                    save_menu.setContentAreaFilled(false);
                    save_menu.setIcon(new ImageIcon("resources/menu/LoadGameButton.png"));

                    //a jlist of saved games to be used
                    final JList sampleJList = new JList(saved_games_array.toArray(new String[saved_games_array.size()]));
                    sampleJList.setVisibleRowCount(8);
                    //make a the jlist scrollable
                    JScrollPane listPane = new JScrollPane(sampleJList);

                    final JPopupMenu popupMenu = new JPopupMenu();

                    popupMenu.setBorder(BorderFactory.createEmptyBorder());
                    JButton load = new JButton("Load");
                    load.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //load the selected game
                            Simulator simulator = new Simulator();
                            simulator.loadGame(sampleJList.getSelectedIndex());
                            mainWindow.startGame(simulator, true);
                            setVisible(false);
                            mainWindow.removeMenu(MainMenu.this, invoker);
                            popupMenu.setVisible(false);
                        }
                    });
                    getParent().add(popupMenu);
                    popupMenu.add(listPane);
                    popupMenu.add(load);
                    add(popupMenu);
                    popupMenu.show(mainWindow, 650, 350);      
                }
            });        

        JButton options = new JButton("options");
        options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){                
                mainWindow.changeMenu(new Options(mainWindow, invoker));                
            }
        });
     
        JButton resume = new JButton("resume");
        resume.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){                
               mainWindow.removeMenu(MainMenu.this, invoker);
               mainWindow.resume();             
            }
        });
     
        
        JButton exit_game = new JButton("exit");
        exit_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
   
       boolean addResume = false;
       boolean addSave = false;
       
       if(!(invoker instanceof DaMMenu)) {
            if(!mainWindow.getGameOver()){        
                addResume = true;
                if(invoker instanceof SinglePlayerPanel){  
                    addSave = true;
                }
            }
        }
        
        if(addResume) {
            add(resume);
        }
                
        add(new_game);
        add(two_player);
        if (addSave) {
            add(save_menu);
        }
        add(options);
        add(load_menu);
        add(exit_game);
        
        
        setBounds(400, 200, (int)new_game.getMinimumSize().getWidth(),
                (int)new_game.getMinimumSize().getHeight()*getComponentCount());
        
        
       

            
          
        
    }
    
    private void startGame(boolean playerMode){
         mainWindow.stopMusic();
         Simulator sim = new Simulator();         
         mainWindow.changeMenu(new NameMenu(sim, mainWindow, invoker, playerMode));
    }
    
}
