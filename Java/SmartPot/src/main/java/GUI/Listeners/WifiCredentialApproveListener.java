package GUI.Listeners;

import Utilities.LoginDialog;
import Utilities.WifiConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WifiCredentialApproveListener implements ActionListener {
    private LoginDialog dialog;
    public WifiCredentialApproveListener(LoginDialog dialog) {
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        String ssid = dialog.getSSID();
        String password = dialog.getPassword();
        WifiConnection connection = new WifiConnection(ssid, password);
        connection.connect();
        dialog.dispose();
    }
}
