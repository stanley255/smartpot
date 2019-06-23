package GUI.Listeners;

import GUI.SmartpotFrame;
import Utilities.BulkDataCollector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BulkDataCollectListener implements ActionListener {
    private SmartpotFrame frame;
    public BulkDataCollectListener(SmartpotFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BulkDataCollector bdc = new BulkDataCollector(frame);
        System.out.println(bdc.magic());
    }
}
