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

    public String getRfidByName(String name, String sirname) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stmt1;
            stmt1 = (Statement) con.createStatement();
            String sql = "select * from \"APP\".\"USER\" where \"FIRSTNAME\" = '" + name + "' AND \"LASTNAME\" = '" + sirname + "'";

            ResultSet rs = stmt1.executeQuery(sql);
            String fn = "";
            while (rs.next()) {
                fn = rs.getString("RFID");
            }
            return fn;

        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }
        return "";

        //return sql query get rfid by name;
    }

    /*public boolean exists(String rfid) {
     try {
     Connection con = DriverManager.getConnection(host, uName, uPass);

     Statement stmt1;
     stmt1 = (Statement) con.createStatement();
     String sql = "select * from \"APP\".\"USER\" where \"RFID\" = '"+rfid+"'";

     ResultSet rs = stmt1.executeQuery(sql);
     boolean fn=false; 
     while (rs.next()) {
     fn = rs.
     }
     return fn;

     } catch (SQLException err) {
     System.out.println(err.getMessage());

     }
     return false;
     //return sql query does this rfid exist in database?
     }
     */
    public boolean getStatus(String rfid) {
        int credits = get_credits(rfid);
        boolean status = false;
        /*	int a= get credits(rfid();

         //boolean status = sql query get status by rfid;

         if(status == 1){
         if(a<=0){
         sql query set status by rfid(0);
         return false;
         }else{
         return true;
         }
         return false;
         */
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
        /*
         * lav cash om til minutes. 
         * tjek credits
         * sæt credtis til credits+minutes
         */
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

    public int get_credits(String rfid) {
        int credits = 0;
        java.util.Date date = new java.util.Date();
        Timestamp current = new Timestamp(date.getTime());
        Timestamp checkIn = new Timestamp(date.getTime());
        boolean status = false;

        /*int a= sql get credits(rfid();
         timestamp b = current time;
         timestamp c= sql query get last time by rfid;
         boolean status = sql query get status by rfid;

         if(status = true){
         a = b-c;
         //current time minus last time (it will end in minus if you dont have enough credits)
         if(a<0){
         return 0;
         }
         }
         return a
         */
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
//        System.out.println(credits + " " + current + " " + checkIn + " " + status+" "+diffInMinutes);
    }

    public boolean setStatus(String rfid, boolean status) {
        //sql set last timestamp to current time;
        //sql query set status by rfid (status)
        //this.status = status;
        int credits = get_credits(rfid);
        setCredits(rfid,credits);
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
            if (status) {
                if (credits > 0) {
                    stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"CHECKIN\" = '" + current + "' WHERE RFID = '" + rfid + "'");
                    stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"STATUS\" = '" + mySTATUS + "' WHERE RFID = '" + rfid + "'");
                }
            } else {
                stmt.executeUpdate("INSERT INTO \"APP\".\"TRAVELLOG\" (\"RFID\",\"CHECKIN\",\"CHECKOUT\",\"TRAVELTIME\") VALUES ('" + rfid + "','" + checkIn + "','" + current + "','0')");
                stmt.executeUpdate("UPDATE \"APP\".\"USER\" SET \"STATUS\" = '" + mySTATUS + "' WHERE RFID = '" + rfid + "'");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());

        }

        return status;
    }
}

/*
constructor(){
connects to database etc
}



public String getRfidByName(string name, string sirname){
	return sql query get rfid by name;
}



public boolean exists(rfid){
	return sql query does this rfid exist in database?
}



public boolean getSTATUS(rfid){
	int a= get credits(rfid();

	boolean status = sql query get status by rfid;

	if(status == true && a<1){
	sql query set status by rfid(false);
	return false;
	}
	return status;
}

public int get credits(rfid){
	int a= get credits(rfid();
	timestamp b = current time;
	timestamp c= sql query get last time by rfid;
	boolean status = sql query get status by rfid;

	if(status = true){
	a = b-c;
	//current time minus last time (it will end in minus if you dont have enough credits)
	if(a<0){
	return 0;
	}
	}
	return a
}

//returns true if setstatus suceeded
public boolean setSTATUS(rfid, boolean status){
	sql set last timestamp to current time;
	sql query set status by rfid (status)
}

rfid, name, lastname, credits, status


///////////
eksempel på at finde den sidste nye timestamp til et rfid i db2
SELECT a.ID, 

(SELECT count(*) 
FROM posts 
WHERE a.ID = posts.post_author 
AND posts.post_type = 'post' 
AND posts.post_status = 'publish') AS post_count, 

(SELECT posts.ID 
FROM posts 
WHERE a.ID = posts.post_author 
AND posts.post_type = 'post' 
AND posts.post_status = 'publish' 
ORDER BY posts.post_date DESC LIMIT 1) AS latest_post_ID 

FROM USERs As a 
ORDER BY post_count DESC


*/