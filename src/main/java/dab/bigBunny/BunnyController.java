package dab.bigBunny;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class BunnyController {
    // number of degrees to rotate per step
    private final int ROTATION_AMOUNT = 5;
    
    // default ammount to accelerate
    private final double DEF_ACCELERATION = 0.4;;
    
    private final double BRAKING_ACCELERATION = -1;
    
    // ammount we slow down when nothing is happenning (it's basically usual drag)
    private final double NORMAL_STOPPING = -0.5;
    
    
    private int rotation;
    private double x, y, speed, boundX, boundY;
    private boolean movingForward, rotatingLeft, rotatingRight, braking;
    private boolean softwareFailure;

    private Environment environment;
    private int radius;
    private int health;
    private Rectangle bounds, hitBounds;
    private final double bounce = -0.5;

    /* 
     * BunnyController: Environment, position, size
    */    
    BunnyController(Environment e, Point p, int size) {
        this.environment = e;
        this.radius = size;
        
        this.x = p.x;
        this.y = p.y;
        
        movingForward = false;
        braking = false;
        rotatingLeft = false;
        rotatingRight = false;
        
        rotation = 0;
        speed = 0;
        
        health = 100;
    }
    
    BunnyController(Environment environment, int radius) {
        this(environment, new Point(100, 100), radius);
    }

    public void step() {
        updateMovement();
        //moveForward();
        //doRotateLeft(rotatingLeft);
        //doRotateRight(rotatingRight);
        softwareFailure = environment.getSoftwareFailure();
    }
    
    /*
     * calculate acceleration depending on the state
     * (running, braking, intersecting slime)
     */
    private double computeAcceleration() {
        double acceleration;
        if (movingForward && !braking) {
            acceleration = DEF_ACCELERATION;
        } else if (braking) {
            acceleration = BRAKING_ACCELERATION;
        } else {
            acceleration = NORMAL_STOPPING;
        }
        
        Slime intersected = environment.intersectWithSlime(new Point(getX(), getY()), radius);
        if (intersected != null) {
           // System.out.println("Intersected with " + intersected.toString());
            // BAD CODE!!!
            if (speed > 2)
                speed = speed * (1 - intersected.getFreshness() * 0.2);
                /*{
                acceleration = -1 * (intersected.getFreshness() + 0.5);
            } else {
                acceleration /= 2;
            }*/
        }
        
        return acceleration;
    }
    
    protected void updateMovement() {
        double acceleration = computeAcceleration();
        double newX, newY;
        
        // no not allow backwards movement
        speed += acceleration;
        if (speed < 0) {
            speed = 0;
        }

        // calculate predicted coordonates
        newX = x + speed * Math.cos(Math.toRadians(rotation));
        newY = y + speed * Math.sin(Math.toRadians(rotation));  
        
        // check for solid collisions (including bounding box)
        
        // update orientation (rotation)
        // TODO: should we update the rotation first and then the coordonates?
        if (rotatingLeft)
            rotation = (rotation - ROTATION_AMOUNT) % 360;
        if (rotatingRight) 
            rotation = (rotation + ROTATION_AMOUNT) % 360; 
       
        // calculate new coordonates
        x = newX;
        y = newY;
    }

    public void startForward()     { movingForward = true;  }
    public void stopForward()      { movingForward = false; }
    
    public void startBrake()       { braking       = true;  }
    public void stopBrake()        { braking       = false; }

    public void startRotateLeft()  { rotatingLeft  = true;  }
    public void stopRotateLeft()   { rotatingLeft  = false; }

    public void startRotateRight() { rotatingRight = true;  }
    public void stopRotateRight()  { rotatingRight = false; }

    /*
     * see {@link moveForward()} for reasons
     */
    @Deprecated
    public void doRotateLeft(boolean left) {
        if (left) { 
            if(!softwareFailure){ rotateLeft(); }
            else { rotateRight(); }
        }
    }

    /*
     * see {@link moveForward()} for reasons
     */
    @Deprecated
    public void doRotateRight(boolean right) {
        if (right) {
            if(!softwareFailure){ rotateRight(); }
            else { rotateLeft(); }
        }       
    }
    
    private void rotateRight(){
        rotation = (rotation + ROTATION_AMOUNT) % 360;
    }
    
    private void rotateLeft(){
        rotation = (rotation - ROTATION_AMOUNT)% 360;
                if (rotation < 0) {
                    rotation += 360;
                }
    }

    /*
     * move bunny forward according to forward/braking state + slime slow down
     * reason for deprecating: 
     *   doesn't scale very well (need to call other functions for bound checking)
     *   it doesn't allow for easy changes in rotation 
     *     (think what happens if we colide with a component at a very small angle)
     * 
     * @deprecated use {@link updateMovement()} instead
     */
    //Do not Go outside the game!!
    @Deprecated
    public void moveForward() {
        double acceleration, x0, y0;
        if ((movingForward && !braking)|| (speed <0)) {
            acceleration = DEF_ACCELERATION;
        } else if (braking && (speed >0)) {
            acceleration = BRAKING_ACCELERATION;
        } else if(speed >0){
            acceleration = NORMAL_STOPPING;
        } else {acceleration = 0;}
        
        System.out.println("speed "+ speed);
        
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
        if((-1<speed)&& speed<0){speed =0;}
        x0 = x;
        y0 = y;
        
        x += speed * Math.cos(Math.toRadians(rotation));
        y += speed * Math.sin(Math.toRadians(rotation));     
        
        //to check if intersectig with something - be that bounds or components
        checkInBounds(new Point2D.Double(x,y) ); 
        chekIntersects(x0, y0);
    }



    public void hasBeenShot(){
        health --;
        System.out.println(health);
        if (health <= 0){
            
           //Nice animation of dying bunny, and gameover
       
        }
    }
    
    private void checkInBounds(Point2D.Double point){
        double a,b;
        a= point.getX();
        b= point.getY();
  
        if(!bounds.contains(a,b)){
           speed = bounce * speed;           
            
            if(bounds.getMinX() > a){
                x = bounds.getMinX();
            } else if (bounds.getMaxX() < a){
                x = bounds.getMaxX();
            }
            
            if(bounds.getMinY() > b){
                y = bounds.getMinY();
            } else if (bounds.getMaxY() < b){
                y = bounds.getMaxY();
            }
        }     
    }
    
    public void chekIntersects(double x0, double y0){
        double x2, y2, halfHeight, halfWidth;
        
        
        //latter make these for multiple thingies
        x2 = hitBounds.getCenterX();                    
        y2 = hitBounds.getCenterY();
        halfHeight = hitBounds.getHeight() / 2;
        halfWidth = hitBounds.getWidth() / 2;
        
        /* 
         * first proposition checks if new coordinates of the bunny are inside the hitable object,
         * second one checks if the bunny jumps over the hitable object, i.e. the line between its
         * previous coordinates and the new ones intersects the object. 
         */
        if((hit(y2, x2, halfHeight, halfWidth))||(hitBounds.intersectsLine(x0, y0, x, y))){ 
            //Break the component

            speed = bounce * speed; 
            //give headake
            
            if (rotation == 0) {
                handleHitFromLeft(x2, halfWidth);
            } else if(rotation == 90){
                handleHitFromAbove(y2, halfHeight);
            } else if (rotation == 180){
                handleHitFromRight(x2, halfWidth);
            } else if (rotation == 270){
                handleHitFromBelow(y2, halfHeight);
            } else if(rotation<90) {                    
                handleTopLeft(x0,x2,y0,y2,halfHeight,halfWidth);                                        
            } else if(rotation < 180){
                handleTopRight(x0,x2,y0,y2,halfHeight,halfWidth);
            } else if(rotation < 270){
                handleBottomRight(x0,x2,y0,y2,halfHeight,halfWidth);
            } else {
                handleBottomLeft(x0,x2,y0,y2,halfHeight,halfWidth);
            }
        }        
    }
    
    /*set y to be at the upper border of the hittable object */
    private void handleHitFromAbove(double y2, double halfHeight){
        y = y2 - halfHeight - radius;
    }
    
    /*set x to be at the left border of the hittable object */
    private void handleHitFromLeft(double x2, double halfWidth){
        x= x2 - halfWidth - radius;
    }
    
    /*set x to be at the right border of the hittable object */
    private void handleHitFromRight(double x2, double halfWidth){
        x = x2 + halfWidth + radius;
    }
    
    /*set x to be at the bottom border of the hittable object */
    private void handleHitFromBelow(double y2, double halfHeight){
        y = y2 + halfHeight + radius;
    }
    
    /*check if the object is hit from above or from the side*/
    private void handleTopLeft(double x0, double x2, double y0, double y2, double halfHeight, double halfWidth){       
        if(hitFromAbove(x0, x2, y0, y2, halfHeight, halfWidth)){
            handleHitFromAbove(y2, halfHeight);
            adjustX(x0, y0);      
        } else{           
            handleHitFromLeft(x2, halfWidth);
            adjustY(x0, y0, 1);           
        }
    }
    
    /*check if the object is hit from above or from the side */
    private void handleTopRight(double x0, double x2, double y0, double y2, double halfHeight, double halfWidth){        
        if(hitFromAbove(x0, x2, y0, y2, halfHeight, halfWidth)){
            handleHitFromAbove(y2, halfHeight);
            adjustX(x0, y0);  
        } else{
            handleHitFromRight(x2, halfWidth);
            adjustY(x0, y0, -1);
        }    
    }
    
    /*check if the object is hit from bellow or from the side */
    private void handleBottomLeft(double x0, double x2, double y0, double y2, double halfHeight, double halfWidth){        
        if(hitFromBelow(x0, x2, y0, y2, halfHeight, halfWidth)){
           handleHitFromBelow(y2, halfHeight);
           adjustX(x0, y0);  
        } else{
           handleHitFromLeft(x2, halfWidth);
           adjustY(x0, y0, 1);
        }     
    }
    
    /*check if the object is hit from bellow or from the side */
    private void handleBottomRight(double x0, double x2, double y0, double y2, double halfHeight, double halfWidth){  
        if(hitFromBelow(x0, x2, y0, y2, halfHeight, halfWidth)){
            handleHitFromBelow(y2, halfHeight);
            adjustX(x0, y0);  
        } else{           
            handleHitFromRight(x2, halfWidth);
            adjustY(x0, y0, -1);
        }
    }
    
    /*a check that the circle(bunny) has hit the the hittable object */
    private boolean hit(double y2, double x2, double halfHeight, double halfWidth){
        return((hitOnHeight(y, y2,halfHeight))&&hitOnWidth(x, x2, halfWidth));
    }
    
    /*a check if the circle is between the height coordinates of the hittable object */
    private boolean hitOnHeight(double theY, double y2, double halfHeight){
        return((theY > y2 - halfHeight - radius)&&(theY < y2 + halfHeight + radius));
    }
    
    /*a check if the circle is between the width coordinates of the hittable object */
    private boolean hitOnWidth(double theX,double x2, double halfWidth){
        return((theX > x2 - halfWidth - radius)&&(theX < x2 + halfWidth + radius));
    }
        
    /* 
     * A check if the bunny hits the object from above. It gets the coordinates of 
     * y at which the circle would have hit the object. Then it gets the coordinates of x
     * at that point, those depend on rotation of the bunny and current position. then it checks 
     * if the x is in the bounds of the width of object. If it is not - the object is still not hit
     * and would get hit from the side.
     */
    private boolean hitFromAbove(double x0, double x2,double y0, double y2, double halfHeight, double halfWidth) {
        double deltaY = (y2 - halfHeight - radius) - y0;
        double newX;
        newX = x0 + (deltaY * tangent(90-rotation));
        return (hitOnWidth(newX, x2, halfWidth));
    }
   
    /*same as hitFromAbove, just checks for the thing from above */
    private boolean hitFromBelow(double x0, double x2,double y0, double y2, double halfHeight, double halfWidth){
       double deltaY = y0 - (y2 + halfHeight + radius);
       double newX;
       newX = x0 + (deltaY * tangent(rotation-90));        
       return (hitOnWidth(newX, x2, halfWidth));
    }
    
    /* 
     * when object is hit from above or below, x for the place where it was hit is counted. 
     * it is counted using dX = dY * tan(alfa), where dX and dY is the difference between bunny starting point 
     * and hit-point (i.e. the edges of triangle), and alfa is the angle of the corner oposite to x.  
     */
    private void adjustX(double x0, double y0){
        x = x0 + ((y-y0)*tangent(90-rotation));
    }
    
    /* 
     * when object is hit from left or right, y for the place where it was hit is counted. 
     * it is counted using dY = dX * tan(alfa), where dX and dY is the difference between bunny starting point 
     * and hit-point (i.e. the edges of triangle), and alfa is the angle of the corner oposite to y. i is either 1
     * or -1 used only for angle direction adjustment. 
     */
    private void adjustY(double x0, double y0, int i){
        y = y0 + ((x-x0)* tangent(rotation*i));    
    }
    
    public void setHitBounds(Rectangle rectangle) {
        hitBounds = rectangle;        
    }
        
    public int getX() { return (int) x; }

    public int getY() { return (int) y; }
    
    public Point getCoordinates() { return (new Point(getX(), getY())); }

    /*
     * A rotation is a circular *movement* of an object around a center (or point) of rotation 
     * source: wikipedia
     * 
     * @deprecated use {@link getOrientation()} instead
     */
    @Deprecated
    public int getRotation()      { return getOrientation(); }
    
    public int getOrientation()   { return rotation; }
    
    public int getHealth()        { return health; }
    
    /*
     * why would we need to get back the radius, it doesn't change does it?
     */
    @Deprecated
    public int getRadius()        { return radius; }

    public void setBounds(Rectangle rectangle) {
        bounds = rectangle;
    }    

    private double tangent(int alfa){
        return Math.tan(Math.toRadians(alfa));
    }
}
