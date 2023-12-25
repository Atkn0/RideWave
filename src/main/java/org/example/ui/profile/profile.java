package org.example.ui.profile;

import org.example.Models.userModel;
import org.example.ui.home.homePage;
import org.example.ui.login.loginPageForm;

import javax.swing.*;
import java.awt.*;


public class profile extends JFrame {

    private JPanel panel1;
    private JButton backButton;
    private JButton anotherButton;
    

    public profile( userModel currentUserModel){
        initializeUI(currentUserModel);
        buttonClickedListener(currentUserModel.getEmail());
    }

    private void initializeUI(userModel currentUserModel) {
        setTitle("Profile Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = createMainPanel(currentUserModel);
        add(mainPanel);

        setVisible(true);
    }
    private JPanel createMainPanel(userModel currentUserModel) {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);

        ImageIcon originalIcon = new ImageIcon("src/main/resources/assets/user.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel profileLabel = new JLabel(scaledIcon);
        profileLabel.setHorizontalAlignment(JLabel.CENTER);
        profileLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        mainPanel.add(profileLabel, constraints);

        constraints.gridy++;
        constraints.gridwidth = 1;

        String userEmail = currentUserModel.getEmail();
        String userPassword = currentUserModel.getPassword();
        String cardNumber = currentUserModel.getCardNumber();
        String balance = currentUserModel.getBalance();

        addLabelAndValue(mainPanel, constraints, "Email", userEmail);
        addLabelAndValue(mainPanel, constraints, "Password:", userPassword);
        addLabelAndValue(mainPanel, constraints, "Transportation Card Number:", cardNumber);
        addLabelAndValue(mainPanel, constraints, "Balance:", "$" + balance);

        backButton = new JButton("Back To Homepage");
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.weighty = 0;

        mainPanel.add(backButton, constraints);

        anotherButton = new JButton("Sign Out!");

        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.gridx++;
        mainPanel.add(anotherButton, constraints);

        return mainPanel;
    }
    private void addLabelAndValue(JPanel panel, GridBagConstraints constraints, String label, String value) {
        panel.add(new JLabel(label), constraints);
        constraints.gridx++;
        panel.add(new JLabel(value), constraints);
        constraints.gridx = 0;
        constraints.gridy++;
    }
    private void buttonClickedListener(String userEmail){
        backButton.addActionListener(e->{
            homePage homePage = new homePage(userEmail);
            homePage.setVisible(true);
            profile.this.dispose();
        });

        anotherButton.addActionListener(e->{
            loginPageForm loginPage = new loginPageForm();
            loginPage.setVisible(true);
            profile.this.dispose();
        });
    }
}

