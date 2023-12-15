package ui.signup;

import ui.login.loginPageForm;

import javax.swing.*;

public class signup extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JPanel signUpPanel;
    private JLabel loginLabel;

    public signup() {
        initializeTheForm();

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked();
            }
        });

    }

    private void loginLabelMouseClicked() {
        loginPageForm loginPage = new loginPageForm();
        loginPage.setVisible(true);
        signup.this.dispose();
    }
    private void initializeTheForm() {add(signUpPanel);
        setTitle("Sign Up");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
