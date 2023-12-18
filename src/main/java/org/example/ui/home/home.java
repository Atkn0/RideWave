package org.example.ui.home;

import javax.swing.*;
import java.awt.*;

public class home extends JFrame {
    private CardLayout cardLayout;
    private JPanel kartPanel;

    public home() {
        setTitle("Ana Sayfa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        kartPanel = new JPanel(cardLayout);

        // Sayfa 1
        JPanel favori = new JPanel();
        favori.add(new JLabel("Favori Otobüsler"));

        // Sayfa 2
        JPanel allbusses = new JPanel();
        allbusses.add(new JLabel("Bütün Otobüsler"));

        // Sayfa 3
        JPanel profile = new JPanel();
        profile.add(new JLabel("Profil"));

        kartPanel.add(favori, "Favori otobüsler");
        kartPanel.add(allbusses, "Bütün Otobüsler");
        kartPanel.add(profile, "Profil");

        DefaultListModel<String> sayfaListModel = new DefaultListModel<>();
        sayfaListModel.addElement("Favori Otobüsler");
        sayfaListModel.addElement("Bütün Otobüsler");
        sayfaListModel.addElement("Profil");

        JList<String> sayfaListesi = new JList<>(sayfaListModel);
        sayfaListesi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sayfaListesi.addListSelectionListener(e -> {
            String secilenSayfa = sayfaListesi.getSelectedValue();
            if (secilenSayfa != null) {
                cardLayout.show(kartPanel, secilenSayfa);
            }
        });

        JScrollPane listScrollPane = new JScrollPane(sayfaListesi);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(listScrollPane, BorderLayout.WEST);
        panel.add(kartPanel, BorderLayout.CENTER);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



}
