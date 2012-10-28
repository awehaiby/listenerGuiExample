/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.event.ActionEvent;

/**
 *
 * @author johannes
 */
public class adminListener{
    mainGui MyMainGui;
    adminGui MyAdminGui;
    
    public adminListener(mainGui MyMainGui){
        this.MyMainGui=MyMainGui;
        //add listeners
        MyMainGui.addAdminListener(this);
        MyMainGui.addUserListener(this);
        
        MyAdminGui=new adminGui();
    }
    
}
