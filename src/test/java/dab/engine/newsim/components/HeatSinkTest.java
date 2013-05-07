package dab.engine.newsim.components;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import dab.engine.newsim.utils.Steam;
import dab.engine.newsim.utils.Water;

/**
 *
 * @author bjw523
 */
public class HeatSinkTest {
    @Test
    public void testStepWaterInit() {
        HeatSink heatSink = new HeatSink("heatSink");
        heatSink.step();
        assertEquals(300, heatSink.getWaterTemp());
    }
    
    @Test
    public void testStepSteamInit() {
        HeatSink heatSink = new HeatSink("heatSink");
        heatSink.step();
        assertEquals(300, heatSink.getSteamTemp());
    }
    
    @Test
    public void testStepFailed() {
        HeatSink heatSink = new HeatSink("heatSink");
        double waterTemp = heatSink.getWaterTemp();
        double steamTemp = heatSink.getSteamTemp();
        heatSink.coolWater(new Water(400, 30000));
        heatSink.coolSteam(new Steam(400, 30000));
        heatSink.fail(1);
        heatSink.step();
        assertTrue(heatSink.getWaterTemp() == waterTemp && heatSink.getSteamTemp() == steamTemp);
    }
    
    @Test
    public void testStepCoolerWater() {
        HeatSink heatSink = new HeatSink("heatSink");
        double addedWaterTemp = 100;
        heatSink.coolWater(new Water(addedWaterTemp, 30000));
        double waterTemp = heatSink.getWaterTemp();
        heatSink.step();
        assertTrue(heatSink.getWaterTemp() > waterTemp);
    }
    
    @Test
    public void testStepWarmerWater() {
        HeatSink heatSink = new HeatSink("heatSink");
        double addedWaterTemp = 400;
        heatSink.coolWater(new Water(addedWaterTemp, 30000));
        double waterTemp = heatSink.getWaterTemp();
        heatSink.step();
        assertTrue(heatSink.getWaterTemp() > waterTemp);
    }
    
    @Test
    public void testStepCoolerSteam() {
        HeatSink heatSink = new HeatSink("heatSink");
        double addedSteamTemp = 100;
        heatSink.coolSteam(new Steam(addedSteamTemp, 30000));
        double steamTemp = heatSink.getSteamTemp();
        heatSink.step();
        assertTrue(heatSink.getSteamTemp() > steamTemp);
    }
    
    @Test
    public void testStepWarmerSteam() {
        HeatSink heatSink = new HeatSink("heatSink");
        double addedSteamTemp = 400;
        heatSink.coolSteam(new Steam(addedSteamTemp, 30000));
        double steamTemp = heatSink.getSteamTemp();
        heatSink.step();
        assertTrue(heatSink.getSteamTemp() > steamTemp);
    }
    
    @Test
    public void testCoolerWater() {
        HeatSink heatSink = new HeatSink("heatSink");
        Water water = new Water(100, 30000);
        double waterTemp = heatSink.getWaterTemp();
        heatSink.coolWater(water);
        assertTrue(heatSink.getWaterTemp() < waterTemp);
    }
    
    @Test
    public void testWarmerWater() {
        HeatSink heatSink = new HeatSink("heatSink");
        Water water = new Water(400, 30000);
        double waterTemp = heatSink.getWaterTemp();
        heatSink.coolWater(water);
        assertTrue(heatSink.getWaterTemp() > waterTemp);
    }
    
    @Test
    public void testCoolerSteam() {
        HeatSink heatSink = new HeatSink("heatSink");
        Steam steam = new Steam(100, 30000);
        double steamTemp = heatSink.getSteamTemp();
        heatSink.coolSteam(steam);
        assertTrue(heatSink.getSteamTemp() < steamTemp);
    }
    
    @Test
    public void testWarmerSteam() {
        HeatSink heatSink = new HeatSink("heatSink");
        Steam steam = new Steam(100, 30000);
        double steamTemp = heatSink.getSteamTemp();
        heatSink.coolSteam(steam);
        assertTrue(heatSink.getSteamTemp() < steamTemp);
    }
}
