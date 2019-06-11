package GUI;

import GUI.Listeners.WifiCredentialExitListener;
import Utilities.LoginDialog;

import javax.swing.*;

public class CancelButton extends JButton {
    public CancelButton(LoginDialog dialog) {
        super("Cancel");
        addActionListener(new WifiCredentialExitListener(dialog));
    }
}
