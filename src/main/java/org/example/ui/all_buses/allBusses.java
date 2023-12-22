package org.example.ui.all_buses;

import org.example.database.sqLiteConnector;
import org.example.Models.BusModel;
import org.example.ui.selected_bus.selectedBuspage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class allBusses extends JFrame {

/*

 */
    private String selectedRoute;

    public allBusses(){
        initializeUI();
        initializeBusList();
    }
    private void initializeUI() {
        setTitle("All Buses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
    private void initializeBusList(){

        DefaultListModel busListModel = new DefaultListModel();
        List<String> allBusesNames = sqLiteConnector.getAllBusNames();

        for (String busName : allBusesNames) {
            busListModel.addElement(busName);
        }

        JList<String> busList = new JList<>(busListModel);
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
                    BusModel busModel = getSelectedBusRoute(selectedBus);
                    //selectedBusNavigation(selectedBus);
                    selectRouteDiaglog(busModel);
                }
            }
        });
    }
    private void selectedBusNavigation(String selectedBus) {
        selectedBuspage selectedBusPage = new selectedBuspage(selectedBus);
        selectedBusPage.setVisible(true);
        allBusses.this.dispose();
    }
    private void selectRouteDiaglog(BusModel selectedBusModel){
        JFrame frame = new JFrame("Select a route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        initalizeRadioButtons(frame,selectedBusModel);

    }
    private void initalizeRadioButtons(JFrame frame,BusModel selectedBusModel){


        String firstStation = selectedBusModel.getFirstStation();
        String lastStation = selectedBusModel.getLastStation();

        JRadioButton rota1Button = new JRadioButton(firstStation + "-" + lastStation);
        JRadioButton rota2Button = new JRadioButton(lastStation + "-" + firstStation);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rota1Button);
        buttonGroup.add(rota2Button);

        JButton devamEtButton = getButton(rota1Button, rota2Button, frame);

        devamEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBusModelBusName = selectedBusModel.getBusName();
               selectedBusNavigation(selectedBusModelBusName);
            }
        });

        frame.add(rota1Button);
        frame.add(rota2Button);
        frame.add(devamEtButton);

        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    private BusModel getSelectedBusRoute(String selectedBus){
        return sqLiteConnector.retrieveBusDataFromDatabase(selectedBus);
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

