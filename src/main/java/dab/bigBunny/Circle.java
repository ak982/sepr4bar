/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.newsim.interfaces.FailableComponentView;
import java.awt.Rectangle;

/**
 *
 * @author Aiste
 */
public class Circle extends HittableComponent{
    
    
    public Circle(FailableComponentView component, int x, int y, int width, int height){
      super(component, x, y, width, height);
    }
    
    //TODO: adjust the differences depending on how much bigger the picture frame is from 
    //the picture itself. And probably check which of the components that is or sth before adjusting
 
    @Override
    public Rectangle getHittableBounds(int radius){
       int newX =  x - radius;
       int newY = y - radius;
       int newWidth = width +radius + radius;
       int newHeight = height + radius + radius;
       return new Rectangle(newX, newY, newWidth, newHeight);       
    }
 
}
