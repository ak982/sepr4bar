package dab.bigBunny;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class BunnyController {

    private int rotation;
    private double x, y, speed, boundX, boundY;
    private boolean forward, rotateLeft, rotateRight, braking;
    private final int rotationAmount = 5;
    private final double defAcceleration = 0.4;
    private final double breakingAcceleration = -1;
    private final double noramlStopping = -0.5;
    private Environment environment;
    private int radius;
    private int health;
    private Rectangle bounds;

    BunnyController(Environment environment, int radius) {
        x = 100;
        y = 100;
        rotation = 0;
        forward = false;
        rotateLeft = false;
        rotateRight = false;
        speed = 1;
        braking = false;
        this.environment = environment;
        this.radius = radius;
        health = 100;
    }

    public void step() {
        moveForward(forward);
        doRotateRight(rotateRight);
        doRotateLeft(rotateLeft);
    }

    public void startForward() { forward = true; }

    public void stopForward() { forward = false; }

    public void startRotateLeft() { rotateLeft = true; }

    public void stopRotateLeft() { rotateLeft = false; }

    public void startRotateRight() { rotateRight = true; }

    public void stopRotateRight() { rotateRight = false; }

    public void doRotateLeft(boolean left) {
        if (left) { 
            rotation = (rotation - rotationAmount) % 360;
        }
    }

    public void doRotateRight(boolean right) {
        if (right) {
            rotation = (rotation + rotationAmount) % 360;
        }
    }

    //Do not Go outside the game!!
    public void moveForward(boolean forw) {
        double acceleration;
        if (forward && !braking) {
            acceleration = defAcceleration;
        } else if (braking) {
            acceleration = breakingAcceleration;
        } else {
            acceleration = noramlStopping;
        }
        
        Slime intersected = environment.intersectWithSlime(new Point(getX(), getY()), radius);
        if (intersected != null) {
           // System.out.println("Intersected with " + intersected.toString());
            // BAD CODE!!!
            if (speed > 2) {
                acceleration = -1 * (intersected.getFreshness() + 0.5);
            } else {
                acceleration /= 2;
            }
        }        
       // System.out.println(String.format("speed: %f, acc: %f", speed, acceleration));     
        speed += acceleration;
        if (speed < 0) {
            speed = 0;
        }
        x += speed * Math.cos(Math.toRadians(rotation));
        y += speed * Math.sin(Math.toRadians(rotation));     
        
        //to check if intersectig with something - be that bounds or components
        checkInBounds(new Point2D.Double(x,y) ); 
        
        
    }

    public void startBrake() { braking = true; }

    public void stopBrake() { braking = false; }

    public void hasBeenShot(){
        health --;
        System.out.println(health);
        if(health <= 0){
            
           //Nice animation of dying bunny, and gameover
       
        }
    }
    
    private void checkInBounds(Point2D.Double point){
        double a,b;
        a= point.getX();
        b= point.getY();
        
        
        
        
        
        if(!bounds.contains(a,b)){
           //set speed. maybe bounce
            
            
            if(bounds.getMinX() > a){
                x = bounds.getMinX();
            }
            else if (bounds.getMaxX() < a){
                x = bounds.getMaxX();
            }
            
            if(bounds.getMinY() > b){
                y = bounds.getMinY();
            }
            else if (bounds.getMaxY() < b){
                y = bounds.getMaxY();
            }
        }
        
        
    }
    
    public int getX() { return (int) x; }

    public int getY() { return (int) y; }
    
    public Point getCoordinates(){ return (new Point(getX(),getY())); }

    public int getRotation() { return rotation; }
    
    public int getHealth(){ return health; }
    
    public int getRadius(){ return radius; }
    
    public void setBounds(Rectangle rectangle) {
        bounds = rectangle;
        System.out.println("x of bounds " + bounds.getX());
        System.out.println("x2 of bounds " + (bounds.getX()+bounds.getWidth()));
        System.out.println("is the same? " + bounds.getMaxX());
    }
    
    
    
}
