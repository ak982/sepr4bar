package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Displays the Help Screen. Image is located in Resources/Menu/
 * 
 * @author Haddock
 *
 */

public class HelpScreen extends JDialog {

    private MainWindow mainWindow;
    private JLayeredPane invoker;
    private Dimension size;
    
    public HelpScreen(){
        
        //initialize the help screen with back button and an image
       
       JLabel helpImage = new JLabel();
       ImageIcon icon = new ImageIcon("resources/menu/HelpImage.png");
       helpImage.setIcon(icon);
       setBounds(0,0,icon.getIconWidth(), icon.getIconHeight());
       
       
       //requestFocus();
       JButton back = new JButton("Back");
       back.setBackground(Color.PINK);
       back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //disapear
                HelpScreen.this.setVisible(false);
              
            }
        });
        back.setBounds(10, 10, 70, 30);
       // add(back, BorderLayout.CENTER);
       // add(helpImage, BorderLayout.CENTER);
        add(back);
        add(helpImage);
        
    }
    
}
