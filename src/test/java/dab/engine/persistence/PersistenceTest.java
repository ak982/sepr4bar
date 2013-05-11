/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.persistence;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import dab.engine.newsim.PowerPlant;
import dab.engine.newsim.SinglePlayerSimulator;
import dab.engine.newsim.components.Reactor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author David
 */
public class PersistenceTest {

	Persistence persistence;
	SaveGame before;
	SaveGame after;
    
    public PersistenceTest() throws IOException {
        persistence = new Persistence();
        before = new SinglePlayerSimulator("name").saveGame();
        after = null;
    }

    @Before
    public void setUp() throws IOException {
        persistence = new Persistence();
        before = new SinglePlayerSimulator("name").saveGame();
        after = null;
    }

    @Test
    public void shouldSerializeReactor() throws JsonProcessingException {
        String result = persistence.serialize(new Reactor("reactor", 1, 1));
        assertNotSame("", result);
    }

    @Test
    public void shouldSerializePowerPlant() throws JsonProcessingException {
        String result = persistence.serialize(new PowerPlant());
        assertNotSame("", result);
    }

    private void backAndForthPersistence() throws JsonMappingException, JsonProcessingException, IOException {

    }

    @Test
    public void shouldPersistConsistently() throws  IOException {
        String representation = persistence.serialize(before);
        after = persistence.deserializeSaveGame(representation);
        assertNotNull(after);
        assertNotSame(before, after);
        
        assertEquals(persistence.serialize(before), persistence.serialize(after));
        //assertEquals(after.toString(), before.toString());
        
        /*
        assertEquals(before.controlRodPosition(), after.controlRodPosition());
        assertEquals(before.energyGenerated(), after.energyGenerated());
        assertEquals(before.reactorPressure(), after.reactorPressure());
        assertEquals(before.reactorTemperature(), after.reactorTemperature());
        assertEquals(before.reactorWaterLevel(), after.reactorWaterLevel());*/
    }
}
