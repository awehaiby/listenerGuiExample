/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author johannes
 */
public class adminListener {

    mainGui MyMainGui;
    adminGui MyAdminGui;

    public adminListener(mainGui MyMainGui) {
        //create views
        MyAdminGui = new adminGui();
        this.MyMainGui = MyMainGui;
        //add listeners
        MyMainGui.addAdminListener(new adminButtonListener());
        MyAdminGui.addLogoutListener(new logoutButtonListener());
    }

    class adminButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyMainGui.setVisible(false);
            MyAdminGui.setVisible(true);
        }
    }

    class logoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyMainGui.setVisible(true);
            MyAdminGui.setVisible(false);
        }
    }
}
