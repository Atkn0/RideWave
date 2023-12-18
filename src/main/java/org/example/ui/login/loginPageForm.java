package org.example.ui.login;

import org.example.database.sqLiteConnector;
import org.example.ui.homePage.homePageFrom;
import org.example.ui.signup.signupPage;

import javax.swing.*;
import java.sql.Connection;

public class loginPageForm extends JFrame{

    private JTextField emailTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JPanel loginPanel;
    private JLabel signupLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;

    public loginPageForm(){
        initializeTheForm();
        buttonClickedListener();
    }


    private void buttonClickedListener(){
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed();
            }
        });
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpLabelMouseClicked();
            }
        });
    }
    private void signUpLabelMouseClicked() {
        signupPage signupPage = new signupPage();
        signupPage.setVisible(true);
        loginPageForm.this.dispose();
    }
    private Connection sqlConnection(){
        Connection connection = sqLiteConnector.connect();
        return connection;
    }
    private void loginButtonActionPerformed() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        boolean isLoginSuccess = auth(email,password);
        if (isLoginSuccess){
            homePageFrom homepage = new homePageFrom();
            homepage.setVisible(true);
            loginPageForm.this.dispose();
        }else {
            System.out.println("Giriş Yapılamadı!");
        }
    }
    private void initializeTheForm() {
        add(loginPanel);
        setSize(600,600);
        setTitle("RideWave Login");
        setLocationRelativeTo(null);
    }
    private boolean auth(String email, String password){
        return sqLiteConnector.authentication(email,password);
    }

}
