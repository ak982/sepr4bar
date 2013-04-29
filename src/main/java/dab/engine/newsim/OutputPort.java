/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dab.engine.newsim;

/**
 *
 * @author eduard
 */
public class OutputPort extends Port {
    private InputPort connection;
    
    public OutputPort() {
        connection = null;
    }
    
    public void setConnection(InputPort connection) {
        this.connection = connection;
    }
    
    @Override
    public HydraulicState getHydroState() {
        return connection.getHydroState();
    }
}
