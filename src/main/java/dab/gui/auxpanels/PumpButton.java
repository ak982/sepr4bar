package dab.gui.auxpanels;

import dab.engine.newsim.interfaces.PumpView;

/**
 *
 * @author eduard
 */
public class PumpButton extends ControlButton {

    private PumpView pump;

    public PumpButton(PumpView pump, String name) {
        super(name);
        this.pump = pump;
    }

    @Override
    protected void onClick() {
        if (!pump.hasFailed()) { // component is running
            pump.setStatus(!pump.getStatus()); // toggle pump status
            setStatus(pump.getStatus());
        }
    }

    @Override
    public void update() {
        super.update();
        if (pump.hasFailed()) {
            setFailed();
        } else {
            setStatus(pump.getStatus());
        }
    }
}
