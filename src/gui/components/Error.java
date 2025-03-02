package gui.components;

import javax.swing.*;
import java.awt.*;

public class Error extends JPanel {
    
    public Error(String customText) {
        setLayout(new BorderLayout());
        
        ImageIcon errorIcon = new ImageIcon("assets/img/error.png");
        JLabel errorLabel = new JLabel(errorIcon);
        add(errorLabel, BorderLayout.CENTER);

        // Create the label
        JLabel label = new JLabel(customText, JLabel.CENTER);
        add(label, BorderLayout.SOUTH);
    }
}
