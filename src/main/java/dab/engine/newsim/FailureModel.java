package dab.engine.newsim;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dab.engine.newsim.interfaces.FailableObject;
import dab.engine.newsim.utils.Constants;
import dab.engine.newsim.utils.Difficulty;
import dab.engine.newsim.utils.OptionsHolder;
import dab.engine.newsim.utils.RandomBuffer;
import dab.engine.newsim.utils.RandomGenerator;
import dab.engine.simulator.FailMode;
import dab.engine.simulator.GameOverException;
import dab.engine.simulator.SoftFailReport;
import dab.engine.simulator.UserCommands;
import static dab.engine.simulator.UserCommands.CLOSE;
import static dab.engine.simulator.UserCommands.MOVE;
import static dab.engine.simulator.UserCommands.OPEN;
import static dab.engine.simulator.UserCommands.TURNOFF;
import static dab.engine.simulator.UserCommands.TURNON;
import dab.engine.utilities.Pressure;
import static dab.engine.utilities.Units.percent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages software and hardware failures.
 *
 * The FailureModel is responsible for inducing software and hardware failures
 * in the plant.
 *
 * Software failures are currently unimplemented; however, as all calls to the
 * PhysicalModel are delegated through the FailureModel, it is easy to alter
 * this delegation in arbitrary ways to simulate control and/or reporting
 * failures.
 *
 * Hardware failures are determined by inspecting the PhysicalModel to determine
 * the status, wear, and operating conditions of the various components, and
 * then instructing the PhysicalModel to mark certain components as having
 * failed.
 *
 * Failures are constrained to one per timestep, which is enabled by the
 * delegation of the step() method through the FailureModel.
 *
 * @author Marius Dumitrescu
 */

public abstract class FailureModel {
    
    protected SoftFailReport lastFailReport = new SoftFailReport();
    
    protected RandomGenerator dice = new RandomGenerator();
    
    @JsonProperty
    protected PowerPlant powerPlant;
    
    
    protected FailureModel() {
        
    }

    public FailureModel(PowerPlant plant) {
        this.powerPlant = plant;
    }

     /**
     * Step the PhysicalModel and determine any failures.
     *
     * Also implements reactor safety rules.
     *
     */
    public void step() throws GameOverException {
        powerPlant.step();
        
        // implement turbine safety rules
        if (powerPlant.getTurbine().hasFailed()) {
            powerPlant.getReactor().setEmergencyOff(true);
        } else {
            powerPlant.getReactor().setEmergencyOff(false);
        }
    }
    
    PowerPlant getPowerPlant() {
        return powerPlant;
    }
    
    protected final void setDamagesToComponents(int maxDamage, int damageIncrease) {
        for(FailableObject component : powerPlant.getFailableComponents()){
            component.getFailureController().setDamageValues(maxDamage, damageIncrease);
        }     
    }
    
    protected double getDifficultyModifier() {
        return (OptionsHolder.getInstance().getDifficulty().asDouble());
    }
    
    public void setDifficulty(int difficulty) {
        if (difficulty == 1) {
            OptionsHolder.getInstance().setDifficulty(Difficulty.EASY);
        } else if (difficulty == 2) {
            OptionsHolder.getInstance().setDifficulty(Difficulty.MEDIUIM);
        } else {
            OptionsHolder.getInstance().setDifficulty(Difficulty.HARD);
        }
    }

    protected abstract FailMode getSoftwareFailureMode();
    public abstract SoftFailReport generateSoftwareReport(UserCommands targetCommand, double targetParameter);
    
    // names of failed components
    public ArrayList<String> listFailedComponents() {
        ArrayList<String> out = new ArrayList<>();
        
        for (FailableObject fo : powerPlant.getFailableComponents()) {
            if (fo.getFailureController().hasFailed()) {
                out.add(fo.getName());
            }
        }

        return out;

    }

    /**
     * @param UserCommand
     * @return double (a random failure for each command)
     */
    protected double generateFailedParameter(UserCommands command) {
        Random rand = new Random();
        switch (command) {
            case TURNON:
            case TURNOFF:
                //System.out.println(rand.nextInt(2) + 1);
                return rand.nextInt(2) + 1;
            case OPEN:
            case CLOSE:
                return rand.nextInt(2) + 1;
            case MOVE:
                return rand.nextInt(51);
            default:
                return 0;
        }
    }

 
}
