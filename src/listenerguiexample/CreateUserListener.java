/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import protocol.Packet;
import serial.FrameEventListener;
import serial.SerialTransceiver;

/**
 *
 * @author johannes
 */
public class CreateUserListener {

    adminGui MyAdminGui;
    CreateUserGui MyCreateUserGui;

    public CreateUserListener(adminGui MyAdminGui) {
        //create views
        this.MyAdminGui = MyAdminGui;
        MyCreateUserGui = new CreateUserGui();
        //add listeners

        MyAdminGui.addCreateUserListener(new CreateUserButtonListener());
        MyCreateUserGui.addCancelListener(new CancelButtonListener());

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

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyAdminGui.setVisible(true);
            MyCreateUserGui.setVisible(false);
        }
    }
}
