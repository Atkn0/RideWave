package org.example.ui.selected_bus;

import org.example.database.sqLiteConnector;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class selectedBuspage extends JFrame {

    private JPanel selectedBusPanel;


    public selectedBuspage(String selectedBusName, String selectedBusCode) {
        initializeUI(selectedBusName);
    }

    private void initializeUI(String selectedBusName) {
        setTitle("Selected Bus Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel oluştur
        selectedBusPanel = new JPanel();
        selectedBusPanel.setLayout(new BorderLayout());

        // Üst kısım
        JLabel busInfoLabel = new JLabel("M4 -- BusCrowd : %40", SwingConstants.CENTER);
        busInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme
        selectedBusPanel.add(busInfoLabel, BorderLayout.NORTH);


        ArrayList<String> stationList = sqLiteConnector.getStationsByBusname(selectedBusName);
        // Orta kısım
        // Örnek veri
        String[] stationData = {"Station 1", "Station 2", "Station 3", "Station 4", "Station 5",
                "Station 6", "Station 7", "Station 8", "Station 9", "Station 10"};

        // StationListPanel oluştur
        JPanel stationListPanel = new JPanel();
        stationListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Boşluk ekleme
        stationListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme


        // Station icon ve isimleri ekleyerek oluşturulan paneli StationListPanel'e ekle
        for (int i = 0; i < stationList.size(); i++) {
            String stationName = stationList.get(i);
            JPanel stationPanel = createStationPanel(stationName);
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
        JButton backButton = new JButton("Back to Home Page");
        JButton addToFavoriteButton = new JButton("Add to Favorite");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // İki butonlu bir panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Boşluk ekleme
        buttonPanel.add(backButton);
        buttonPanel.add(addToFavoriteButton);

        selectedBusPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Forma ana paneli ekle
        add(selectedBusPanel);
        setLocationRelativeTo(null);
    }

    private JLabel createArrowLabel() {
        JLabel arrowLabel = new JLabel("--->");
        arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        arrowLabel.setVerticalAlignment(SwingConstants.CENTER);
        arrowLabel.setPreferredSize(new Dimension(30, 30)); // ---> işareti boyutunu ayarla
        return arrowLabel;
    }

    private JPanel createStationPanel(String stationName) {
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new BorderLayout());

        // Örnek bir boyut (genişlik x yükseklik)
        int newWidth = 30;
        int newHeight = 30;

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

    /*
    private void initializeComponents(String selectedBusName, String selectedBusCode) {
        stationsPanelContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // boşluklar

        // Otobüs bilgilerini gösteren paneli initialize et
        JPanel busInfoPanel = initializeBusInfo(selectedBusName);
        gbc.gridy++;
        stationsPanelContainer.add(busInfoPanel, gbc);

        gbc.gridy++;
        populateStationListPanel();

        // ScrollPane içindeki paneli yatay olarak yerleştirme
        JPanel horizontalStationListPanel = new JPanel();
        horizontalStationListPanel.setLayout(new BoxLayout(horizontalStationListPanel, BoxLayout.X_AXIS));
        horizontalStationListPanel.add(stationListTextArea);
        JScrollPane scrollPane = new JScrollPane(horizontalStationListPanel);

        stationsPanelContainer.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTH;
        addFavoriteButton = new JButton("Add to Favorite Button");
        stationsPanelContainer.add(addFavoriteButton, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(stationsPanelContainer, BorderLayout.CENTER);

        addFavoriteButton.setPreferredSize(new Dimension(150, 30));
    }

    private JPanel initializeBusInfo(String selectedBusName) {
        JPanel busInfoPanel = new JPanel();
        busInfoPanel.setLayout(new GridLayout(1, 0));

        JLabel busNameLabel = new JLabel("Bus Name: " + selectedBusName);
        busInfoPanel.add(busNameLabel);

        // Diğer otobüs bilgilerini ekleyebilirsiniz.

        return busInfoPanel;
    }

    private void buttonClickedListener(String selectedBusName) {
        addFavoriteButton.addActionListener(e -> {
            System.out.println("selectedBusCode = " + selectedBusName);
            // Diğer işlemleri ekleyebilirsiniz.
        });
    }

    private void populateStationListPanel() {
        stationListTextArea = new JTextPane();
        stationListTextArea.setEditable(false);
        stationListTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    }

    private void populateStationList(String selectedBusName) {
        ArrayList<String> stations = getStations(selectedBusName);

        if (!stations.isEmpty()) {
            int currentStationIndex = 2;

            for (int i = 0; i < stations.size(); i++) {
                String station = stations.get(i);

                JPanel stationPanel = createStationPanel(station, i == currentStationIndex);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = i;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(0, 10, 0, 0); // Duraklar arası boşluklar
                stationsPanelContainer.add(stationPanel, gbc);

                // Son durak değilse --> işaretini ekle
                if (i < stations.size() - 1) {
                    JLabel arrowLabel = new JLabel(" --> ");
                    GridBagConstraints arrowGbc = new GridBagConstraints();
                    arrowGbc.gridx = 1;
                    arrowGbc.gridy = i;
                    stationsPanelContainer.add(arrowLabel, arrowGbc);
                }
            }
        } else {
            JLabel stationNotFound = new JLabel("Durak bulunamadı.");
            stationsPanelContainer.add(stationNotFound);
        }
    }

    private JPanel createStationPanel(String station, boolean isCurrentStation) {
        JPanel stationPanel = new JPanel();
        stationPanel.setPreferredSize(new Dimension(150, 60));
        stationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        stationPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());

        ImageIcon stationIcon = new ImageIcon("src/main/resources/assets/station.png");
        Image scaledStationIcon = stationIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        stationIcon = new ImageIcon(scaledStationIcon);

        JLabel iconLabel = new JLabel(stationIcon);
        contentPanel.add(iconLabel, BorderLayout.NORTH);

        JLabel textLabel = new JLabel(station, SwingConstants.CENTER);
        contentPanel.add(textLabel, BorderLayout.CENTER);

        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        stationPanel.add(contentPanel, BorderLayout.CENTER);

        if (isCurrentStation) {
            stationPanel.setBackground(Color.GREEN);

            ImageIcon currentBusIcon = new ImageIcon("dosya_yolu/current_bus_icon.png");
            Image scaledCurrentBusIcon = currentBusIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            currentBusIcon = new ImageIcon(scaledCurrentBusIcon);

            JLabel currentBusLabel = new JLabel(currentBusIcon, SwingConstants.CENTER);
            stationPanel.add(currentBusLabel, BorderLayout.WEST);
        }

        return stationPanel;
    }

    private ArrayList<String> getStations(String selectedBusName) {
        // Sizin durakları almanız gerekiyor
        ArrayList<String> stations = new ArrayList<>();
        stations.add("Durak1");
        stations.add("Durak2");
        stations.add("Durak3");
        stations.add("Durak4");
        stations.add("Durak5");
        stations.add("Durak6");
        stations.add("Durak7");
        stations.add("Durak8");
        return stations;
    }

     */








    }

