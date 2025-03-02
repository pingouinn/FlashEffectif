package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dataClasses.Activity;

public class ActivityComponent extends JPanel {
    private Activity activityData;
    private String name;
    private ImageIcon imageIcon;

    public ActivityComponent(Activity act) {
        this.activityData = act;
        this.name = act.getName();

        if (act.isActivityComplete()) {
            ImageIcon tempImageIcon = new ImageIcon("assets/img/green_dot.png");
            Image image = tempImageIcon.getImage();
            this.imageIcon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        } else {
            ImageIcon tempImageIcon = new ImageIcon("assets/img/red_dot.png");
            Image image = tempImageIcon.getImage();
            this.imageIcon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }

        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1, true));
        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(nameLabel, BorderLayout.WEST);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        setPreferredSize(new Dimension(300, 50));

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        add(imageLabel, BorderLayout.EAST);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle click event
                System.out.println("Component clicked: " + name);
            }
        });
    }
}
