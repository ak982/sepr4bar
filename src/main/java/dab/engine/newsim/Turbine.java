/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class Turbine extends Component {
    
    double totalEnergyGenerated;
    double energyGeneratedLast;
    
    public Turbine() {
        totalEnergyGenerated = 0;
        energyGeneratedLast  = 0;
    }
    
    public double getLastEnergy() {
        return energyGeneratedLast;
    }
    
    @Override
    protected HydraulicState getHydroState() {
        return outputComponent.getHydroState();
    }

    /*
     * FIXME: magicnumber: * 100
     */
    @Override
    protected void receiveMatter(Matter m) {
        Steam steamReceived = (Steam)m;
        energyGeneratedLast = steamReceived.getMass() * 100;
        totalEnergyGenerated += energyGeneratedLast;
        // even maybe remove some of the actual energy?
        outputComponent.receiveMatter(m);
    }

}
