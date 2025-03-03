package gui.components;

import javax.swing.*;

import dataClasses.CachedActions;
import utils.ConfigManager;

import java.awt.*;

public class AuthInterface extends JPanel {
    public AuthInterface(String username, String password) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour des composants

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel imageLabel = new JLabel(new ImageIcon("assets/img/appIcon.png"));
        add(imageLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField usernameField = new JTextField(username);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JPasswordField passwordField = new JPasswordField(password);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton signInButton = new JButton("Sign In");
        add(signInButton, gbc);

        // Link the button to an action
        signInButton.addActionListener(e -> {
            ConfigManager configManager = new ConfigManager();
            String usernameInput = usernameField.getText();
            String passwordInput = new String(passwordField.getPassword());
            configManager.setProperty("username", usernameInput);
            configManager.setProperty("password", passwordInput);
            CachedActions.username = usernameInput;
            CachedActions.password = passwordInput;
            CachedActions.isAuthDone = true;
        });
    }
}
