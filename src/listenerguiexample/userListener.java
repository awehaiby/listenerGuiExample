/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 *
 * @author johannes
 */
public class userListener {

    mainGui MyMainGui;
    userGui MyUserGui;
    dbController db;
    String currentLoggedInRfid;

    public userListener(mainGui MyMainGui, dbController db) {
        //create views
        currentLoggedInRfid = "";
        this.db = db;
        MyUserGui = new userGui();

        this.MyMainGui = MyMainGui;
        //add listeners
        MyUserGui.addOkListener(new okButtonListener());
        this.MyMainGui.addLoginListener(new loginButtonListener());
        MyUserGui.addLogoutListener(new logoutButtonListener());
    }

    class okButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int addCredits = MyUserGui.getCredits();
            if (addCredits <= 0) {
                JOptionPane.showMessageDialog(
                        null, "Submit positive amount", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int credits = db.get_credits(currentLoggedInRfid) + addCredits;
                db.setCredits(currentLoggedInRfid,
                        credits);
                MyUserGui.setText(credits);
            }
        }
    }

    class loginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String rfid = MyMainGui.getRfid();
            String pw = MyMainGui.getPassword();
            if (!db.isAdmin(rfid)) {
                if (db.login(
                        rfid,
                        pw)) {
                    MyMainGui.setVisible(false);
                    MyMainGui.clear();
                    MyMainGui.setVisible(false);
                    MyUserGui.setVisible(true);
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
            currentLoggedInRfid = "";
            MyMainGui.clear();
            MyMainGui.setVisible(true);
            MyUserGui.setVisible(false);
        }
    }
}
