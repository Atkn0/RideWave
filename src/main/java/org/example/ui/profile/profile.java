package org.example.ui.profile;

import org.example.Models.userModel;
import org.example.database.sqLiteConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class profile extends JFrame {

    private JPanel panel1;
    

    public profile( userModel currentUserModel){
        initializeUI(currentUserModel);
        setVisible(true);
    }

    private void initializeUI(userModel currentUserModel) {
        // JFrame ayarları
        setTitle("Profile Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = createMainPanel(currentUserModel);

        // Ana pencereye paneli ekle
        add(mainPanel);

        // Pencereyi görünür yap
        setVisible(true);
    }
    private JPanel createMainPanel(userModel currentUserModel) {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Profile icon
        ImageIcon profileIcon = new ImageIcon("profile_icon.png"); // Profil ikonunun dosya yolu
        JLabel profileLabel = new JLabel(profileIcon);
        profileLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(profileLabel, constraints);

        constraints.gridy++;
        constraints.gridwidth = 1;

        String userEmail = currentUserModel.getEmail();
        String userPassword = currentUserModel.getPassword();

        // Kullanıcı bilgileri
        addLabelAndValue(mainPanel, constraints, "Email", userEmail);
        addLabelAndValue(mainPanel, constraints, "Password:", userPassword);
        addLabelAndValue(mainPanel, constraints, "Transportation Card Number:", "123456789");
        addLabelAndValue(mainPanel, constraints, "Balance:", "$32.00");

        return mainPanel;
    }
    private void addLabelAndValue(JPanel panel, GridBagConstraints constraints, String label, String value) {
        panel.add(new JLabel(label), constraints);
        constraints.gridx++;
        panel.add(new JLabel(value), constraints);
        constraints.gridx = 0;
        constraints.gridy++;
    }

}

