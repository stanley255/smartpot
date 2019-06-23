package GUI;

import javax.swing.*;
import java.awt.*;
public class SmartpotFrame extends JFrame {
    private JEditorPane text;
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
        panel.add(new BulkCollectButton(this));
        this.getContentPane().add(text = new JEditorPane(), BorderLayout.CENTER);
        this.getContentPane().add(panel, BorderLayout.NORTH);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
