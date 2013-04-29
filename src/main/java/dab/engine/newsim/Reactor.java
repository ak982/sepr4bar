/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.CannotRepairException;
import dab.engine.simulator.views.ReactorView;
import dab.engine.utilities.Percentage;
import dab.engine.utilities.Pressure;
import dab.engine.utilities.Temperature;

/**
 *
 * @author eduard
 */
public class Reactor extends Container {

    public static final double ROOM_TEMP = 300;
    
    Percentage rodPosition;
    
    public Reactor(double volume) {
        super(new Water(ROOM_TEMP, 1), new Steam(ROOM_TEMP, 1), 1);
    }

    public void moveControlRods(Percentage extracted) {
        this.rodPosition = extracted;
    }

    public Percentage controlRodPosition() {
        return rodPosition;
    }
    
}
