package org.example.ui.signup;

import org.example.database.sqLiteConnector;
import org.example.ui.homePage.homePageFrom;
import org.example.ui.login.loginPageForm;

import javax.swing.*;

public class signupPage extends JFrame{
    private JTextField emailTextField;
    private JTextField passwordTextField;
    private JButton signupButton;
    private JLabel title;
    private JLabel email;
    private JLabel password;
    private JLabel loginLabel;
    private JPanel signupPanel;

    public signupPage(){
        initializeTheForm();
        buttonClickedListeners();

    }

    private void buttonClickedListeners(){
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked();
            }
        });

        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonClicked();
            }
        });

    }

    private void signUpButtonClicked(){
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        boolean isSuccess = createUser(email,password);
        if (isSuccess){
            homePageFrom homePage = new homePageFrom();
            homePage.setVisible(true);
            signupPage.this.dispose();
        }else{
            System.out.println("Kullanıcı oluşturulamadı!");
        }
    }

    private void initializeTheForm() {add(signupPanel);
        setTitle("Sign Up");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private void loginLabelMouseClicked() {
        loginPageForm loginPage = new loginPageForm();
        loginPage.setVisible(true);
        signupPage.this.dispose();
    }

    private boolean createUser(String email,String password){
      boolean isCreated = sqLiteConnector.createUserSqlite(email,password);
      return isCreated;
    }
}
