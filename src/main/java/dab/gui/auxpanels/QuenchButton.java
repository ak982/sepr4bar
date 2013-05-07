/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.auxpanels;

import dab.engine.newsim.interfaces.ReactorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 *
 * @author eduard
 */
public class QuenchButton extends JToggleButton {
    private ReactorView linkedReactor;
    
    public QuenchButton(ReactorView reactor) {
        this.linkedReactor = reactor;
        setText("Quench!!");
        setIcon(new ImageIcon(QuenchButton.class.getResource("active.png")));
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                linkedReactor.quench();
                setSelected(true);
                setEnabled(false);
            }
        }) ;
    }
    
    
}
