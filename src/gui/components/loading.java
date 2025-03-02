package gui.components;

import javax.swing.*;
import java.awt.*;

public class Loading extends JPanel {

    public Loading(String customText) {
        setLayout(new BorderLayout());
        
        // Load the spinner.gif
        ImageIcon spinnerIcon = new ImageIcon("assets/img/spinner.gif");
        JLabel spinnerLabel = new JLabel(spinnerIcon);
        add(spinnerLabel, BorderLayout.CENTER);

        // Create the label
        JLabel label = new JLabel(customText, JLabel.CENTER);
        add(label, BorderLayout.SOUTH);
    }
}
