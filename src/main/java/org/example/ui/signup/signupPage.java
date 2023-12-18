package org.example.ui.signup;

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
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked();
            }
        });
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
}
