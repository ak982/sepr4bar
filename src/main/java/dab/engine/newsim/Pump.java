/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class Pump extends Component {
    private double power;
    
    public Pump(double power) {
        this.power = power;
    }
    
    public double getPower() {
        return power;
    }
    
    public void setPower(double power) {
        this.power = power;
    }
    
    @Override
    protected HydraulicState getHydroState() {
        ContainerHydroState chs = (ContainerHydroState)getOutputComponent().getHydroState();
        return new ContainerHydroState(chs.getPressure() - power, chs.getCompressibleVolume(), chs.getArea());
    }

    @Override
    protected void receiveMatter(Matter m) {
        outputComponent.receiveMatter(m);
    }
    
    
}
