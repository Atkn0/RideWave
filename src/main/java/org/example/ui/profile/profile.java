package org.example.ui.profile;

import org.example.models.userModel;
import org.example.database.sqLiteConnector;

import javax.swing.*;
import java.sql.Connection;


public class profile extends JFrame {

    private userModel currentUserModel;
    private JPanel panel1;

    public profile( userModel currentUserModel){
        this.currentUserModel = currentUserModel;
        Connection connection = sqLiteConnector.connect();
        String getusername = sqLiteConnector.getUserByName("berke");
    }
}

