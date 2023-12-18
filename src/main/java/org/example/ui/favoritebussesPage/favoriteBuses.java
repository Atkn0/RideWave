package org.example.ui.favoritebussesPage;

import org.example.ui.selectedbuspage.selectedbuspage;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class favoriteBuses extends JFrame {

    public favoriteBuses() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Favorite Buses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);


        // Liste öğeleri
        String[] busItems = {"Bus 1", "Bus 2", "Bus 3", "Bus 4", "Bus 5"};

        // JList oluştur
        JList<String> busList = new JList<>(busItems);
        adc(busList);

        // Liste elemanına tıklandığında tetiklenecek olay dinleyicisi


        // JScrollPane içine JList'i ekle
        JScrollPane scrollPane = new JScrollPane(busList);

        // Layout Manager'ı kullanarak bileşenleri düzenle
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void adc(JList<String > busList) {
        busList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Liste elemanı seçildiğinde burası çalışır
                    String selectedBus = busList.getSelectedValue();
                    selectedBusNavigation();

                }
            }
        });


    }
    private void selectedBusNavigation(){
        selectedbuspage selectedbuspage = new selectedbuspage();
        selectedbuspage.setVisible(true);
        favoriteBuses.this.dispose();

    }
}
