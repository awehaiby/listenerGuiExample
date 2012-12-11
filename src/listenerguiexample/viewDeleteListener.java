/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author johannes
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
            db.deleteUser(myViewDeleteGui.getRfidString());
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

            int credits = db.get_credits(rfid);
            boolean status = db.getStatus(rfid);
            System.out.println(rfid + " " + credits + " " + status);
            if (db.exists(rfid)) {
                //myViewDeleteGui.setText(rfid);
                if (status) {
                    myViewDeleteGui.setText("Travel minutes: " + credits + " Checked in");
                } else {
                    myViewDeleteGui.setText("Travel minutes: " + credits + " Checked out");
                }
                //System.out.println("hello" + name + " " + sirname);
            } else {
                JOptionPane.showMessageDialog(
                        myViewDeleteGui, "user does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
