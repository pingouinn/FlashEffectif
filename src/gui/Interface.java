package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.HashMap;

// Components 

import gui.components.Loading;
import gui.components.DisplayActivityList;
import gui.components.SearchBar;
import gui.components.Error;
import gui.components.ValidComponent;

// Data classes

import dataClasses.Activity;

public class Interface extends JFrame {

    private JPanel panel;

    public Interface() {
        setTitle("FlashEffectif");
        setSize(1920/2, 1080/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold components
        this.panel = new JPanel();
        add(this.panel, BorderLayout.CENTER);
    }

    public void addBaseUi() {
        this.panel.add(new SearchBar(), BorderLayout.CENTER);
    }

    public void showLoading(String text) {
        this.panel.removeAll();
        this.panel.add(new Loading(text), BorderLayout.CENTER);
        redraw();
    }

    public void showError(String text) {
        this.panel.removeAll();
        this.panel.add(new Error(text), BorderLayout.CENTER); 
        redraw();
    }
    
    public void showValid(String text) {
        this.panel.removeAll();
        this.panel.add(new ValidComponent(text), BorderLayout.CENTER);
        redraw();
    }

    public void showActivityList(HashMap<String,Activity> activities) {
        this.panel.removeAll();
        this.addBaseUi();
        this.panel.add(new DisplayActivityList(activities), BorderLayout.WEST);
        redraw();
    }

    private void redraw() {
        this.panel.revalidate();
        this.panel.repaint();
    }
}
