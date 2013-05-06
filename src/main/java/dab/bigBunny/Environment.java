/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.newsim.utils.Constants;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Eduard Nicodei
 * @author Aiste Kiskyte
 */
public class Environment {

    private final static int DEFAULT_FAILURE_TIME = 100;
    private final static int DEFAULT_CANT_FAIL = 300;
    private int width;
    private int height;
    private TemporaryObjectList<Slime> slimes;
    private TemporaryObjectList<BulletHole> bullets;
    private boolean softwareFailure;
    private int sfTime, sfCantFailTime;
    private boolean headache;
    private int headacheTime;
    private final int DEFAULT_HEADACHE_TIME = 100;
    private int slimeRadius;

    public Environment() {
        slimes = new TemporaryObjectList<>();
        bullets = new TemporaryObjectList<>();
        
        softwareFailure = false;
        sfTime = 0;
        sfCantFailTime = 0;

        headache = false;
        headacheTime = 0;
    }
    
    public void setBounds(int width, int height){
        this.width = width;
        this.height = height;
    }

    // idea: keep them sorted them by freshness
    public void step() {

        stepSoftwareFailure();
        stepHeadache();
        Random rnd = new Random();
        if (rnd.nextDouble() < 0.035) { // 1% chance
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            slimes.add(new Slime(x, y, 8 * 30, rnd.nextDouble() * Math.PI));
            //System.out.println("new slime " + x + " " + y);
        }
        slimes.step();
        bullets.step();

    }

    // return the newest one that intersects
    public Slime intersectWithSlime(Point p, int radius) {
        for (Slime s : slimes.getRawList()) {
            int sqdistance = (int) p.distanceSq(s.getLocation());
            int sqSumRadius = (radius + slimeRadius) * (radius + slimeRadius);
            if (sqdistance < sqSumRadius) { // they intersect
                return s;
            }
        }
        return null;
    }

    // NOTE: return arrayList;
    public LinkedList<Slime> getSlimes() {
        return slimes.getRawList();
    }

    public void addBullet(Point location) {
        bullets.add(new BulletHole(location));
    }

    //CopyPasting from Slime. Bad. Sorry. 
    public LinkedList<BulletHole> getBullets() {
        return bullets.getRawList();
    }

    public void startSoftwareFailure() {
        if (sfCantFailTime <= 0) {
            softwareFailure = true;
            sfTime = DEFAULT_FAILURE_TIME;
            sfCantFailTime = DEFAULT_CANT_FAIL;
            //call software failure in the powerplant
        }
    }

    private void stepSoftwareFailure() {
        if (softwareFailure) {
            sfTime--;
            if (sfTime <= 0) {
                softwareFailure = false;
            }
        }
        if (sfCantFailTime > 0) {
            sfCantFailTime--;
        }
    }

    public boolean getSoftwareFailure() {
        return softwareFailure;
    }

    public void startHeadache() {
        headache = true;
        if (headacheTime <= 0) {
            headacheTime = DEFAULT_HEADACHE_TIME;
        }
    }

    private void stepHeadache() {
        if (headache) {
            headacheTime--;
            if (headacheTime <= 0) {
                headache = false;
                System.out.println("stoping the headache");
            }
        }
    }

    public boolean getHeadache() {
        return headache;
    }
    
    public void setSlimeRadius(int slimeRadius){
        this.slimeRadius = slimeRadius;
    }
}
