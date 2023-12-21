package org.example;


import org.example.Models.userModel;
import org.example.database.sqLiteConnector;
import org.example.ui.homePage.homePageFrom;
import org.example.ui.login.loginPageForm;
import org.example.ui.profile.profile;
import org.example.ui.selectedbuspage.selectedBuspage;
import org.example.ui.signup.signupPage;

import javax.swing.*;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {
        homePageFrom hm = new homePageFrom();
        hm.setVisible(true);
    }
}
