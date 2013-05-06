package dab.gui.auxpanels;

import dab.engine.newsim.AbstractSimulator;
import dab.engine.newsim.components.Reactor;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * The ObamaPanel contains information of immediate importance to the user, such
 * as component failure and critical temperatures. It contains an image of
 * obama, a speech bubble and a label for text to appear
 *
 * @author Team Haddock
 *
 */
public abstract class ObamaPanel extends JPanel {

    protected JLabel lblObama;
    protected JLabel lblSpeech;
    protected JLabel lblWords;
    AbstractSimulator simulator;
    

    public ObamaPanel(AbstractSimulator sim) {
        this.simulator = sim;
        
        setBackground(Color.WHITE);
        setLayout(null);        
        

        lblWords = new JLabel();
        lblWords.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
        lblWords.setForeground(Color.BLACK);
        lblWords.setVerticalAlignment(SwingConstants.TOP);
        lblWords.setHorizontalAlignment(SwingConstants.LEFT);
        lblWords.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblWords.setBounds(210, 30, 800, 800);
        add(lblWords);

        lblSpeech = new JLabel();
        lblSpeech.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
        lblSpeech.setBounds(0, -80, 1000, 300);
        lblSpeech.setIconTextGap(50);
        lblSpeech.setIcon(new ImageIcon("src/main/resources/dab/gui/auxpanels/speech.png"));
        add(lblSpeech);
    }
    
    protected void setText(String text) {
        lblWords.setText("<html>" + text + "</html>");
    }
    
    protected final String makeWarning(String playerName, String message) {
        return "WARNING, " + playerName + ": " + message + "<br>";
    }

    protected String getWarningMessages(String playerName) {
        String temp = "";
        
        if (simulator.getReactor().coreTemperature().inKelvin() > 800) {
            temp += makeWarning(playerName, "REACTOR CORE TEMPERATURE TOO HIGH! QUENCH IT!");
        }
        
        if (simulator.getReactor().waterLevel().ratio() > Reactor.EXCESSWATER_THRESHOLD - 0.05) {
            temp += makeWarning(playerName, "Water level in reactor too high, discarding some of it.");;
        }

         for (String failedComponent : simulator.listFailedComponents()) {
            temp += makeWarning(playerName, "The " + failedComponent + " HAS FAILED");
         }
         
         return temp;
    }

    // update the panel with reactor warnings and stuff
    public abstract void update();
}
