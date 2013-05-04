/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.simulator.views.FailableComponentView;
import java.awt.Rectangle;

/**
 *
 * @author Aiste
 */
public class RecCircle extends HittableComponent{
    private Circle circle1, circle2;
    private TheRectangle rectangle;
    
    public RecCircle(FailableComponentView component, int x, int y, int width, int height, Circle circle1,
            Circle circle2, TheRectangle rectangle){
        super(component, x, y, width, height);
        this.circle1 = circle1;
        this.circle2 = circle2;
        this.rectangle = rectangle;
    }

    public Circle getCircle1() {
        return circle1;
    }
    
    public Circle getCircle2(){
        return circle2;
    }
    
    public TheRectangle getRectangle(){
        return rectangle;
    }
    
    //To change This!!
    @Override
    protected Rectangle getHittableBounds(int radius) {
        return (new Rectangle(x, y, width, height));
    }
    
}
