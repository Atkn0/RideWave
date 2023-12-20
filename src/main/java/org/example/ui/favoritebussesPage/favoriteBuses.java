package org.example.ui.favoritebussesPage;

import org.example.ui.selectedbuspage.selectedbuspage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;

public class favoriteBuses extends JFrame {
    private String selectedRoute;
    private DefaultListModel<String> busListModel;

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

    private void initializeBusList() {
        busListModel = new DefaultListModel<>();
        JList<String> busList = new JList<>(busListModel);
        listSelectionListener(busList);
        JScrollPane scrollPane = new JScrollPane(busList);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // Fetch bus data from the database and populate the list

    }

    private void listSelectionListener(JList<String> busList) {
        busList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedBus = busList.getSelectedValue();
                    selectRouteDialog(selectedBus);
                }
            }
        });
    }

    private void selectedBusNavigation(String selectedBus, String selectedRoute) {
        selectedbuspage selectedBusPage = new selectedbuspage(selectedBus, selectedRoute);
        selectedBusPage.setVisible(true);
        favoriteBuses.this.dispose();
    }

    private void selectRouteDialog(String selectedBus) {
        JFrame frame = new JFrame("Select a route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        ButtonGroup buttonGroup = new ButtonGroup();

        // Fetch route data from the database based on the selected bus
        String[] routes = retrieveRoutesFromDatabase(selectedBus);

        // Create radio buttons dynamically based on the database content
        for (String route : routes) {
            JRadioButton routeButton = new JRadioButton(route);
            buttonGroup.add(routeButton);
            frame.add(routeButton);
        }

        JButton devamEtButton = getButton(frame, selectedBus);

        frame.add(devamEtButton);

        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String[] retrieveRoutesFromDatabase(String selectedBus) {
        String[] routes = null;

        // SQLite database connection
        String jdbcURL = "jdbc:sqlite:C:\\Users\\imtekmuhendislik\\Downloads\\sqlite-tools-win-x64-3440200\\deneme.db";

        try (Connection connection = DriverManager.getConnection(jdbcURL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT location, destination FROM buses WHERE bus_name = ?")) {

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, selectedBus);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Count the number of rows in the result set
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }

                // Initialize the routes array with the size of the result set
                routes = new String[rowCount];

                // Re-execute the result set to retrieve the values

                int index = 0;
                while (resultSet.next()) {
                    // Modify the column names "location" and "destination" with your actual column names
                    String location = resultSet.getString("location");
                    String destination = resultSet.getString("destination");

                    // Concatenate location and destination to form the route name
                    String routeName = location + " - " + destination;

                    // Store the route names in the array
                    routes[index++] = routeName;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return routes;
    }

    private JButton getButton(JFrame frame, String selectedBus) {
        JButton devamEtButton = new JButton("Next");
        devamEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = getSelectedRadioButton(frame);

                if (selectedButton != null) {
                    selectedRoute = selectedButton.getText();
                    System.out.println("Selected Route: " + selectedRoute);

                    // Perform any action with selectedBus and selectedRoute
                    selectedBusNavigation(selectedBus, selectedRoute);

                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please choose a route.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return devamEtButton;
    }

    private JRadioButton getSelectedRadioButton(JFrame frame) {
        // Iterate through components in the frame to find the selected radio button
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    return radioButton;
                }
            }
        }
        return null;
    }


    }

