/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.auxpanels;

import dab.bigBunny.BunnyController;
import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.newsim.TwoPlayerSimulator;
import dab.engine.simulator.FailMode;
import dab.gui.mainpanels.GameInterface;

/**
 *
 * @author eduard
 */
public class TwoPlayerObama extends ObamaPanel {

    BunnyController    bunnyController;

    public TwoPlayerObama(TwoPlayerSimulator sim, BunnyController bctrl) {
        super(sim);
        this.bunnyController = bctrl;
    }

    private TwoPlayerSimulator getSimulator() {
        return (TwoPlayerSimulator)simulator;
    }
    
    @Override
    public void update() {
        String temp = getWarningMessages(getSimulator().getUsername1());

        if (simulator.getSoftwareStatus() != FailMode.WORKING) {
            temp += "" + getSimulator().getUsername2() + " has used it's psypower to disable the software!" + "<br>";
        }

        if (bunnyController.getHealth() < 20) {
            temp += "Your health is low " + getSimulator().getUsername2() + ", destroy the plant faster! " + "<br>";
        }

        if (bunnyController.hasHeadache()) {
            temp += "Your head hurts from bumping into the component, " + getSimulator().getUsername2();
        }

        setText(temp);
    }
}
