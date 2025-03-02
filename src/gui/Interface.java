package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;

// Components 

import gui.components.loading;

public class Interface extends JFrame {

    private JPanel panel;
    private JLabel centeredText;

    public Interface() {
        setTitle("FlashEffectif");
        setSize(1920/2, 1080/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold components
        this.panel = new JPanel();
        this.panel.setBackground(java.awt.Color.WHITE);

        centeredText = new JLabel("Initialising FlashEffectif...");
        this.panel.add(centeredText);

        add(this.panel, BorderLayout.CENTER);
    }

    public void updateTextCentered(String text) {
        centeredText.setText(text);
    }

    public void showLoading(String text) {
        this.panel.removeAll();
        this.panel.setBackground(java.awt.Color.WHITE);
        this.panel.add(new loading(text), BorderLayout.CENTER);
        this.panel.revalidate();
        this.panel.repaint();
    }

    public void showTextCentered(String text) {
        this.panel.removeAll();
        this.centeredText.setText(text);
        this.panel.add(this.centeredText);
        this.panel.revalidate();
        this.panel.repaint();
    }
}
