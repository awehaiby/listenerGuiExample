/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import controller.RFIDEventManagerSimple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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

    public adminListener(mainGui MyMainGui, RFIDEventManagerSimple rFIDEventManagerSimple, dbController db) {
        //create views
        this.db = db;
        MyAdminGui = new adminGui();
        this.MyMainGui = MyMainGui;
        this.rFIDEventManagerSimple = rFIDEventManagerSimple;
        myviewDeleteListener = new viewDeleteListener(MyAdminGui, db);
        createUser = new CreateUserListener(MyAdminGui,db);
        rFIDEventManagerSimple.setCreateUserListener(createUser);
        //add listeners
        this.MyMainGui.addLoginListener(new loginButtonListener());
        MyAdminGui.addLogoutListener(new logoutButtonListener());
    }

       class loginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String rfid = MyMainGui.getRfid();
            String pw = MyMainGui.getPassword();
            if (db.isAdmin(rfid)) {
                if (db.login(
                        rfid,
                        pw)) {
                    MyMainGui.setVisible(false);
                    MyMainGui.clear();
                    MyMainGui.setVisible(false);
                    MyAdminGui.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null, "Wrong login details", "Error", JOptionPane.ERROR_MESSAGE);
                    MyMainGui.clear();
                }

            }
        }
    }
    class logoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyMainGui.clear();
            MyMainGui.setVisible(true);
            MyAdminGui.setVisible(false);
        }
    }
}
