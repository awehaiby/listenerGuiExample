/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.adminGui;
import gui.viewDeleteUser;

/*
 *
 * @author ivo, johannes, benjamin
 */
public class viewDeleteListener {

    adminGui MyAdminGui;
    viewDeleteUser myViewDeleteGui;
    dbController db;
    String currentRfid;

    public viewDeleteListener(adminGui MyAdminGui, dbController db) {
        //create views
        this.db = db;
        currentRfid = "";
        this.MyAdminGui = MyAdminGui;
        myViewDeleteGui = new viewDeleteUser();
        //add listeners

        MyAdminGui.addViewDeleteUserListener(new viewDeleteButtonListener());
        myViewDeleteGui.addBackToAdminListener(new backToAdminListener());
        myViewDeleteGui.addSubmitListener(new submitListener());
        myViewDeleteGui.addClearListener(new clearListener());
        myViewDeleteGui.addDeleteListener(new deleteUserListener());
    }

    public void guiSetText(String text) {
        myViewDeleteGui.setText(text);
    }

    class viewDeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyAdminGui.setVisible(false);
            myViewDeleteGui.setVisible(true);
            myViewDeleteGui.clearAll();
        }
    }

    class deleteUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete user", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if (!db.deleteUser(myViewDeleteGui.getRfidString())) {
                    JOptionPane.showMessageDialog(
                            myViewDeleteGui, "Cannot delete admins", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class backToAdminListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            currentRfid = "";
            MyAdminGui.setVisible(true);
            myViewDeleteGui.setVisible(false);

        }
    }

    class clearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            myViewDeleteGui.clearAll();
        }
    }

    class submitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String rfid = myViewDeleteGui.getRfidString();

            int credits = db.getCredits(rfid);
            boolean status = db.getStatus(rfid);
            System.out.println(rfid + " " + credits + " " + status);
            if (db.exists(rfid)) {
                //myViewDeleteGui.setText(rfid);
                if (status) {
                    myViewDeleteGui.setText("Travel minutes: " + credits + " Checked in");
                } else {
                    myViewDeleteGui.setText("Travel minutes: " + credits + " Checked out");
                }
            } else {
                JOptionPane.showMessageDialog(
                        myViewDeleteGui, "User does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
