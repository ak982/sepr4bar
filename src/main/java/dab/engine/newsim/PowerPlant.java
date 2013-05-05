/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PowerPlant {
    
    @JsonProperty
    private ArrayList<Reactor> reactors;
    
    @JsonProperty
    private ArrayList<Condenser> condensers;
    
    @JsonProperty
    private ArrayList<Turbine> turbines;
    
    @JsonProperty
    private ArrayList<Pump> pumps;
    
    @JsonProperty
    private ArrayList<Valve> valves;
    
    public PowerPlant() {
        // setup the arrayLists
        reactors   = new ArrayList<>(1);
        condensers = new ArrayList<>(1);
        turbines   = new ArrayList<>(1);
        pumps      = new ArrayList<>(2);
        valves     = new ArrayList<>(2);
        
        // setup the components
        Reactor reactor = new Reactor("Reactor", 10, 1);
        Condenser condenser = new Condenser("Condenser", 10, 0.7);
        Turbine turbine = new Turbine("Turbine");
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
        reactors.add(reactor);
        condensers.add(condenser);
        turbines.add(turbine);
        pumps.add(pump);
        valves.add(reactorToTurbine);
        valves.add(condenserToPump);
    }
    
    public void step() throws GameOverException {
        for (Reactor r : reactors) {
            r.step();
        }
        
        for (Condenser c : condensers) {
            c.step();
        }
    }
    
    //<editor-fold desc="getters">
    public Reactor getReactor() {
        return reactors.get(0);
    }
    
    public Condenser getCondenser() {
        return condensers.get(0);
    }
    
    public Turbine getTurbine() {
        return turbines.get(0);
    }
    
    public ArrayList<Pump> getPumps() {
        return new ArrayList<>(pumps);
    }
    
    public ArrayList<Valve> getValves() {
        return new ArrayList<>(valves);
    }
    
    protected ArrayList<FailableObject> getFailableComponents() {
        ArrayList<FailableObject> failables = new ArrayList<>();
        for (Condenser c : condensers) {
            failables.add(c);
            failables.add(c.getHeatSink());
        }
        failables.addAll(turbines);
        failables.addAll(pumps);
        
        return failables;
    }
    //</editor-fold>
    
}
