package org.example.ui.allBussesPage;

import org.example.ui.favoritebussesPage.favoriteBuses;
import org.example.ui.selectedbuspage.selectedbuspage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class allBusses extends JFrame {


    private String selectedRoute;

    public allBusses(){
        initializeUI();
        initializeBusList();
    }
    private void initializeUI() {
        setTitle("Favorite Buses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
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
                    String selectedBus = busList.getSelectedValue();
                    //selectedBusNavigation(selectedBus);
                    selectRouteDiaglog();
                }
            }
        });
    }
    private void selectedBusNavigation(String selectedBus) {
        selectedbuspage selectedBusPage = new selectedbuspage(selectedBus, selectedRoute);
        selectedBusPage.setVisible(true);
        allBusses.this.dispose();
    }




    private void selectRouteDiaglog(){
        JFrame frame = new JFrame("Select a route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JRadioButton rota1Button = new JRadioButton("Anadolu - Estu");
        JRadioButton rota2Button = new JRadioButton("Estu - Anadolu");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rota1Button);
        buttonGroup.add(rota2Button);

        JButton devamEtButton = getButton(rota1Button, rota2Button, frame);

        frame.add(rota1Button);
        frame.add(rota2Button);
        frame.add(devamEtButton);

        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton getButton(JRadioButton rota1Button, JRadioButton rota2Button, JFrame frame) {
        JButton devamEtButton = new JButton("Next");
        devamEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rota1Button.isSelected()) {
                    selectedRoute = "Route 1";
                } else if (rota2Button.isSelected()) {
                    selectedRoute = "Route 2";
                } else {
                    selectedRoute = null;
                }

                if (selectedRoute != null) {
                    System.out.println("Selected Route: " + selectedRoute);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please choose a route.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return devamEtButton;
    }
}

