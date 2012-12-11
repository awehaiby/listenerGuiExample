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
    loginDialog loginGui;
    RFIDEventManagerSimple rFIDEventManagerSimple;
    dbController db;
    viewDeleteListener myviewDeleteListener;
    
    public adminListener(mainGui MyMainGui, RFIDEventManagerSimple rFIDEventManagerSimple, dbController db) {
        //create views
        this.db = db;
        MyAdminGui = new adminGui();
        this.MyMainGui = MyMainGui;
        loginGui = new loginDialog(this.MyMainGui, true);
        this.rFIDEventManagerSimple = rFIDEventManagerSimple;
        myviewDeleteListener = new viewDeleteListener(MyAdminGui, db);
        createUser = new CreateUserListener(MyAdminGui);
        rFIDEventManagerSimple.setCreateUserListener(createUser);
        //add listeners
        loginGui.addCancelListener(new loginCancelButtonListener());
        loginGui.addLoginListener(new loginButtonListener());
        MyMainGui.addAdminListener(new adminButtonListener());
        MyAdminGui.addLogoutListener(new logoutButtonListener());
    }

    class adminButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            loginGui.setVisible(true);
        }
    }

    class loginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (db.login(loginGui.getFirstName(),
                    loginGui.getLastName(),
                    loginGui.getPassword(),
                    1)) {
                loginGui.setVisible(false);
                loginGui.clear();
                MyMainGui.setVisible(false);
                MyAdminGui.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(
        null, "Wrong login details", "Error", JOptionPane.ERROR_MESSAGE);
                loginGui.clear();
            }
        }
    }

    class loginCancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
                loginGui.setVisible(false);
                loginGui.clear();
                MyMainGui.setVisible(true);
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
