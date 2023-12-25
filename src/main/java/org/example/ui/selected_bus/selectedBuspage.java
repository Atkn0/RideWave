package org.example.ui.selected_bus;

import org.example.Models.BusModel;
import org.example.database.sqLiteConnector;
import org.example.ui.all_buses.allBusses;
import org.example.ui.favorite_buses.favoriteBuses;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class selectedBuspage extends JFrame {

    private JPanel selectedBusPanel;
    JButton addToFavoriteButton;
    JButton backButton;

    private int currentStationIndex = -1;
    private ArrayList<String> stationList;


    public selectedBuspage(String selectedBusName, String selectedBusCode,String userEmail,String parentPage,String selectedRoute) {
        System.out.println("selectedbuspage selectedRoute = " + selectedRoute);
        initializeUI(selectedBusName,userEmail,selectedRoute);
        buttonClickedListeners(userEmail,selectedBusName,parentPage);
    }

    private void initializeUI(String selectedBusName,String email,String selectedRoute) {
        setTitle("Selected Bus Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel oluştur
        selectedBusPanel = new JPanel();
        selectedBusPanel.setLayout(new BorderLayout());

        BusModel selectedBusModel = sqLiteConnector.retrieveBusDataFromDatabase(selectedBusName);


        String busNumber = selectedBusModel.getBusName();
        String crowdPercentage = String.valueOf(selectedBusModel.getBusCrowd());
        String labelText = String.format("%s -- BusCrowd : %s", busNumber, crowdPercentage);
        JLabel busInfoLabel = new JLabel(labelText, SwingConstants.CENTER);
        busInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme
        selectedBusPanel.add(busInfoLabel, BorderLayout.NORTH);

        System.out.println("selected route in selectedBusPage : " + selectedRoute);

        if (selectedRoute.equals("First Route")){
            stationList = sqLiteConnector.getStationsByBusname(selectedBusName);
        } else if (selectedRoute.equals("Second Route")) {
            stationList = sqLiteConnector.getStationsByBusname(selectedBusName);
            Collections.reverse(stationList);
        }

        if (currentStationIndex == -1) {
            currentStationIndex = getRandomCurrentStationIndex(stationList.size());
        }

        // StationListPanel oluştur
        JPanel stationListPanel = new JPanel();
        stationListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Boşluk ekleme
        stationListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme


        // Station icon ve isimleri ekleyerek oluşturulan paneli StationListPanel'e ekle
        for (int i = 0; i < stationList.size(); i++) {
            String stationName = stationList.get(i);
            JPanel stationPanel = createStationPanel(stationName,i);
            stationListPanel.add(stationPanel);

            // Son durak değilse ---> işaretini ekle
            if (i < stationList.size() - 1) {
                stationListPanel.add(createArrowLabel());
            }
        }


        JScrollPane scrollPane = new JScrollPane(stationListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Yatay kaydırma çubuğunu devre dışı bırakma
        selectedBusPanel.add(scrollPane, BorderLayout.CENTER);

        // Alt kısım
        backButton = new JButton("Back to Buses Page");
        addToFavoriteButton = new JButton("Add to Favorite");

        // Kontrolü yap ve butonu ayarla
        if (isInFavorites(selectedBusName,email)) {
            addToFavoriteButton.setText("Remove from Favorites");
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // İki butonlu bir panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme
        buttonPanel.add(backButton);
        buttonPanel.add(addToFavoriteButton);

        selectedBusPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Forma ana paneli ekle
        add(selectedBusPanel);
        setLocationRelativeTo(null);
    }

    private int getRandomCurrentStationIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    private void buttonClickedListeners(String userEmail,String selectedBusName,String parentPage){

        addToFavoriteButton.addActionListener(e->{
            if (isInFavorites(selectedBusName,userEmail)) {
                removeFromFavorites(selectedBusName,userEmail);
                addToFavoriteButton.setText("Add to Favorite");
            } else {
                addToFavorites(selectedBusName,userEmail);
                addToFavoriteButton.setText("Remove from Favorites");
            }
        });


        backButton.addActionListener(e->{
            backToNavigation(userEmail,parentPage);
        });



    }


    private void backToNavigation(String email, String parentpage){

        if (parentpage.equals("AllBuses")){
            allBusses allBuses = new allBusses(email);
            allBuses.setVisible(true);
            selectedBuspage.this.dispose();
        }else{
            favoriteBuses favoritePage = new favoriteBuses(email);
            favoritePage.setVisible(true);
            selectedBuspage.this.dispose();
        }



    }
    private void addToFavorites(String selectedBusName,String userEmail) {
        sqLiteConnector.addFavoriteBus(userEmail,selectedBusName);
    }

    private void removeFromFavorites(String selectedBusName,String email) {
        sqLiteConnector.removeFavoriteBus(email,selectedBusName);
    }

    private boolean isInFavorites(String selectedBusName,String email) {
        List<String> favoriteBuses = sqLiteConnector.getFavoriteBusesByEmail(email); // Kullanıcı emailini ekleyin
        return favoriteBuses.contains(selectedBusName);
    }

    private JLabel createArrowLabel() {
        JLabel arrowLabel = new JLabel("--->");
        arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        arrowLabel.setVerticalAlignment(SwingConstants.CENTER);
        arrowLabel.setPreferredSize(new Dimension(30, 30)); // ---> işareti boyutunu ayarla
        return arrowLabel;
    }

    private JPanel createStationPanel(String stationName,Integer stationIndex) {
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new BorderLayout());

        // Örnek bir boyut (genişlik x yükseklik)
        int newWidth = 30;
        int newHeight = 30;



        // İstasyonun rengini belirle
        if (stationIndex == currentStationIndex) {
            stationPanel.setBackground(Color.GREEN);  // Yeşil renk
        }

        // Image'ı al
        Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/station.png");

        // Yeni boyutlu Image oluştur
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // ImageIcon oluştur
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Durak ismi ekle
        JLabel nameLabel = new JLabel(stationName, SwingConstants.CENTER);

        // StationPanel boyutunu sabitle
        stationPanel.setPreferredSize(new Dimension(80, 60));  // Sabit boyut

        // NameLabel boyutunu sabitle
        nameLabel.setPreferredSize(new Dimension(80, 30));  // Sabit boyut

        // Station icon ve durak ismini StationPanel'e ekle
        stationPanel.add(new JLabel(resizedIcon), BorderLayout.NORTH);
        stationPanel.add(nameLabel, BorderLayout.CENTER);

        // Border ve sağ kenar çizgisi ekle
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY),  // Sağ kenar çizgisi
                BorderFactory.createEmptyBorder(5, 5, 5, 5)  // Hücre içi boşluk
        );
        stationPanel.setBorder(border);

        return stationPanel;
    }
}

