/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class Constants {
    
    public static final int AVOGADRO_NUMBER = 600;
    public static final double GAS_CONSTANT_R = 8.314;
    public static final double BOLTZMAN_CONSTANT = GAS_CONSTANT_R / AVOGADRO_NUMBER;
    public static final double ROOM_TEMP = 300;
    public static final double NORMAL_FREEZING_POINT = 273.15;
    public static final double GRAVITATIONAL_ACCELERATION = 9.8;
    public static final double WATER_NORMAL_DENSITY = 1000; // kg / m3
    public static final double NORMAL_BOILING_POINT = NORMAL_FREEZING_POINT + 100;
    public static final double LATENT_HEAT = 2260; // kjoules
    public static final double ATMOSPHERIC_PRESSURE = 100000;
    public static final double MOLAR_MASS = 0.018; // kg / mol
    public static final double NORMAL_STEAM_PARTICLES_PER_VOLUME = Constants.ATMOSPHERIC_PRESSURE / (Constants.BOLTZMAN_CONSTANT * Constants.ROOM_TEMP);
    public static final double SPECIFIC_HEAT_WATER = 4.2; // kj / kgK;
    public static final double SPECIFIC_HEAT_STEAM = 2;
    public static final int    TICKS_PER_SECOND = 10;
    
}
