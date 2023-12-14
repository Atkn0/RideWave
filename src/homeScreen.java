import javax.swing.JFrame;
import java.awt.*;

public class homeScreen extends JFrame {
    public homeScreen() {
        // JFrame özelliklerini ayarlamak
        setTitle("Ana Sayfa");
        setSize(800, 600); // Genişlik ve yükseklik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
        setLayout(new BorderLayout()); // Layout yöneticisini belirleme (isteğe bağlı)


    }

    public static void main(String[] args) {

        homeScreen anaSayfa = new homeScreen();
        anaSayfa.setVisible(true);
    }
}
