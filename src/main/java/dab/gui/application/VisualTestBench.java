/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.application;

import dab.engine.newsim.PowerPlant;
import dab.engine.newsim.components.Condenser;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.components.Pump;
import dab.engine.newsim.components.Reactor;
import dab.engine.newsim.components.Turbine;
import dab.engine.newsim.utils.Water;
import dab.engine.simulator.GameOverException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author eduard
 */
public class VisualTestBench implements ActionListener {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                VisualTestBench vtb = new VisualTestBench();
                //vtb.setVisible(true);
                vtb.start();
            }
        });
        //VisualTestBench vtb = new VisualTestBench();
        //vtb.start();
        //System.out.println(vtb.reactor);
    }
    
    Timer timer;
    PowerPlant plant;
    
    JLabel rView, cView, tView, pView;
    
    public VisualTestBench() {
        System.out.println("Running");
        System.out.flush();
        timer = new Timer(1000 / Constants.TICKS_PER_SECOND, this);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        plant = new PowerPlant();
        
        //BoxLayout b = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        //setLayout(new FlowLayout());
        rView = new JLabel();
        cView = new JLabel();
        tView = new JLabel();
        pView = new JLabel();
        rView.setVisible(true);
        cView.setVisible(true);
        tView.setVisible(true);
        pView.setVisible(true);
        /*add(rView);
        add(cView);
        add(tView);
        add(pView);*/
        
        /*System.out.println("0.1 atm: " + (Water.getBoilingTemperature(10000)));
        System.out.println("1 atm:   " + (Water.getBoilingTemperature(100000)));
        System.out.println("10 atm:  " + (Water.getBoilingTemperature(1000000)));
        System.out.println("100 atm: " + (Water.getBoilingTemperature(10000000)));
        
        System.out.println("K 320: " + (Water.getBoilingPressure(327)));
        System.out.println("K 373: " + (Water.getBoilingPressure(374)));
        System.out.println("K 473: " + (Water.getBoilingPressure(474)));
        System.out.println("K 573: " + (Water.getBoilingPressure(574)));*/
        
        
        //System.exit(0);
        /*for (int i = 1; i < 400; ++i) {
            reactor.step();
            condenser.step();
        }
        condenser.toggleDebugMode();
        for (int i = 1; i < 10; ++i) {
            System.out.println(i);
            System.out.println(reactor.toString());
            System.out.println(condenser.toString());
            reactor.step();
            System.out.println(reactor.toString());
            System.out.println(condenser.toString());
            condenser.step();
        }*/
        
    }
    
    public void start() {
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
        plant.step();
        } catch (GameOverException e) {
            System.out.println("game over: " + e);
            timer.stop();
        }
        System.out.println(plant.getReactor());
        System.out.println(plant.getCondenser());
        
        //System.out.println("Turbine: " + plant.getTurbine().getLastEnergy());
        
        
        
        System.out.println();
        
        System.out.flush();
        
    }
    
}
