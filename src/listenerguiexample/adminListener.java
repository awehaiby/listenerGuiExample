/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import controller.RFIDEventManagerSimple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author johannes
 */
public class adminListener {

    mainGui MyMainGui;
    adminGui MyAdminGui;
    CreateUserListener createUser;
    RFIDEventManagerSimple rFIDEventManagerSimple;
    dbController db;
    viewDeleteListener myviewDeleteListener;

    public adminListener(mainGui MyMainGui, RFIDEventManagerSimple rFIDEventManagerSimple,dbController db) {
        //create views
        this.db=db;
        MyAdminGui = new adminGui();
        this.MyMainGui = MyMainGui;
        this.rFIDEventManagerSimple = rFIDEventManagerSimple;
myviewDeleteListener=new viewDeleteListener(MyAdminGui,db);
        createUser = new CreateUserListener(MyAdminGui);
        rFIDEventManagerSimple.setCreateUserListener(createUser);
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
