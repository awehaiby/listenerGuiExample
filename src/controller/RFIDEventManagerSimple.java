/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import protocol.TcsPacket;
import serial.FrameEvent;
import serial.FrameEventListener;
import serial.SerialTransceiver;
import java.util.TooManyListenersException;
import javax.swing.Timer;
import listenerguiexample.CreateUserListener;
import listenerguiexample.dbController;
import listenerguiexample.mainGui;
import protocol.Packet;

/**
 * The
 * <code>RFIDEventManagerSimple</code> class is the central controller in the
 * application. It receives requests from the serial port, processes them and
 * transmits the response. The
 * <code>RFIDEventManagerSimple</code> is implemented using the State design
 * pattern.
 *
 * @version 16/02/10
 * @author ibr
 */
public class RFIDEventManagerSimple implements FrameEventListener {

    private SerialTransceiver transmitter;
    private String portNumber = "/dev/cu.usbserial-000013FA";//
    private String source = "02";
    private String destination = "01";
    private Packet packet;
    CreateUserListener myCreateUserListener;
    dbController db;
    mainGui main;
    String scanned = "";

    public RFIDEventManagerSimple(mainGui main, dbController db) {
        this.db = db;
        this.main = main;

    }

    public void setCreateUserListener(CreateUserListener myCreateUserListener) {
        this.myCreateUserListener = myCreateUserListener;
    }

    /**
     * Get the value of source
     *
     * @return the value of source
     */
    public synchronized String getSource() {
        return source;
    }

    /**
     * Set the value of source
     *
     * @param source new value of source
     */
    public synchronized void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the value of packet
     *
     * @return the value of packet
     */
    public synchronized Packet getPacket() {
        return packet;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public synchronized String getDestination() {
        return destination;
    }

    /**
     * Set the value of destination
     *
     * @param destination new value of destination
     */
    public synchronized void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Set the value of transmitter
     *
     * @param transmitter new value of transmitter
     */
    public synchronized void setTransmitter(SerialTransceiver transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Open the transmitter serial port.
     *
     * @throws TooManyListenersException
     */
    public synchronized void openPort() throws TooManyListenersException {
        if (transmitter != null) {
            transmitter.openPort(portNumber);
        }
    }

    /**
     * Close the transmitter serial port.
     */
    public synchronized void closePort() {
        if (transmitter != null) {
            transmitter.closePort();
        }
    }

    /**
     * Send the
     * <code>RFIDResponse</code> as an
     * <code>TcsPacket</code> using the serial transmitter.
     *
     * @param rFIDResponse the response to send
     */
    public synchronized void sendRFIDResponse(String status, String data) {
        TcsPacket mETPacket = new TcsPacket(source, destination, status, data);
        transmitter.transmit(mETPacket.getBytes());
    }

    /**
     * The method called by the
     * <code>SerialFrame</code> when a complete data packet is received.
     *
     * @param frameEvent the frame event
     */
    public synchronized void frameReady(FrameEvent frameEvent) {
        byte[] received = frameEvent.getData();
        System.out.print("\nReceived at Server: [");
        System.out.println(new String(received) + "]");
        packet = new TcsPacket(received);
        System.out.println("           command: [" + packet.getCommandStatus() + "]");
        System.out.println("           data:    [" + packet.getData() + "]");
        //TO DO Process request and send response
        if (main.isVisible()) {
            processRequestMode1(packet);
        } else {
            processRequestMode2(packet);
        }
    }

    private void processRequestMode1(Packet packet) {//intereference without gui
        //THIS CODE IS FOR SIMPLE DEMONSTRATION ONLY.
        //IT IS DIFFICULT TO MAINTAIN AND TEST.
        //IT SHOULD BE REPLACED BY EG. COMMAND PATTERN IN THE LATER DESIGN
        String command = packet.getCommandStatus();
        String returnMessage = "";
        if (command.equals("01")) {
            int creds = db.getCredits(packet.getData());
            scanned = packet.getData();
            Timer timer = new Timer(4000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    scanned = "";
                    sendRFIDResponse("00", "");//checked out
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start(); // Go go go!
            returnMessage = String.format("%-16s", "credit:" + creds);
            boolean status = db.getStatus(scanned);
            if (status) {
                sendRFIDResponse("01", returnMessage);//checked in
            } else {
                sendRFIDResponse("02", returnMessage);//checked out
            }
        } else if (command.equals("02")) {
            sendRFIDResponse("00", "");
            scanned = "";
        } else if (command.equals("03")) {
            if (scanned.length() > 0) {
                int creds = db.getCredits(scanned);
                if (creds > 0) {
                    boolean status = db.setStatus(scanned, !db.getStatus(scanned));
                    if (status) {
                        returnMessage = String.format("%-16s", "credit:" + creds);
                        sendRFIDResponse("04", returnMessage);//checked in

                    } else {
                        returnMessage = String.format("%-16s", "credit:" + creds);
                        sendRFIDResponse("05", returnMessage);//checked out

                    }
                    //sendRFIDResponse("04", returnMessage);
                } else {
                        returnMessage = String.format("%-16s", "credit:" + creds);
                    sendRFIDResponse("03", returnMessage);
                }
            }
            scanned = "";
        }
    }

    private void processRequestMode2(Packet packet) {//interference with gui
        //THIS CODE IS FOR SIMPLE DEMONSTRATION ONLY.
        //IT IS DIFFICULT TO MAINTAIN AND TEST.
        //IT SHOULD BE REPLACED BY EG. COMMAND PATTERN IN THE LATER DESIGN
        String command = packet.getCommandStatus();
        String returnMessage = "";

        if (command.equals("01")) {
            sendRFIDResponse("00", "");
            myCreateUserListener.guiSetText(packet.getData());
        }
    }
}
