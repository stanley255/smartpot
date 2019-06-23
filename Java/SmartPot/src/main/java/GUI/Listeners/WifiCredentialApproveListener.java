package GUI.Listeners;

import GUI.SmartpotFrame;
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
        if(checkCredentials(ssid, password)) {
            WifiConnection connection = new WifiConnection(ssid, password);
            String response = connection.connect();
            dialog.dispose();
            ((SmartpotFrame) dialog.getParent()).setText(response);
        }
    }

    private boolean checkCredentials(String ssid, String password) {
        return !ssid.isEmpty() && !password.isEmpty();
    }
}
