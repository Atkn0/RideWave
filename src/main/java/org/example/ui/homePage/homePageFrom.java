package org.example.ui.homePage;

import org.example.Models.userModel;
import org.example.ui.allBussesPage.allBusses;
import org.example.ui.favoritebussesPage.favoriteBuses;
import org.example.ui.profile.profile;
import org.example.database.sqLiteConnector;

import javax.swing.*;
import java.awt.*;

public class homePageFrom extends JFrame {

    private JPanel homepagePanel;
    GridBagConstraints gbc = new GridBagConstraints();
    JButton allBusesButton;
    JButton favoriteBusesButton;
    JButton profileButton;


    public homePageFrom() {
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
            setAllBusesButtonClicked();
        });
        favoriteBusesButton.addActionListener(e -> {
           setFavoriteBusesButton();
        });
        profileButton.addActionListener(e -> {
            setProfileButton();
        });
    }
    private void setAllBusesButtonClicked(){
        allBusses  allBusses = new allBusses();
        allBusses.setVisible(true);
        homePageFrom.this.dispose();
    }
    private void setFavoriteBusesButton(){
        favoriteBuses favoritebuses = new favoriteBuses();
        favoritebuses.setVisible(true);
        homePageFrom.this.dispose();
    }
    private void setProfileButton(){
        userModel userModel= getUserModel();
        profile profile = new profile(userModel);
        profile.setVisible(true);
        homePageFrom.this.dispose();
    }
    private userModel getUserModel(){


        return  sqLiteConnector.currentProfileModel();
    }

    }

