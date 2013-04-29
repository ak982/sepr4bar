/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.mainpanels;

import dab.bigBunny.BunnyController;
import dab.bigBunny.Environment;
import dab.bigBunny.HitBoundsController;
import dab.engine.simulator.Simulator;
import dab.gui.gamepanel.GamePanel;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Aiste
 */
public class TwoPlayerPanel extends GamePanel{
    private HitBoundsController hitboundsController;
    private BunnyController bc;
   
    public TwoPlayerPanel (Simulator sim) {
        super(sim);
        
        Environment env = new Environment(getWidth(), getHeight());
        hitboundsController = new HitBoundsController();
        bc = new BunnyController(env, hitboundsController, new Point(100, 100));
        bc.setBounds(new Rectangle(getWidth(), getHeight()));
    
    }
    
}
