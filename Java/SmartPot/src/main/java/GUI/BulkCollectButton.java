package GUI;

import GUI.Listeners.BulkDataCollectListener;

import javax.swing.*;

class BulkCollectButton extends JButton {
    BulkCollectButton(SmartpotFrame frame) {
        super("Bulk");
        addActionListener(new BulkDataCollectListener(frame));
    }
}
