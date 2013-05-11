/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim.interfaces;

/**
 *
 * @author eduard
 */
public interface ValveView extends FailableComponentView{
    public boolean getOpen();
    public void setOpen(boolean openStatus);
}
