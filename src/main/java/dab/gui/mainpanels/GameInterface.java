package dab.gui.mainpanels;

import dab.engine.newsim.AbstractSimulator;
import dab.engine.simulator.GameOverException;
import dab.gui.application.MainWindow;
import dab.gui.auxpanels.ControlPanel;
import dab.gui.auxpanels.InfoPanel;
import dab.gui.auxpanels.ObamaPanel;
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

    private Timer animator;
    private int counter;
    protected MainWindow mainWindow;
    protected JSplitPane leftPane, rightPane;

    public GameInterface(MainWindow mw) {
        this.mainWindow = mw;

        counter = 0;
        //FIXME: play music
        

        ActionListener taskStep = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    step();
                } catch (GameOverException e) {
                    // stop the game loop when game over
                    animator.stop();
                    mainWindow.setGameOver(true);
                    showGameOverMenu();
                }
            }
        };
        animator = new Timer(1000 / 30, taskStep);

        setupPanels();
    }

    protected void start() {
        // rock and roll baby!
        addKeyListener(this);
        animator.start();
        repaint();

    }

    protected void setupPanes() {
        leftPane.setLeftComponent(getGamePanel());
        leftPane.setRightComponent(getObamaPanel());
        rightPane.setLeftComponent(getInfoPanel());
        rightPane.setRightComponent(getButtonPanel());
    }

    private void setupPanels() {

        setLayout(new BorderLayout());

        JSplitPane topLevelSplitPane;

        topLevelSplitPane = new JSplitPane();
        //topLevelSplitPane.setDividerSize(5);

        leftPane = new JSplitPane();
        //leftPane.setDividerSize(5);
        leftPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        rightPane = new JSplitPane();
        //rightPane.setDividerSize(5);
        rightPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        rightPane.setResizeWeight(0.7);

        topLevelSplitPane.setLeftComponent(leftPane);
        topLevelSplitPane.setRightComponent(rightPane);
        add(topLevelSplitPane);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            handelEscape();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    } //Do nothing

    protected abstract void showGameOverMenu();

    protected abstract AbstractSimulator getSimulator();

    protected abstract GamePanel getGamePanel();

    protected abstract ObamaPanel getObamaPanel();

    protected abstract InfoPanel getInfoPanel();

    protected abstract ControlPanel getButtonPanel();

    protected void step() throws GameOverException {

        if (counter % 3 == 0) {
            getSimulator().step();

            getInfoPanel().update();
            getObamaPanel().update();
            getGamePanel().updateComponents();
            getButtonPanel().update();
            counter = 0;
        }
        counter++;
        requestFocusInWindow();


    }

    private void handelEscape() {
        // pause the game loop when esc is pressed

        animator.stop();
        mainWindow.changeMenu(new MainMenu(mainWindow, getGamePanel()));

    }

    public void resume() {
        animator.start();
    }
}
