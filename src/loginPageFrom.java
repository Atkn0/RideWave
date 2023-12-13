import javax.swing.*;

public class loginPageFrom extends JFrame {

    JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public loginPageFrom(){
        add(panel1);
        setSize(400,400);
        setTitle("RideWave Login");

        loginButton.addActionListener(e -> {
            String username = textField1.getText();
            String password = passwordField1.getText();

            if(username.equals("admin") && password.equals("admin")){
                JOptionPane.showMessageDialog(null,"Login Successful");
            }
            else{
                JOptionPane.showMessageDialog(null,"Login Failed");
            }
        });


    }

}
