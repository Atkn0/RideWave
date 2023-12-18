package org.example.ui.login;

import org.example.ui.signup.signupPage;

import javax.swing.*;

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

    private void loginButtonActionPerformed() {
        /*
        homepage homePage = new homepage();
        homePage.setVisible(true);
        loginPageForm.this.dispose();

         */
        
    }

    private void initializeTheForm() {
        add(loginPanel);
        setSize(600,600);
        setTitle("RideWave Login");
        setLocationRelativeTo(null);
    }

}
