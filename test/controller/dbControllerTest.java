/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.table.DefaultTableModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johannes
 */
public class dbControllerTest {

    public dbControllerTest() {
    }

    @Test
    public void testExists() {
        System.out.println("exists");
        String rfid = "admin";
        dbController instance = new dbController();
        boolean expResult = true;
        boolean result = instance.exists(rfid);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetAndGetCredits() {
        System.out.println("set and Get Credits");
        String rfid = "test";//test bruger
        int minutes = 10;
        dbController instance = new dbController();
        minutes = instance.getCredits(rfid) + minutes;
        instance.setCredits(rfid, minutes);

        int expResult = minutes;
        int result = instance.getCredits(rfid);
        assertEquals(expResult, result);
    }

    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        String userName = "test";
        String userSirName = "subject";
        String rfid = "rfid";
        dbController instance = new dbController();
        boolean expResult = true;
        boolean result = instance.createUser(rfid, userName, userSirName, "1234");
        assertEquals(expResult, result);
    }

    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        String rfid = "rfid";
        boolean status = false;
        dbController instance = new dbController();
        boolean expResult = false;
        boolean result = instance.setStatus(rfid, status);
        assertEquals(expResult, result);
    }
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        String rfid = "test";
        dbController instance = new dbController();
        boolean expResult = false;
        boolean result = instance.getStatus(rfid);
        assertEquals(expResult, result);
    }
    @Test
    public void testDeleteUser() {
        System.out.println("delete User");
        String rfid = "rfid";
        dbController instance = new dbController();
        boolean expResult = true;
        boolean result = instance.deleteUser(rfid);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsAdmin() {
        System.out.println("isAdmin");
        String rfid = "admin";
        dbController instance = new dbController();
        boolean expResult = true;
        boolean result = instance.isAdmin(rfid);
        assertEquals(expResult, result);
    }

    @Test
    public void testLogin() {
        System.out.println("login");
        String rfid = "test";
        String password = "1234";
        dbController instance = new dbController();
        boolean expResult = true;
        boolean result = instance.login(rfid, password);
        assertEquals(expResult, result);
    }
}
