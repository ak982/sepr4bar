/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.utils;

/**
 *
 * @author eduard
 */
public class Constants {

    // others
    public static final double ROOM_TEMP = 300;
    public static final double GRAVITATIONAL_ACCELERATION = 9.8;
    public static final int    TICKS_PER_SECOND = 5;
    public static final double ATMOSPHERIC_PRESSURE = 100000;
    
    // gas constants
    public static final int    AVOGADRO_NUMBER = 600; // wait wut? :) (don't worry it's intentional)
    public static final double GAS_CONSTANT_R = 8.314;
    public static final double BOLTZMAN_CONSTANT = GAS_CONSTANT_R / AVOGADRO_NUMBER;
    
    // water properties
    public static final double NORMAL_FREEZING_POINT = 273.15;
    public static final double MOLAR_MASS_WATER = 0.018;    // kg / mol
    public static final double NORMAL_BOILING_POINT = NORMAL_FREEZING_POINT + 100;
    public static final double NORMAL_DENSITY_WATER = 1000; // kg / m3
    public static final double LATENT_HEAT_WATER = 2260;    // kjoules FIXME: normally it's 2260
    public static final double SPECIFIC_HEAT_WATER = 4.2;   // kj / kgK;
    public static final int    WATER_PARTICLES_PER_KILOGRAM = (int)(MOLAR_MASS_WATER * AVOGADRO_NUMBER);
    
    // steam properties
    public static final int    NORMAL_PARTICLES_PER_VOLUME_STEAM = (int)(ATMOSPHERIC_PRESSURE / (BOLTZMAN_CONSTANT * ROOM_TEMP));
    public static final double SPECIFIC_HEAT_STEAM = 2;
    
}
