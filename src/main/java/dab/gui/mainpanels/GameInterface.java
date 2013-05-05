package dab.gui.mainpanels;

import dab.bigBunny.BunnyController;
import dab.bigBunny.Environment;
import dab.bigBunny.HitBoundsController;
import dab.bigBunny.TwoPlayerScreen;
import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.simulator.FailMode;
import dab.engine.simulator.GameOverException;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.gui.application.MainWindow;
import dab.gui.auxpanels.ButtonPanel;
import dab.gui.auxpanels.InfoPanel;
import dab.gui.auxpanels.ObamaPanel;
import dab.gui.gamepanel.GameOver;
import dab.gui.gamepanel.GamePanel;
import dab.gui.sound.Sounds;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

public class GameInterface extends JPanel implements KeyListener {

    private SinglePlayerSimulator simulator;
    private MainWindow mainWindow;
    private Sounds music;
    public final int MAX_SIZE_WIDTH = 1366;
    public final int MAX_SIZE_HEIGHT = 768;
    private GamePanel gamePanel;
    private ObamaPanel obamaPanel;
    private ButtonPanel buttonPanel;
    private InfoPanel infoPanel;
    private Timer animator;
    public static final Pressure CONDENSER_WARNING_PRESSURE = new Pressure(25530000);
    private GameOver gameOver;
    private boolean onePlayerMode;
    private BunnyController controller;
    private Environment environment;
    private HitBoundsController hitboundsController;

    public static GameInterface instance() {
        throw new UnsupportedOperationException();

    }

    public GameInterface (MainWindow mainWindow, SinglePlayerSimulator simulator, boolean onePlayerMode) {
        this.mainWindow = mainWindow;
        this.simulator = simulator;
        this.onePlayerMode = onePlayerMode;
        
        music = new Sounds("resources/music/backgroundSound.wav", true);
                   
        
        
        setupPanels();
        setupTimer();
     
        
        // rock and roll baby!
        animator.start();
        addKeyListener(this);      
    }
    
    private void setupPanels() {       
        setLayout(new BorderLayout());
        JSplitPane topLevelSplitPane, leftPane, rightPane;
        
        topLevelSplitPane = new JSplitPane();
        topLevelSplitPane.setDividerSize(5);
        //topLevelSplitPane.setResizeWeight(0.2);
        
        leftPane = new JSplitPane();
        leftPane.setDividerSize(5);
        leftPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        rightPane = new JSplitPane();
        rightPane.setDividerSize(5);
        rightPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        rightPane.setResizeWeight(0.5);
        
        if(onePlayerMode) {
            gamePanel = new SinglePlayerPanel(simulator);
        } else {            
            environment = new Environment();
            hitboundsController = new HitBoundsController();
            controller = new BunnyController(environment, hitboundsController, new Point(100, 100));           
            //gamePanel = new TwoPlayerScreen(simulator, environment, hitboundsController, controller); 
        }
         
        obamaPanel = new ObamaPanel(simulator);
        infoPanel = new InfoPanel(simulator);
        buttonPanel = new ButtonPanel(simulator);
        
                
        leftPane.setLeftComponent(gamePanel);
        leftPane.setRightComponent(obamaPanel);
        rightPane.setLeftComponent(infoPanel);
        rightPane.setRightComponent(buttonPanel);        
        
        topLevelSplitPane.setLeftComponent(leftPane);
        topLevelSplitPane.setRightComponent(rightPane);
        add(topLevelSplitPane);  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.startForward();
                break;
            case KeyEvent.VK_LEFT:
                controller.startRotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controller.startRotateRight();
                break;
            case KeyEvent.VK_DOWN:
                controller.startBrake();
                break;
            case KeyEvent.VK_SPACE:
                environment.startSoftwareFailure();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.stopForward();
                break;
            case KeyEvent.VK_LEFT:
                controller.stopRotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controller.stopRotateRight();
                break;
            case KeyEvent.VK_DOWN:
                controller.stopBrake();
                break;
            case KeyEvent.VK_ESCAPE:
                handelEscape();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {   } //Do nothing
    
    private void showGameOverScreen() {
        animator.stop();
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
        gameOver = new GameOver(optionPane);
        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                int value = (Integer) e.getNewValue();
                //if the user clicks the "Yes" option
                if ((value == 0)) {
                    //create a new simulator with the old username
                    String old_username = simulator.getUsername();
                    SinglePlayerSimulator new_simulator = new SinglePlayerSimulator();
                    new_simulator.setUsername(old_username);
                    mainWindow.startGame(new_simulator, true);
                }
                if ((value == 1)) {
                    mainWindow.showMenu();
                }
            }
        });

    }

    private void setupTimer() {

        ActionListener taskStep = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    requestFocus();
                    simulator.step();
                    infoPanel.update();
                    obamaPanel.update();
                    gamePanel.updateComponents();
                    buttonPanel.update();
                    handleMusic();
                          
                    // soon there will be no need to screenUpdate
                    //screenUpdate();
                    //repaint();
                } catch (GameOverException e) {

                    // stop the game loop when game over
                    animator.stop();
                    music.interrupt();
                    showGameOverScreen();
                }
            }
        };
        //game loop updates every 100 ms
        animator = new Timer(100, taskStep);
    }
    
    public void handleMusic(){    
        try {      
            if(music.isAlive() && !mainWindow.getMusic()) {
                music.stopIt();
            } else if(!music.isAlive() && mainWindow.getMusic()&&animator.isRunning()) {
                music.start();
               
            } 
        } catch (IllegalThreadStateException e) {
            //do nothing
        }       
    }

    public static void rewrite() {
        throw new UnsupportedOperationException();
    }

    public void screenUpdate() {
        
        if (getFailed("Turbine") || (simulator.getSoftFailReport().getFailMode() != FailMode.WORKING)) {
            // if there is a software failure or the turbine has failed,
            // change the control rod slider to its actual position
            // why? no idea....
            buttonPanel.setSliderValue((int) simulator.controlRodPosition().points());
        }
    }

    private boolean getFailed(String component) {
        if (simulator.listFailedComponents().length > 0) {
            for (String ChangingComponent : simulator.listFailedComponents()) {
                if (ChangingComponent.equals(component)) {
                    return true;
                }
            }
        }
        return false;
    }

    //@Override
    /*public void update(UserCommands command, int parameter) {
        try {
            switch (command) {
                case TURNON:
                    simulator.changePumpState(parameter, true);
                    break;
                case TURNOFF:
                    simulator.changePumpState(parameter, false);
                    break;
                case OPEN:
                    simulator.changeValveState(parameter, true);
                    break;
                case CLOSE:
                    simulator.changeValveState(parameter, false);
                    break;
                case MOVE:
                    simulator.moveControlRods(new Percentage(parameter));
                    break;
            }
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        } catch (CannotControlException e) {
        }

        screenUpdate();
    }*/

    private void handelEscape()  {
        // pause the game loop when esc is pressed
        
        animator.stop();
        music.stopIt();
        mainWindow.changeMenu(new MainMenu(mainWindow, gamePanel), gamePanel);       
        
    }

    public void stopMusic() {
        music.interrupt();
    }
    
    public void resume() {
    System.out.println("resume");
        animator.start();
        
  //      handleMusic();
    }
    
    

}
