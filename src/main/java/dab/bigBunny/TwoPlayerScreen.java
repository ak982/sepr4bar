/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.bigBunny;

import dab.engine.simulator.Simulator;
import dab.gui.gamepanel.GamePanel;
import dab.gui.gamepanel.UIComponent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author eduard
 */
public class TwoPlayerScreen extends GamePanel implements MouseListener, ActionListener {

    BufferedImage bunny, slime;
    BunnyController controller;
    Environment environment;
    JProgressBar bar;
    private Rectangle bounds;
    private JLabel box;
    private ImageIcon boxToHit;
    private HitBoundsController hitboundsController;

    public TwoPlayerScreen(Simulator simulator, Environment en, HitBoundsController h,BunnyController bc) {
        super(simulator);
        
        this.simulator = simulator;
        this.environment = en;
        this.hitboundsController = h;
        this.controller = bc;

        controller.setBounds(new Rectangle(getWidth(), getHeight()));
        environment.setBounds(getWidth(), getHeight());

        setFocusable(true);
        this.setLayout(null);
        setBackground(Color.WHITE);
        bar = new JProgressBar(0, controller.getHealth());
        this.add(bar);
        bar.setBounds(10, 10, 100, 30);
        bar.setVisible(true);
        bar.setStringPainted(true);
       
        for(UIComponent uc : uiComponents){
                hitboundsController.addHitableComponent(uc.getComponent(),
                        uc.getLocation().x, uc.getLocation().y, uc.getWidth(), uc.getHeight());          
        }

        addMouseListener(this);

        try {
            bunny = ImageIO.read(new File("resources/bunny.jpg"));
        } catch (Exception e) {
            System.err.println("Image not found");
        }
        
         try {
            slime = ImageIO.read(new File("src/main/resources/dab/gui/slime2.png"));
        } catch (Exception e) {
            System.err.println("Image not found");
        }
        environment.setSlimeRadius(slime.getWidth()/2); 
        

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        
        AffineTransform af = new AffineTransform();

        af.translate(controller.getX(), controller.getY());
        af.rotate((90 + controller.getOrientation()) * Math.PI / 180);
        af.translate(-bunny.getWidth() / 2, -bunny.getHeight() / 2);

        for (Slime s : environment.getSlimes()) {
          float f = s.getFreshness();
          g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,f));           
          g2D.drawImage(slime, (int)s.getLocation().getX() - slime.getWidth()/2, 
                 (int) s.getLocation().getY() - slime.getHeight()/2, slime.getWidth(),
                  slime.getHeight(), this);
          
        }
        
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));

        for (BulletHole b : environment.getBullets()) {
            Ellipse2D.Double circle = new Ellipse2D.Double(b.getLocation().getX(), b.getLocation().getY(), 4.0, 4.0);
            g2D.setColor(Color.BLACK);
            g2D.fill(circle);
        }

        Ellipse2D.Double circle = new Ellipse2D.Double((double) controller.getX() - 10, (double) controller.getY() - 10, 20.0, 20.0);
        g2D.drawImage(bunny, af, this);
        g2D.setColor(Color.black);

        g2D.draw(circle);
     
        bar.setValue(controller.getHealth());
    }

    public void mousePressed(MouseEvent e) {
        Point clicked = new Point(e.getX(), e.getY());


        //Also get the power generated, check if it is > then some amount,
        //if it is - subtrackt that amount and call this:
        double distance = clicked.distance(controller.getCoordinates());
        if (distance <= controller.getRadius()) {
            //System.out.println("Bunny has been shot");
            controller.hasBeenShot();
            //Animation of shot bunny 
        } else {
            environment.addBullet(clicked); //bullet hole if missed
         
        }
    }

    public void mouseClicked(MouseEvent e) {
    } //Do nothing

    public void mouseReleased(MouseEvent e) {
    } //Do nothing

    public void mouseEntered(MouseEvent e) {
    } //Do nothing

    public void mouseExited(MouseEvent e) {
    } //Do nothing

    
    @Override
    public void actionPerformed(ActionEvent e) {
                controller.step();
                environment.step();
                repaint();
    }

}

