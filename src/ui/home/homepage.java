package ui.home;

import javax.swing.*;
import java.awt.*;

public class homepage extends JFrame {

    private JPanel homepagePanel;
    GridBagConstraints gbc = new GridBagConstraints();
    JButton allBusesButton;
    JButton favoriteBusesButton;
    JButton profileButton;
    public homepage() {
        initializeThePanel();
        initializeButtons();
        buttonClickedListener();

        add(homepagePanel);
    }

    private void initializeThePanel(){
        setTitle("RideWave");
        setSize(600, 600);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(150, 100));
        return button;
    }
    private void initializeButtons(){
        allBusesButton = createButton("All Buses");
        favoriteBusesButton = createButton("Favorite Buses");
        profileButton = createButton("Profile");

        gbc.gridx = 0;
        homepagePanel.add(allBusesButton, gbc);

        gbc.gridx = 1;
        homepagePanel.add(favoriteBusesButton, gbc);

        gbc.gridx = 2;
        homepagePanel.add(profileButton, gbc);

    }
    private void buttonClickedListener(){
        allBusesButton.addActionListener(e -> {
            System.out.println("All Buses Button Clicked");
        });
        favoriteBusesButton.addActionListener(e -> {
            System.out.println("Favorite Buses Button Clicked");
        });
        profileButton.addActionListener(e -> {
            System.out.println("Profile Button Clicked");
        });
    }

}
