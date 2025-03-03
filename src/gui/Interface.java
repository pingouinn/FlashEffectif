package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

// Components 

import gui.components.Loading;
import gui.components.DisplayActivityList;
import gui.components.SearchBar;
import gui.components.Error;
import gui.components.ValidComponent;
import gui.components.AuthInterface;

// Data classes

import dataClasses.ActivityList;
import dataClasses.CachedActions;
import dataClasses.CachedInstances;
import requests.Queries;
import utils.DateParser;

public class Interface extends JFrame {

    private JPanel panel;
    private Queries queryHandler;
    private int lastIdSaved = 0;

    public Interface() {
        setTitle("FlashEffectif");
        setSize(1920/2, 1080/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets app icon
        setIconImage(new ImageIcon("assets/img/appIcon.png").getImage());

        // Create a panel to hold components
        this.panel = new JPanel();
        add(this.panel, BorderLayout.CENTER);

        //Launch event loop in a new thread
        new Thread(() -> eventLoop()).start();
    }

    public void addBaseUi() {
        this.panel.add(new SearchBar(this.queryHandler), BorderLayout.CENTER);
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

    public void showActivityList(ActivityList activities) {
        this.panel.removeAll();
        this.addBaseUi();
        this.panel.add(new DisplayActivityList(activities.getActivities()), BorderLayout.WEST);
        redraw();
    }

    private void redraw() {
        this.panel.revalidate();
        this.panel.repaint();
    }

    private void eventLoop() {
        while (!CachedActions.shouldKillGui) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkForUpdates();
        }

        //Kill GUI
        this.dispose();
    }

    private void checkForUpdates() {
        if (CachedInstances.getNewActLstId() != lastIdSaved) {
            showLoading("Displaying activities ...");
            showActivityList(CachedInstances.getLastList());
            lastIdSaved = CachedInstances.getNewActLstId();
        } else if (CachedActions.shouldDisplayLoading) {
            showLoading("Requesting activities from server ....");
            CachedActions.shouldDisplayLoading = false;
        }
    }

    public void showAuthInterface(String username, String password) {
        this.panel.removeAll();
        this.panel.add(new AuthInterface(username, password), BorderLayout.CENTER);
        redraw();
    }

    public boolean isAuthDone() {
        return CachedActions.isAuthDone;
    }

    // Outer accessed misc methods for init and cleaning
    public void setQueryHandler(Queries queryHandler) {
        this.queryHandler = queryHandler;

        //Init query to populate activities
        showLoading("Requesting activities from server ...");
        ActivityList res = new ActivityList();
        try {
            res = queryHandler.APIgetActivitiesList(18, DateParser.stringToDate("2025-02-28"), DateParser.stringToDate("2025-03-05"));
        } catch (Exception e) {
            System.out.println("An error occured while performing the request");            
            e.printStackTrace();
        }
    }
}
