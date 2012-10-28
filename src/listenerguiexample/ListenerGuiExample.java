/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

/**
 *
 * @author johannes
 */
public class ListenerGuiExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mainGui main = new mainGui();
        adminListener admin = new adminListener(main);
        
        // TODO code application logic here
    }
}
