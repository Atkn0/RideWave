import javax.swing.*;

public class loginPageForm extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JLabel signUpLabel;

    public loginPageForm(){

        add(panel1);
        setSize(600,600);
        setTitle("RideWave Login");

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Sign Up Clicked");
            }
        });

        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Login Clicked");
            }
        });


    }

}
