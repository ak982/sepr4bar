package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays the Help Screen. Image is located in Resources/Menu/
 * 
 * @author Haddock
 *
 */

public class HelpScreen extends JPanel {

    private MainWindow mainWindow;
    
    public HelpScreen(MainWindow mw)
    {
        this.mainWindow = mw;
        
        //initialize the help screen with back button and an image
       setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       JLabel helpImage = new JLabel();
       helpImage.setIcon(new ImageIcon("resources/menu/HelpImage.png"));
       helpImage.setBounds(0, 0, 700, 500);
       setVisible(true);
        JButton back = new JButton("Back");
        back.setBackground(Color.PINK);
        System.out.println("help");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //go back to the caller (main menu) when pressed
                mainWindow.showOptions();
            }
        });
        back.setBounds(10, 10, 70, 30);
        add(back, BorderLayout.CENTER);
        add(helpImage, BorderLayout.CENTER);
        
    }

}
