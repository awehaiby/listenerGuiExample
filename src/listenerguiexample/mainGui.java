/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenerguiexample;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author johannes
 */
public class mainGui extends javax.swing.JFrame {

    int backgroundColor = 0;
    Timer myTimer;

    /**
     * Creates new form mainGui
     */
    public mainGui() {
        initComponents();
        myTimer = new Timer(5, new TimerListener());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JButton();
        rfid = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 204));

        login.setText("login");

        rfid.setText("rfid");

        password.setText("jPasswordField1");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(login)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(password)
                            .add(rfid))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(rfid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(password, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(login))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainGui().setVisible(true);
            }
        });
    }

    class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            backgroundColor--;
            Color col = new Color(backgroundColor, 102, 204);
            getContentPane().setBackground(col);
            if (backgroundColor < 1) {
                myTimer.stop();
            }
        }
    }

    public void addLoginListener(ActionListener al) {
        login.addActionListener(al);
    }

    public String getRfid() {
        return rfid.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public void clear() {
        rfid.setText("rfid    ");
        password.setText("********");
    }

    public void showTerminalInput() {
        backgroundColor = 255;
        myTimer.start();
    }

    public String getSerialPort(String[] serialPorts) {

        String favoritePizza = (String) JOptionPane.showInputDialog(this,
                "Choose a Terminal Port",
                "Terminal Port",
                JOptionPane.QUESTION_MESSAGE,
                null,
                serialPorts,
                serialPorts[serialPorts.length-1]);
        return favoritePizza;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton login;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField rfid;
    // End of variables declaration//GEN-END:variables
}
