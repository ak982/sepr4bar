/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dab.engine.newsim.components.Condenser;
import dab.engine.newsim.components.Pump;
import dab.engine.newsim.components.Reactor;
import dab.engine.newsim.components.Turbine;
import dab.engine.newsim.components.Valve;
import dab.engine.newsim.interfaces.FailableObject;
import dab.engine.simulator.GameOverException;
import java.util.ArrayList;

/**
 *reactors
 * @author eduard
 * Excuse my seemingly duplication of code, but all of the components are 
 * sufficiently different and thus can not be grouped together
 */
public class PowerPlant {
    
    @JsonProperty
    private Reactor reactor;
    
    @JsonProperty
    private Condenser condenser;
    
    @JsonProperty
    private Turbine turbine;
    
    @JsonProperty
    private ArrayList<Pump> pumps;
    
    @JsonProperty
    private ArrayList<Valve> valves;
    
    public PowerPlant() {
        // setup the arrayLists
        pumps      = new ArrayList<>(2);
        valves     = new ArrayList<>(2);
        
        // setup the components
        reactor = new Reactor("Reactor", 10, 1);
        condenser = new Condenser("Condenser", 10, 0.7);
        turbine = new Turbine("Turbine");
        
        Pump pump = new Pump("Condenser to Reactor Pump", 0.3);
        Valve reactorToTurbine = new Valve("Valve 1");
        Valve condenserToPump  = new Valve("Valve 2");
        
        // make the connections
        reactor.setOutputComponent(reactorToTurbine);
        reactorToTurbine.setOutputComponent(turbine);
        turbine.setOutputComponent(condenser);
        condenser.setOutputComponent(condenserToPump);
        condenserToPump.setOutputComponent(pump);
        pump.setOutputComponent(reactor);
        
        // add them to the array lists
        pumps.add(pump);
        valves.add(reactorToTurbine);
        valves.add(condenserToPump);
    }
    
    // FIXME: massive hack to fix loading (jackson and cycled-abstract)
    public void resetConnections() {
        reactor.setOutputComponent(valves.get(0));
        valves.get(0).setOutputComponent(turbine);
        turbine.setOutputComponent(condenser);
        condenser.setOutputComponent(valves.get(1));
        valves.get(1).setOutputComponent(pumps.get(0));
        pumps.get(0).setOutputComponent(reactor);
    }
    
    public void step() throws GameOverException {
        reactor.step();
        condenser.step();
        turbine.step();
    }
    
    //<editor-fold desc="getters">
    public Reactor getReactor() {
        return reactor;
    }
    
    public Condenser getCondenser() {
        return condenser;
    }
    
    public Turbine getTurbine() {
        return turbine;
    }
    
    public ArrayList<Pump> getPumps() {
        return new ArrayList<>(pumps);
    }
    
    public ArrayList<Valve> getValves() {
        return new ArrayList<>(valves);
    }
    
    protected ArrayList<FailableObject> getFailableComponents() {
        ArrayList<FailableObject> failables = new ArrayList<>();
        failables.add(condenser);
        failables.add(condenser.getHeatSink());
        failables.add(turbine);
        failables.addAll(pumps);
        
        return failables;
    }
    //</editor-fold>
    
}
