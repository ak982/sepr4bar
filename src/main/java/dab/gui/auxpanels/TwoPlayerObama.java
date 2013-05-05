/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.auxpanels;

import dab.bigBunny.BunnyController;
import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.newsim.TwoPlayerSimulator;
import dab.gui.mainpanels.GameInterface;

/**
 *
 * @author eduard
 */
public class TwoPlayerObama extends ObamaPanel {

    TwoPlayerSimulator simulator;
    BunnyController    bunnyController;

    public TwoPlayerObama(TwoPlayerSimulator sim, BunnyController bctrl) {
        super();
        this.simulator = sim;
        this.bunnyController = bctrl;
    }

    @Override
    public void update() {
        String temp = "<html>";

        if (simulator.getReactor().coreTemperature().inKelvin() > 800) {
            temp += "WARNING, " + simulator.getUsername1()+ ": REACTOR CORE TEMPERATURE TOO HIGH! QUENCH IT!" + "<br>";
        }
        
        if (bunnyController.getHealth() < 20) {
            temp += "Your health is low " + simulator.getUsername2()+ ", destroy the plant faster! " + "<br>";
        }

        /*if (simulator.condenserPressure().greaterThan(GameInterface.CONDENSER_WARNING_PRESSURE)) {
            temp += "WARNING, " + simulator.getUsername() + ": CONDENSER PRESSURE TOO HIGH" + "<br>";
        }

        for (String failedComponent : simulator.listFailedComponents()) {
            if (failedComponent.equals("Pump 1")) {
                temp += "WARNING, " + simulator.getUsername() + ": The Water pump HAS FAILED<br>";

            } else if (failedComponent.equals("Pump 2")) {
                temp += "WARNING, " + simulator.getUsername() + ": The coolant pump HAS FAILED<br>";

            } else {
                temp += "WARNING, " + simulator.getUsername() + ": " + failedComponent + " HAS FAILED<br>";
            }
        }*/

        /*if (!simulator.getSoftFailReport().getFailBool()) {
            temp += "WARNING, " + simulator.getUsername() + ": A software failure has occured!";
        }
        temp += "</html>";*/

        lblWords.setText(temp);

    }
}
