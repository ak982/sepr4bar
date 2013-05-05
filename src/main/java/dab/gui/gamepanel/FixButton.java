/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.gui.gamepanel;

import dab.engine.newsim.interfaces.FailableComponentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author eduard
 */
public class FixButton extends JButton {

    FailableComponentView linkedComponent;
    
    public FixButton(FailableComponentView component) {
        this.linkedComponent = component;
        setSize(80, 30);
        setIcon(new ImageIcon(FixButton.class.getResource("wrench.png")));
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                    linkedComponent.fixDamage();
                update();
            }
        });
        setFocusable(false);
    }
    
    public void update() {
        setText(String.valueOf(linkedComponent.getDamage()));
        if (linkedComponent.hasFailed()) {
            setVisible(true);
            
        } else {
            setVisible(false);
        }
        
    }
}
