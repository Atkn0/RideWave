import javax.swing.*;
import java.awt.*;

public class homeScreen extends JFrame {
    private JPanel anaPanel3;
    private JPanel digerPanel;
    public homeScreen() {
        // JFrame özelliklerini ayarlamak
        setTitle("Ana Sayfa");
        setSize(800, 600); // Genişlik ve yükseklik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
        //setLayout(new BorderLayout()); // Layout yöneticisini belirleme (isteğe bağlı)
        String[] busses = {"Öğe 1", "Öğe 2", "Öğe 3", "Öğe 4", "Öğe 5"};
        JList<String> myList = new JList<>(busses);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(myList);
        add(scrollPane, BorderLayout.CENTER);
        // Seçili öğeleri dinlemek için bir ActionListener ekleyin
        myList.addListSelectionListener(e -> {
            // Seçilen öğeleri işleyin (burada bir konsol çıktısı veriyoruz)
            System.out.println("Seçilen otobüs: " + myList.getSelectedValue());
        });

        // Düğme ekleyin
        JButton myButton = new JButton("Seç");
        myButton.addActionListener(e -> {
            // Düğmeye tıklandığında yapılacak işlemler
            System.out.println("Düğmeye tıklandı!");
        });
        add(myButton, BorderLayout.SOUTH);


    }

    public static void main(String[] args) {

        homeScreen anaSayfa = new homeScreen();
        anaSayfa.setVisible(true);
    }
}
