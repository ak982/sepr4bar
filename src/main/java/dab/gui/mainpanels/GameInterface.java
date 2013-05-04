package dab.gui.mainpanels;

import dab.engine.simulator.CannotControlException;
import dab.engine.simulator.FailMode;
import dab.engine.simulator.GameOverException;
import dab.engine.simulator.KeyNotFoundException;
import dab.engine.simulator.Simulator;
import dab.engine.simulator.UserCommands;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

public abstract class GameInterface extends JPanel implements KeyListener {

    private Simulator simulator;
    private MainWindow mainWindow;
    private Sounds music;
    public final int MAX_SIZE_WIDTH = 1366;
    public final int MAX_SIZE_HEIGHT = 768;
    private ObamaPanel obamaPanel;
    private ButtonPanel buttonPanel;
    private InfoPanel infoPanel;
    private Timer animator;
    public static final Pressure CONDENSER_WARNING_PRESSURE = new Pressure(25530000);
    private GameOver gameOver;
    
    private int counter;

    public static GameInterface instance() {
        throw new UnsupportedOperationException();

    }

    public GameInterface (MainWindow mainWindow, Simulator simulator) {
        this.mainWindow = mainWindow;
        this.simulator = simulator;
        
        counter = 0;
        music = new Sounds("resources/music/backgroundSound.wav", true);
  
        ActionListener taskStep = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               step();
            }
        };
        animator = new Timer(1000/30, taskStep);
        
        // rock and roll baby!
        animator.start();
        addKeyListener(this);      
    }
    
    protected void setupPanels() {       
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
        rightPane.setResizeWeight(0.2);
         
        obamaPanel = new ObamaPanel(simulator);
        infoPanel = new InfoPanel(simulator);
        buttonPanel = new ButtonPanel(simulator);
                     
        leftPane.setLeftComponent(getGamePanel());
        leftPane.setRightComponent(obamaPanel);
        rightPane.setLeftComponent(infoPanel);
        rightPane.setRightComponent(buttonPanel);        
        
        topLevelSplitPane.setLeftComponent(leftPane);
        topLevelSplitPane.setRightComponent(rightPane);
        add(topLevelSplitPane);  
    }

    @Override
    public void keyPressed(KeyEvent e) {    
    }

    @Override
    public void keyReleased(KeyEvent e) {
          if(e.getKeyCode()== KeyEvent.VK_ESCAPE){
                handelEscape();
            }   
    }

    @Override
    public void keyTyped(KeyEvent e) {   } //Do nothing
    
    private void showGameOverScreen() {
        animator.stop();
        mainWindow.setGameOver(true);
        showGameOverMenu();           
    }

    protected abstract void showGameOverMenu();
    
    protected void step() {
        try {
           
            if (counter % 3 == 0) {
                simulator.step();
                infoPanel.update();
                obamaPanel.update();
                getGamePanel().updateComponents();
                buttonPanel.update();
                counter = 0;
            }
            handleMusic();
            counter++;
             requestFocus();
            // soon there will be no need to screenUpdate
            //screenUpdate();
            //repaint();
        } catch (GameOverException e) {

            // stop the game loop when game over
            animator.stop();
            music.interrupt();
            showGameOverScreen();
        }
        
         addKeyListener(this);
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
    public void update(UserCommands command, int parameter) {
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
    }

    private void handelEscape()  {
        // pause the game loop when esc is pressed
        
        animator.stop();
        music.stopIt();
        mainWindow.changeMenu(new MainMenu(mainWindow, getGamePanel()));       
        
    }

    public void stopMusic() {
        music.interrupt();
    }
    
    public void resume() {
    System.out.println("resume");
        animator.start();
        
  //      handleMusic();
    }
    
    protected abstract GamePanel getGamePanel();

}
