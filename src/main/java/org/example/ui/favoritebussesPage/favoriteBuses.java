package org.example.ui.favoritebussesPage;

import org.example.ui.selectedbuspage.selectedbuspage;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class favoriteBuses extends JFrame {

    public favoriteBuses() {
        initializeUI();
        initializeBusList();
    }

    private void initializeUI() {
        setTitle("Favorite Buses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void initializeBusList(){

        String[] busItems = {"Bus 1", "Bus 2", "Bus 3", "Bus 4", "Bus 5"};
        JList<String> busList = new JList<>(busItems);
        listSelectionListener(busList);
        JScrollPane scrollPane = new JScrollPane(busList);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

    }
    private void listSelectionListener(JList<String > busList) {
        busList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Liste elemanı seçildiğinde burası çalışır
                    String selectedBus = busList.getSelectedValue();
                    selectedBusNavigation(selectedBus);

                }
            }
        });


    }
    private void selectedBusNavigation(String busCode){
        selectedbuspage selectedbuspage = new selectedbuspage(busCode);
        selectedbuspage.setVisible(true);
        favoriteBuses.this.dispose();

    }
}
