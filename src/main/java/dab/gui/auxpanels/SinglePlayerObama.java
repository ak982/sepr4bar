/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.auxpanels;

import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.simulator.FailMode;
import dab.gui.mainpanels.GameInterface;

/**
 *
 * @author eduard
 */
public class SinglePlayerObama extends ObamaPanel {

    SinglePlayerSimulator simulator;

    public SinglePlayerObama(SinglePlayerSimulator sim) {
        super(sim);
        this.simulator = sim;
    }

    @Override
    public void update() {
        String text = getWarningMessages(simulator.getUsername());
        if (simulator.getSoftwareStatus() != FailMode.WORKING) {
            text += makeWarning(simulator.getUsername(), "Software malfunctionning!");
        }
        setText(text);
        repaint();
    }
}
