package GUI;

import GUI.Listeners.WifiCredentialApproveListener;
import Utilities.LoginDialog;

import javax.swing.*;

public class ConnectButton extends JButton {
    public ConnectButton(LoginDialog dialog) {
        super("Connect");
        addActionListener(new WifiCredentialApproveListener(dialog));
    }
}
