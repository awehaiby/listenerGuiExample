/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * efter glimrende eksempel på mvc fra
 * http://www.leepoint.net/notes-java/GUI/structure/40mvc.html
 */
package gui;

import controller.RFIDEventManagerSimple;
import controller.adminListener;
import controller.dbController;
import controller.userListener;
import protocol.TcsPacket;
import serial.SerialTransceiver;

/**
 *
 * @author johannes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        mainGui main = new mainGui();

        dbController db;
        db = new dbController();

        RFIDEventManagerSimple rFIDEventManagerSimple = new RFIDEventManagerSimple(main, db);
        //Construct another SerialTransceiver for the RFIDEventManager
        SerialTransceiver rFIDEventManagerTransceiver = new SerialTransceiver(new TcsPacket(), rFIDEventManagerSimple);
        //Set the transmitter for the RFIDManagerSimple
        rFIDEventManagerSimple.setTransmitter(rFIDEventManagerTransceiver);

        //Open the RFIDEventManager server port - it waits for messages from
        //the Card Reader
        rFIDEventManagerSimple.openPort();

        adminListener admin = new adminListener(main, rFIDEventManagerSimple, db);
        userListener user = new userListener(main, db);

        main.setVisible(true);

        // TODO code application logic here
    }
}
