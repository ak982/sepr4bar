/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import dab.engine.simulator.FailMode;
import dab.engine.simulator.SoftFailReport;
import dab.engine.simulator.UserCommands;

/**
 *
 * @author eduard
 */
public class SinglePlayerFailureModel extends FailureModel {

    private final static int MAX_DAMAGE_SINGLE = 5;
    private final static int DAMAGE_INCREASE_SINGLE = 1;
    //A component will have a 1 in 50 chance of failing (per second)
    private final static double DEFAULT_FAIL_CHANCE = 1 / 50;

    public SinglePlayerFailureModel(PowerPlant plant) {
        super(plant);
        setDamagesToComponents(MAX_DAMAGE_SINGLE, DAMAGE_INCREASE_SINGLE);
    }

    @Override
    public boolean softFailCheck(UserCommands targetCommand, double targetParameter) {
        double failRoll = dice.rollDouble();
        if (failRoll <= 0.05) { //chance of unresponsive failure
            //Unresponive commands do nothing
            lastFailReport = new SoftFailReport(FailMode.UNRESPONSIVE, targetCommand, targetParameter);
            return false;
        } else if (failRoll <= 0.2) { //chance of any failure (incorrect fail = this - unresponsive fail chance)

            //Incorrect commands behave as another command
            UserCommands actualCommand = UserCommands.randomCommand();
            double actualParameter = generateFailedParameter(actualCommand);
            //doFailedCommand(actualCommand, actualParameter);
            lastFailReport = new SoftFailReport(FailMode.INCORRECT, targetCommand, targetParameter, actualCommand, actualParameter);
            return false;
        } else {
            //Working commands behave as intended
            lastFailReport = new SoftFailReport(FailMode.WORKING, targetCommand, targetParameter);
            return true;
        }
    }

    @Override
    protected double getHardwareFailChance() {
       return DEFAULT_FAIL_CHANCE * getDifficultyModifier();
    }
}
