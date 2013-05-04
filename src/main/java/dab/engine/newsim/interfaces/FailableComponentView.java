/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.interfaces;

/**
 *
 * @author eduard
 */
public interface FailableComponentView {
    public boolean hasFailed();
    public void fixDamage();
    public int getDamage();
    public void fail(int dmg);
}
