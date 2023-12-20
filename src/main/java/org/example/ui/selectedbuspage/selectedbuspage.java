package org.example.ui.selectedbuspage;

import javax.swing.*;
import java.awt.*;

public class selectedbuspage extends JFrame {
    private String selectedBusCode;
    private String selectedRoute;

    public selectedbuspage(String selectedBusCode, String selectedRoute) {
        this.selectedBusCode = selectedBusCode;
        this.selectedRoute = selectedRoute;

        initializeUI();
        System.out.println("Selected Bus Code: " + selectedBusCode);
        System.out.println("Selected Route: " + selectedRoute);
    }

    private void initializeUI() {
        setTitle("Selected Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Add components or actions related to the selected bus code and route
        // For example, you can add JLabels to display the selectedBusCode and selectedRoute
        JLabel busCodeLabel = new JLabel("Selected Bus Code: " + selectedBusCode);
        JLabel routeLabel = new JLabel("Selected Route: " + selectedRoute);

        setLayout(new GridLayout(2, 1));
        add(busCodeLabel);
        add(routeLabel);
    }
}
