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
public abstract class HittableComponent {
    
    protected FailableComponentView component;
    protected int x,y, width, height;
    
    
    public HittableComponent(FailableComponentView component, int x, int y, int width, int height){
        this.component = component;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public FailableComponentView getComponent(){
        return component;
    }
    
    protected Rectangle getDimensions(){
        return new Rectangle(x,y,width,height);
    }
    
    protected abstract Rectangle getHittableBounds(int radius);
}
