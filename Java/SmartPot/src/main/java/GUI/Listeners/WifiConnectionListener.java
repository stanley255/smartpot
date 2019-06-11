package GUI.Listeners;

import GUI.SmartpotFrame;
import Utilities.LoginDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WifiConnectionListener implements ActionListener {
    private SmartpotFrame frame;
    public WifiConnectionListener(SmartpotFrame frame) {
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Initializing connection..");
        LoginDialog login = new LoginDialog(frame);
        login.setVisible(true);
    }
}
