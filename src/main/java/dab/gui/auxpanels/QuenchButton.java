/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.auxpanels;

import dab.engine.newsim.interfaces.ReactorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;

/**
 *
 * @author eduard
 */
public class QuenchButton extends JToggleButton {
    private ReactorView linkedReactor;
    private boolean quenched;
    
    public QuenchButton(ReactorView reactor) {
        this.linkedReactor = reactor;
        quenched = false;
        setText("Quench!!");
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                linkedReactor.quench();
                quenched = true;
                setSelected(true);
                setEnabled(false);
            }
        }) ;
    }
    
    
}
