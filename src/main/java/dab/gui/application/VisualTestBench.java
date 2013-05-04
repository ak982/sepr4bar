/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.application;

import dab.engine.newsim.components.Condenser;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.components.Pump;
import dab.engine.newsim.components.Reactor;
import dab.engine.newsim.components.Turbine;
import dab.engine.newsim.utils.Water;
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
    Reactor reactor;
    Condenser condenser;
    Turbine turbine;
    Pump pump;
    
    JLabel rView, cView, tView, pView;
    
    public VisualTestBench() {
        System.out.println("Running");
        System.out.flush();
        timer = new Timer(1000 / Constants.TICKS_PER_SECOND, this);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        reactor = new Reactor(1, 1);
        condenser = new Condenser(2, 1.5);
        condenser.toggleDebugMode();
        turbine = new Turbine();
        pump = new Pump(2500);
        
        reactor.setOutputComponent(turbine);
        turbine.setOutputComponent(condenser);
        condenser.setOutputComponent(pump);
        pump.setOutputComponent(reactor);
        
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
        reactor.step();
        condenser.step();
        
    }
    
    public void start() {
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        reactor.step();
        System.out.println(reactor);
        System.out.println(condenser);
        condenser.step();
        System.out.println(reactor);
        System.out.println(condenser);
        
        System.out.println("Turbine: " + turbine.getLastEnergy());

        System.out.println("TotalParticles: " + (
                reactor.getSteam().getParticleNr() + 
                reactor.getWater().getParticleNr() + 
                condenser.getSteam().getParticleNr() + 
                condenser.getWater().getParticleNr()
                ));
        
        
        
        System.out.println();
        
        System.out.flush();
        
    }
    
}
