
package dab.gui.mainpanels;

import dab.gui.application.MainWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;


/**
 * The panel to be used as a background for menus before the 
 * game starts
 */
public class DaMMenu extends JLayeredPane {
    private MainMenu mainMenu;
    private MainWindow mw;
    private BufferedImage background;
    
    
    public DaMMenu(MainWindow mw) {        
        this.mw = mw;
        
        try {
            background = ImageIO.read(new File("src/main/resources/dab/gui/intro/intro_bkg.png"));
        } catch (Exception e) {
            System.err.println("Image not found");
        }
            
        setVisible(true);    
        mainMenu = new MainMenu(mw, this);
        mw.changeMenu(mainMenu);       
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g2D.drawImage(background,600,0,background.getWidth(),background.getHeight(),this);
    }
    
}
