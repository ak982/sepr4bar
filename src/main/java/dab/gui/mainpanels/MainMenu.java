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
                mainWindow.stopMusic();
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
                setVisible(false);
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
        
        /*

            JMenuItem save_menu = new JMenuItem();
            save_menu.setOpaque(true);
            save_menu.setIconTextGap(0);
            save_menu.setContentAreaFilled(false);
            save_menu.setIcon(new ImageIcon("resources/menu/saveButton.png"));
            save_menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        simulator.saveGame();
                        JOptionPane.showMessageDialog(null, "Game Saved");
                    } catch (JsonProcessingException e1) {
                        e1.printStackTrace();
                    }

                }
            });
            JMenuItem load_menu = new JMenuItem();
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
                    JMenuItem save_menu = new JMenuItem();
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
                            simulator.loadGame(sampleJList.getSelectedIndex());
                            popupMenu.setVisible(false);
                            //set the control rods in the control panel to the new rods value
                            buttonPanel.setSliderValue(2 * Integer.parseInt(simulator.controlRodPosition().toString()));
                            animator.start();
                        }
                    });
                    getParent().add(popupMenu);
                    popupMenu.add(listPane);
                    popupMenu.add(load);
                    add(popupMenu);
                    popupMenu.show(GameInterface.this, 650, 350);
                    animator.stop();
                }
            });

            */
        
    }
}
