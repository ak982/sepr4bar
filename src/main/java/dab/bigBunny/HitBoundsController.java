/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.simulator.Condenser;
import dab.engine.simulator.Pump;
import dab.engine.simulator.Reactor;
import dab.engine.simulator.Turbine;
import dab.engine.simulator.views.FailableComponentView;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Aiste
 */
public class HitBoundsController {
    private ArrayList<HittableComponent> hittableComponents;
    
    public HitBoundsController() {
        hittableComponents = new ArrayList<HittableComponent>();
    }
    
    public void addHitableComponent(TheRectangle rectangle){
        hittableComponents.add(rectangle);
    }
    
    public void addHitableComponent(Circle circle){
        hittableComponents.add(circle);
    }
    
    public void addHitableComponent(FailableComponentView component, int x, int y, int width, int height) {
        // System.out.println(component.toString());
        if (component instanceof Pump) {
            addHitableComponent(new Circle(component, x, y, width, height));
        } else if (component instanceof Turbine) {
            addHitableComponent(new Circle(component, 375, 68, 48, 48));
            addHitableComponent(new Circle(component, 375, 131, 48, 48));
            addHitableComponent(new TheRectangle(component, x, y+15, width, height-30));       
        } else if(component instanceof Reactor) {
              addHitableComponent(new Circle(component, x, y, 40, 40));        //magic numbers here
              addHitableComponent(new TheRectangle(component, x, y+44, width, height-44));  //magic numbers here        
        } else if (component instanceof Condenser){
            if(height < 120){
                addHitableComponent(new Circle(component, x, 415, width, width));
                addHitableComponent(new TheRectangle(component, x, y, width, height-33));               
            } else {
                addHitableComponent(new Circle(component, 482, 354, 86, 86));
                addHitableComponent(new Circle(component, 482, 412, 86, 86));
                addHitableComponent(new TheRectangle(component, x, 376, width, 86));
            }
        }
    }
    
   public ArrayList<HittableComponent> getHittableComponents(){
       return hittableComponents;
   }
}
