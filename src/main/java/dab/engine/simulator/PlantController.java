package dab.engine.simulator;

import dab.engine.utilities.Percentage;
import dab.engine.seprphase2.GameOverException;


/**
 *
 * @author David
 */
public interface PlantController {

    /**
     *
     * @param extracted
     */
    public void moveControlRods(Percentage extracted);

    /**
     *
     */
    public void changeValveState(int valveNumber, boolean isOpen) throws KeyNotFoundException;

    public void changePumpState(int pumpNumber, boolean isPumping) throws CannotControlException, KeyNotFoundException;

    public void repairPump(int pumpNumber) throws KeyNotFoundException, CannotRepairException;

    public void repairCondenser() throws CannotRepairException;

    public void repairTurbine() throws CannotRepairException;

    public void failCondenser();

    public void failReactor();

    public void setReactorToTurbine(boolean open);

    public void step(int steps) throws GameOverException;
}
