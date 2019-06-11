package Utilities;

import GUI.CancelButton;
import GUI.ConnectButton;
import GUI.SmartpotFrame;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField ssid = new JTextField(15);
    private JPasswordField password = new JPasswordField();
    public LoginDialog(SmartpotFrame frame) {
        super(frame, "Wifi credentials", true);
        createContent();
        pack();
        setResizable(false);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createContent() {
        JPanel labelPanel = new JPanel(new GridLayout(2,1));
        labelPanel.add(new JLabel("SSID"));
        labelPanel.add(new JLabel("Heslo"));

        JPanel inputPanel = new JPanel(new GridLayout(2,1));
        inputPanel.add(ssid);
        inputPanel.add(password);

        JPanel credentialsPanel = new JPanel();
        credentialsPanel.add(labelPanel);
        credentialsPanel.add(inputPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new ConnectButton(this));
        buttonPanel.add(new CancelButton(this));

        setLayout(new BorderLayout());
        add(credentialsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getSSID() {
        return ssid.getText().trim();
    }
}
