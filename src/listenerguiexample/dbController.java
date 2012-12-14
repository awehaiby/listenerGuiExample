/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johannes
 */
public class dbController {
    //fake emu data

    //boolean status = false;
    private static Statement stmt;
    String host = "jdbc:derby://localhost:1527/rejsekort";
    String uName = "rejsekort";
    String uPass = "rejsekort";
    //stop fake data

    dbController() {//connects to database
    }

    public boolean exists(String rfid) {
        boolean returner = false;

        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt;
            stmt = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                returner = true;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        return returner;
        //return sql query does this rfid exist in database?
    }

    public boolean getStatus(String rfid) {
        int credits = getCredits(rfid);
        boolean status = false;
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                status = Integer.parseInt(rs.getString("STATUS")) != 0;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        if (status) {
            if (credits > 0) {
                return status;
            } else {
                setStatus(rfid, false);
                return false;
            }
        }
        return false;
    }

    public void setCredits(String rfid, int cash) {
        int minutes = cash;
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt1;

            stmt1 = con.createStatement();
            stmt1.executeUpdate("UPDATE \"APP\".\"USER\" SET \"CREDITS\" = '" + minutes + "' WHERE \"RFID\" = '" + rfid + "'"); //UPDATE en tabel */


        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
    }

    public boolean deleteUser(String rfid) {
        boolean returner = false;
        if (!isAdmin(rfid)) {//admins cant delete other admins
            returner = true;
        }
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt;

            stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM \"APP\".\"USER\" WHERE \"RFID\" = '" + rfid + "'");// AND DELETE FROM \"APP\".\"TRAVELLOG\" WHERE \"RFID\" = '" + rfid + "'");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return returner;
    }

    boolean createUser(String rfid, String fn, String ln, String pw) {
        boolean returner = true;
        java.util.Date date = new java.util.Date();
        Timestamp current = new Timestamp(date.getTime());
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt;

            stmt = (Statement) con.createStatement();

            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                returner = false;
            }
            if (returner) {
                stmt.executeUpdate("INSERT INTO \"APP\".\"USER\" (\"RFID\",\"ADMIN\",\"PASSWORD\",\"FIRSTNAME\",\"LASTNAME\",\"CREDITS\",\"STATUS\",\"CHECKIN\") VALUES ('" + rfid + "','0','" + pw + "','" + fn + "','" + ln + "','0','0','" + current + "')");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        return returner;
    }

    public void makeUserLogTable(String rfid, DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"TRAVELLOG\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                String d1 = rs.getString("CHECKIN");
                String d2 = rs.getString("CHECKOUT");
                String d3 = rs.getString("TRAVELTIME");
                model.addRow(new Object[]{d1, d2, d3});
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
    }

    public int getCredits(String rfid) {
        int credits = 0;
        java.util.Date date = new java.util.Date();
        Timestamp current = new Timestamp(date.getTime());
        Timestamp checkIn = new Timestamp(date.getTime());
        boolean status = false;

        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                credits = Integer.parseInt(rs.getString("CREDITS"));
                status = Integer.parseInt(rs.getString("STATUS")) != 0;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                checkIn = rs.getTimestamp("CHECKIN");
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        int diffInMinutes = (int) (current.getTime() - checkIn.getTime()) / 60000;
        if (status) {
            if (diffInMinutes > credits) {
                setCredits(rfid, 0);
                return 0;
            } else {
                return credits - diffInMinutes;
            }
        }
        return credits;
    }

    public boolean setStatus(String rfid, boolean status) {

        int credits = getCredits(rfid);
        setCredits(rfid, credits);
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt;
            java.util.Date date = new java.util.Date();
            Timestamp current = new Timestamp(date.getTime());

            int mySTATUS = (status) ? 1 : 0;

            Timestamp checkIn = new Timestamp(date.getTime());

            stmt = (Statement) con.createStatement();

            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                checkIn = rs.getTimestamp("CHECKIN");
            }
            int diffInMinutes = (int) (current.getTime() - checkIn.getTime()) / 60000;
            if (status) {
                if (credits > 0) {
                    stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"CHECKIN\" = '" + current + "' WHERE RFID = '" + rfid + "'");
                    stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"STATUS\" = '" + mySTATUS + "' WHERE RFID = '" + rfid + "'");
                }
            } else {
                stmt.executeUpdate("INSERT INTO \"APP\".\"TRAVELLOG\" (\"RFID\",\"CHECKIN\",\"CHECKOUT\",\"TRAVELTIME\") VALUES ('" + rfid + "','" + checkIn + "','" + current + "','" + diffInMinutes + "')");
                stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"STATUS\" = '" + mySTATUS + "' WHERE RFID = '" + rfid + "'");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }

        return status;
    }

    public boolean isAdmin(String rfid) {
        boolean returner = false;
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                if (Integer.parseInt(rs.getString("ADMIN")) == 1) {
                    returner = true;
                }

            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        return returner;
    }

    boolean login(String rfid, String password) {
        boolean returner = false;
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '" + rfid + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                if (password.equals(rs.getString("PASSWORD"))) {
                    returner = true;
                }

            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        return returner;
    }
}
