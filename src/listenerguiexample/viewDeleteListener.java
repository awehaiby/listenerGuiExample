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
public class viewDeleteListener {

    adminGui MyAdminGui;
    viewDeleteUser myViewDeleteGui;
    dbController db;

    public viewDeleteListener(adminGui MyAdminGui, dbController db) {
        //create views
        this.db = db;
        this.MyAdminGui = MyAdminGui;
        myViewDeleteGui = new viewDeleteUser();
        //add listeners

        MyAdminGui.addViewDeleteUserListener(new viewDeleteButtonListener());
        myViewDeleteGui.addBackToAdminListener(new backToAdminListener());
        myViewDeleteGui.addSubmitListener(new submitListener());
        myViewDeleteGui.addClearListener(new clearListener());
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

    class backToAdminListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
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

            //myViewDeleteGui.setText(rfid);
            if (status) {
                myViewDeleteGui.setText("Travel minutes: " + credits + " Checked in");
            } else {
                myViewDeleteGui.setText("Travel minutes: " + credits + " Checked out");
            }
            //System.out.println("hello" + name + " " + sirname);
        }
    }
}
