package org.example.ui.selectedbuspage;

import javax.swing.*;

public class selectedbuspage extends JFrame {

    public selectedbuspage(String selectedBusCode){
        initializeUI();
        System.out.println("selected bus code" + selectedBusCode);
    }

    private void initializeUI() {
        setTitle("Selected Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
}
