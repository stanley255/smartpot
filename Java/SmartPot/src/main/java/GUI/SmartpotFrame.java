package GUI;

import javax.swing.*;
import java.awt.*;

public class SmartpotFrame extends JFrame {
    public SmartpotFrame() throws HeadlessException {
        initialSetup();
        componentSetup();
        setVisible(true);
    }

    private void initialSetup() {
        setTitle("SmartPot");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void componentSetup() {
        JPanel panel = new JPanel();
        panel.add(new UpdateButton(this));
        this.getContentPane().add(panel);
    }
}
