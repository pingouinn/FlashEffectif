package gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import dataClasses.Activity;

public class DisplayActivityList extends JPanel {

    public DisplayActivityList(HashMap<String,Activity> activities) {
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        if (activities.isEmpty()) {
            activityPanel.add(new JLabel("No activities found"));
        } else {
            for (Activity activity : activities.values()) {
                activityPanel.add(new ActivityComponent(activity));
            }
        }

        JScrollPane scrollPane = new JScrollPane(activityPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
