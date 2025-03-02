package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class SearchBar extends JPanel {
    
    private JTextField searchField;
    private JCheckBox incompleteOnlyCheckBox;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton searchButton;

    public SearchBar() {
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
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchText = searchField.getText();
        boolean incompleteOnly = incompleteOnlyCheckBox.isSelected();
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();

        // Perform search logic here
        System.out.println("Search Text: " + searchText);
        System.out.println("Incomplete Only: " + incompleteOnly);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }
}
