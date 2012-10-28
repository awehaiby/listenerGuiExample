/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * efter glimrende eksempel på mvc fra
 * http://www.leepoint.net/notes-java/GUI/structure/40mvc.html
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
        userListener user = new userListener(main);
        main.setVisible(true);


        // TODO code application logic here
    }
}
