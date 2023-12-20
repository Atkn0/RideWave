package org.example;


import org.example.database.sqLiteConnector;
import org.example.ui.homePage.homePageFrom;
import org.example.ui.login.loginPageForm;
import org.example.ui.signup.signupPage;

import javax.swing.*;
import java.sql.Connection;


public class Main {


    public static void main(String[] args) {
       homePageFrom homePageFrom = new homePageFrom();
       homePageFrom.setVisible(true);
    }
}
