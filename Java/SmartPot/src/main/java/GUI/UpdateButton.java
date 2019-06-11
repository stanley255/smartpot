package GUI;

import GUI.Listeners.WifiConnectionListener;

import javax.swing.*;

class UpdateButton extends JButton {
    UpdateButton(SmartpotFrame frame) {
        super("Connect");
        addActionListener(new WifiConnectionListener(frame));
    }
}
