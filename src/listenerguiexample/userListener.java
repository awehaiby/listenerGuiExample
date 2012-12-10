/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.*;

/**
 *
 * @author johannes
 */
public class userListener {
    mainGui MyMainGui;
    userGui MyUserGui;
    dbController db;
    public userListener(mainGui MyMainGui,dbController db){
       //create views
        this.db=db;
        MyUserGui = new userGui();
        this.MyMainGui = MyMainGui;
        //add listeners
        MyMainGui.addUserListener(new userButtonListener());
        MyUserGui.addLogoutListener(new logoutButtonListener());
    }

    class userButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyMainGui.setVisible(false);
            MyUserGui.setVisible(true);
        }
    }

    class logoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            MyMainGui.setVisible(true);
            MyUserGui.setVisible(false);
        }
    }
    
}
