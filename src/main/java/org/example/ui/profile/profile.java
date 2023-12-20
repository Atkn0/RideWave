package org.example.ui.profile;

import org.example.database.sqLiteConnector;

import javax.swing.*;
import java.sql.Connection;

public class profile extends JFrame {
    private JPanel panel1;

    public profile(){
        Connection connect = sqLiteConnector.connect();
        sqLiteConnector.getAllUsers(connect);
    }

}
