package gui.components;

import javax.swing.*;

import dataClasses.ActivityList;
import dataClasses.CachedActions;
import dataClasses.CachedInstances;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import requests.Queries;

public class SearchBar extends JPanel {
    
    private JTextField searchField;
    private JCheckBox incompleteOnlyCheckBox;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton searchButton;
    private Queries queryHandler;

    public SearchBar(Queries queryHandler) {
        this.queryHandler = queryHandler;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Search field
        searchField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(searchField, gbc);

        // Incomplete only checkbox
        incompleteOnlyCheckBox = new JCheckBox("Incomplet seulement");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(incompleteOnlyCheckBox, gbc);

        // Start date spinner
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");
        startDateSpinner.setEditor(startDateEditor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Start Date:"), gbc);
        gbc.gridx = 1;
        add(startDateSpinner, gbc);

        // End date spinner
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
        endDateSpinner.setEditor(endDateEditor);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(new JLabel("End Date:"), gbc);
        gbc.gridx = 3;
        add(endDateSpinner, gbc);

        // Search button
        searchButton = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        add(searchButton, gbc);

        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> performSearch()).start();
            }
        });
    }

    private void performSearch() {
        String searchText = searchField.getText();
        boolean incompleteOnly = incompleteOnlyCheckBox.isSelected();
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();
        
        CachedActions.shouldDisplayLoading = true;

        // Call the API to get the search results
        ActivityList res = new ActivityList();
        try {
            res = queryHandler.APIgetActivitiesList(18, startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (Exception e) {
            System.out.println("An error occured while performing the request");
            e.printStackTrace();
        }

        if (incompleteOnly) {
            res.filterCompleteActivities();
        }

        if (!searchText.isEmpty()) {
            res.filterActivitiesByText(searchText);
        }

        CachedInstances.addActivityList(CachedInstances.getNewActLstId(), res);
    }
}
