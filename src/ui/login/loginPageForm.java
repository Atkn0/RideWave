package ui.login;

import javax.swing.*;
import ui.signup.signup;
import ui.home.homepage;

public class loginPageForm extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JLabel signUpLabel;

    public loginPageForm(){

        initializeTheForm();


        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed();
            }
        });

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpLabelMouseClicked();
            }
        });


    }

    private void loginButtonActionPerformed() {
        homepage homePage = new homepage();
        homePage.setVisible(true);
        loginPageForm.this.dispose();
    }


    private void initializeTheForm() {
        add(panel1);
        setSize(600,600);
        setTitle("RideWave Login");
        setLocationRelativeTo(null);
    }
    private void signUpLabelMouseClicked() {
        signup signupPage = new signup();
        signupPage.setVisible(true);
        loginPageForm.this.dispose();

    }

}
