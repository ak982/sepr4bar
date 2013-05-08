/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import dab.engine.persistence.SaveGame;
import java.io.IOException;

/**
 *
 * @author eduard
 */
public class SinglePlayerSimulator extends AbstractSimulator {
    
    private String userName;
    private SinglePlayerFailureModel failureModel;
    
    public SinglePlayerSimulator() {
        super();
        this.userName = "";
        this.failureModel = new SinglePlayerFailureModel(powerPlant);
    } 
    
    public SinglePlayerSimulator(String playerName) {
        this();
        this.userName = playerName;
    }
    
    public void setUsername(String userName) {
        this.userName = userName;
    }
    
    /**
     *
     * Save game
     * @throws JsonProcessignException
     */
    public void saveGame() throws JsonProcessingException {
        SaveGame saveGame = new SaveGame(failureModel, userName);
        try {
            saveGame.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } 
    }
    /**
     *
     * Load a game
     * @param game Number
     */
    public void loadGame(int gameNumber) {
        try {
            SaveGame saveGame = SaveGame.load(listGames()[gameNumber]);
            this.failureModel = saveGame.getFailureModel();
            failureModel.afterLoad();
            this.powerPlant = failureModel.getPowerPlant();
            this.userName = saveGame.getUserName();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected FailureModel getFailureModel() {
        return failureModel;
    }
    
    public String getUsername() {
        return userName;
    }

}
