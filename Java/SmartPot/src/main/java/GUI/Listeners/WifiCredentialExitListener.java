package GUI.Listeners;

import Utilities.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WifiCredentialExitListener implements ActionListener {
    private JDialog dialog;
    public WifiCredentialExitListener(LoginDialog dialog) {
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }
}
