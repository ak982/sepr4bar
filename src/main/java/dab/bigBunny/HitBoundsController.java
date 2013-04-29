/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.simulator.views.FailableComponentView;
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
        if (component.toString().contains("Pump")) {
            addHitableComponent(new Circle(component, x, y, width, height));
        } else if (component.toString().contains("Turbine")) {
            addHitableComponent(new TheRectangle(component, x, y, width, height));
        } else if(component.toString().contains("Reactor")) {
            addHitableComponent(new TheRectangle(component, x, y, width, height));  //magic numbers here
            addHitableComponent(new Circle(component, x, y, width, height));        //magic numbers here
        } else if (component.toString().contains("Condenser")){
            
        }
    }
    
   public ArrayList<HittableComponent> getHittableComponents(){
       return hittableComponents;
   }
}
