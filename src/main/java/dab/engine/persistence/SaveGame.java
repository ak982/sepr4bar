package dab.engine.persistence;

import dab.engine.simulator.FailureModel;
import dab.engine.simulator.PhysicalModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import dab.engine.newsim.PowerPlant;
import dab.engine.newsim.SinglePlayerFailureModel;

/**
 * Saveable/Loadable game state
 * 
 * @author David
 */
public class SaveGame {

    /**
     * Load a specified saved game
     *  @param String
     *  @return SaveGame
     */
    public static SaveGame load(String filename) throws JsonParseException, IOException {
        Persistence p = new Persistence();
        return p.deserializeSaveGame(FileSystem.readString(filename));
    }

    @JsonProperty
    private PowerPlant powerPlant;
    @JsonProperty
    private SinglePlayerFailureModel failureModel;
    @JsonProperty
    private String userName;

    /**
     *  Save the state of the game
     *  @param PhysicalModel
     *  @param FailureModel
     *  @param String
     */
    public SaveGame(PowerPlant physicalModel, SinglePlayerFailureModel failureModel, String userName) {
        this.powerPlant = physicalModel;
        this.failureModel = failureModel;
        this.userName = userName;
    }

    /**
     * Serialize the saved game
     * @throws JsonProcessingException, FileNotFoundException, IOException
     */
    public void save() throws JsonProcessingException, FileNotFoundException, IOException {
        Persistence p = new Persistence();
        String data = p.serialize(this);
        FileSystem.createSavePath();
        FileSystem.writeString(fileName(), data);
    }

    public String getUserName() {
        return userName;
    }

    public PowerPlant getPowerPlant() {
        return powerPlant;
    }
    
    public SinglePlayerFailureModel getFailureModel() {
        return failureModel;
    }

    /**
     * generateFileName generates a new unique file name using getTimeInMillis
     *
     * @return The newly generated random file name
     */
    private String fileName() {
        Calendar cal = Calendar.getInstance();
        return "DaBAR" + userName + "." + cal.getTimeInMillis() + ".sav";
    }
}
