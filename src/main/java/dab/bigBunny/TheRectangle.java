/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.simulator.FailableComponent;

/**
 *
 * @author Aiste
 */
public class TheRectangle extends HittableComponent{
    
    public TheRectangle(FailableComponent component, int x, int y, int width, int height){
        super(component, x, y, width, height);
        adjustCoordinates();
    }
      
    
    //TODO: adjust the differences depending on how much bigger the picture frame is from 
    //the picture itself. And probably check which of the components that is or sth before adjusting
    @Override
    protected void adjustCoordinates(){
        
    }
      
}