/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.CreateUserGui;
import gui.adminGui;
import protocol.Packet;
import serial.FrameEventListener;
import serial.SerialTransceiver;

/**
 *
 * @author ivo, johannes, benjamin
 */
public class CreateUserListener {

    adminGui MyAdminGui;
    CreateUserGui MyCreateUserGui;
    dbController db;

    public CreateUserListener(adminGui MyAdminGui, dbController db) {
        //create views
        this.db = db;
        this.MyAdminGui = MyAdminGui;
        MyCreateUserGui = new CreateUserGui();
        //add listeners

        MyAdminGui.addCreateUserListener(new CreateUserButtonListener());
        MyCreateUserGui.addCancelListener(new CancelButtonListener());
        MyCreateUserGui.addCreateListener(new CreateAUserListener());

    }

    public void guiSetText(String text) {
        MyCreateUserGui.setText(text);
    }

    class CreateUserButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyAdminGui.setVisible(false);
            MyCreateUserGui.setVisible(true);
        }
    }

    class CreateAUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String fn = MyCreateUserGui.getFirstName();
            String ln = MyCreateUserGui.getLastName();
            String pw = MyCreateUserGui.getPassword();
            String rfid = MyCreateUserGui.getRfid();
            if (rfid.length() > 0 && fn.length() > 0 && ln.length() > 0 && pw.length() > 0) {
                if (db.createUser(rfid, fn, ln, pw)) {
                    JOptionPane.showMessageDialog(
                            MyCreateUserGui, "user created");
                } else {
                    JOptionPane.showMessageDialog(
                            MyCreateUserGui, "rfid allready in use", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(
                        MyCreateUserGui, "missing good input data", "Error", JOptionPane.ERROR_MESSAGE);
            }
            MyAdminGui.setVisible(true);
            MyCreateUserGui.clearAll();
            MyCreateUserGui.setVisible(false);
        }
    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyAdminGui.setVisible(true);
            MyCreateUserGui.clearAll();
            MyCreateUserGui.setVisible(false);
        }
    }
}
