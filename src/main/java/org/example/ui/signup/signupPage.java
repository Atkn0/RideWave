package org.example.ui.signup;

import org.example.database.sqLiteConnector;
import org.example.ui.home.homePageFrom;
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
    private JLabel cardNumber;
    private JTextField cardNumberTextField;

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
        String cardNumber = cardNumberTextField.getText();

        // Şifre kontrolü
        if (isPasswordValid(password)) {
            // Diğer işlemleri devam ettir
            boolean isUserCreateSuccess = createUser(email, password, cardNumber);
            boolean isUserFavoriteSuccess = addUserToFavoritesBuses(email);

            if (isUserCreateSuccess & isUserFavoriteSuccess) {
                homePageFrom homePage = new homePageFrom(email);
                homePage.setVisible(true);
                signupPage.this.dispose();
            } else {
                System.out.println("Kullanıcı oluşturulamadı!");
            }
        } else {
            System.out.println("Geçersiz şifre!");
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

    private boolean createUser(String email,String password,String cardNumber){
        return sqLiteConnector.createUserSqlite(email,password,cardNumber);
    }
    private boolean addUserToFavoritesBuses(String userEmail){
        return sqLiteConnector.addUserToFavoriteBuses(userEmail);
    }

    private boolean isPasswordValid(String password) {
        // Password length check
        if (password.length() < 8 || password.length() > 64) {
            JOptionPane.showMessageDialog(null, "Password must be between 8 and 64 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Uppercase letter check
        if (!password.matches(".*[A-Z].*")) {
            JOptionPane.showMessageDialog(null, "Password must contain an uppercase letter.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Lowercase letter check
        if (!password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(null, "Password must contain a lowercase letter.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Digit check
        if (!password.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Password must contain a digit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Symbol check
        if (!password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:'\",.<>/?].*")) {
            JOptionPane.showMessageDialog(null, "Password must contain a symbol.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // If all checks pass, consider the password valid
        return true;
    }
}
