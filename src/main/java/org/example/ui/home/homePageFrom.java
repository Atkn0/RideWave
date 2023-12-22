package org.example.ui.home;

import org.example.Models.userModel;
import org.example.ui.all_buses.allBusses;
import org.example.ui.favorite_buses.favoriteBuses;
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


    public homePageFrom(String userEmail) {
        initializeThePanel();
        initializeButtons();
        buttonClickedListener(userEmail);

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
    private void buttonClickedListener(String userEmail){
        allBusesButton.addActionListener(e -> {
            setAllBusesButtonClicked();
        });
        favoriteBusesButton.addActionListener(e -> {
           setFavoriteBusesButton(userEmail);
        });
        profileButton.addActionListener(e -> {
            setProfileButton(userEmail);
        });
    }
    private void setAllBusesButtonClicked(){
        allBusses  allBusses = new allBusses();
        allBusses.setVisible(true);
        homePageFrom.this.dispose();
    }
    private void setFavoriteBusesButton(String userEmail){
        //Burada loginPage'den gelen email verisi alınmalı
        userModel currentUserModel= getUserModel(userEmail);
        favoriteBuses favoritebuses = new favoriteBuses();
        favoritebuses.setVisible(true);
        homePageFrom.this.dispose();
    }
    private void setProfileButton(String userEmail){
        userModel userModel= getUserModel(userEmail);
        profile profile = new profile(userModel);
        profile.setVisible(true);
        homePageFrom.this.dispose();
    }
    private userModel getUserModel(String userEmail){
        return  sqLiteConnector.currentProfileModel(userEmail);
    }

    }

