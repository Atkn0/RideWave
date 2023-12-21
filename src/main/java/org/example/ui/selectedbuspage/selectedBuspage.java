package org.example.ui.selectedbuspage;

import org.example.database.sqLiteConnector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class selectedBuspage extends JFrame {
    private String selectedBusCode;
    private Box duraklarBox;
    private JTextPane stationListTextArea;
    JPanel stationsPanelContainer;
    private String selectedRoute;

    public selectedBuspage(String selectedBusName) {
        initializeUI();
        initializeComponents();
        populateStationList(selectedBusName);
    }
    private void initializeUI() {
        setTitle("Selected Bus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        JLabel busCodeLabel = new JLabel("Selected Bus Code: " + selectedBusCode);
        JLabel routeLabel = new JLabel("Selected Route: " + selectedRoute);

        setLayout(new GridLayout(2, 1));
        add(busCodeLabel);
        add(routeLabel);
    }
    private void initializeComponents() {
        stationsPanelContainer = new JPanel(new GridLayout(1, 0)); // 1 satır, 0 sütunlu GridLayout kullanarak yatayda sırala
        JScrollPane scrollPane = new JScrollPane(stationsPanelContainer);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

    }
    private void populateStationList(String selectedBusName) {
        ArrayList<String> stations = getStations(selectedBusName); // Sizin durak listesini almanız gerekiyor

        if (!stations.isEmpty()) {
            int currentStationIndex = 2; // Örneğin, 2. durakı current durak olarak kabul ediyoruz.

            for (int i = 0; i < stations.size(); i++) {
                String station = stations.get(i);

                JPanel durakPanel = createStationPanel(station, i == currentStationIndex);
                stationsPanelContainer.add(durakPanel);

                // Son durak değilse --> işaretini ekle
                if (i < stations.size() - 1) {
                    JLabel arrowLabel = new JLabel(" --> ");
                    stationsPanelContainer.add(arrowLabel);
                }
            }
        } else {
            JLabel stationNotFound = new JLabel("Durak bulunamadı.");
            stationsPanelContainer.add(stationNotFound);
        }
    }
    private JPanel createStationPanel(String station, boolean isCurrentStation) {
        JPanel stationPanel = getStationPanel(station);
        stationCurrentControl(isCurrentStation, stationPanel);
        return stationPanel;
    }
    private static void stationCurrentControl(boolean isCurrentStation, JPanel stationPanel) {
        // Eğer current duraksa yeşil arkaplan ve currentBus icon
        if (isCurrentStation) {
            stationPanel.setBackground(Color.GREEN);

            ImageIcon currentBusIcon = new ImageIcon("dosya_yolu/current_bus_icon.png"); // CurrentBus iconunuzun dosya yolunu belirtin
            Image scaledCurrentBusIcon = currentBusIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            currentBusIcon = new ImageIcon(scaledCurrentBusIcon);

            JLabel currentBusLabel = new JLabel(currentBusIcon, SwingConstants.CENTER);
            stationPanel.add(currentBusLabel, BorderLayout.WEST);
        }
    }
    private static JPanel getStationPanel(String station) {
        JPanel stationPanel = new JPanel();
        stationPanel.setPreferredSize(new Dimension(60,60)); // Sabit genişlik ve yükseklik ayarla
        stationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Dış çerçeve
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // İç boşluk (margin)
        ));
        stationPanel.setLayout(new BorderLayout());

        // Durak icon ve text container
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Durak icon
        ImageIcon stationIcon = new ImageIcon("src/main/resources/assets/station.png"); // Durak iconunuzun dosya yolunu belirtin
        Image scaledStationIcon = stationIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        stationIcon = new ImageIcon(scaledStationIcon);

        JLabel iconLabel = new JLabel(stationIcon);
        contentPanel.add(iconLabel, BorderLayout.NORTH);

        // Durak text
        JLabel textLabel = new JLabel(station, SwingConstants.CENTER);
        contentPanel.add(textLabel, BorderLayout.CENTER);

        // Margin eklemek için içteki paneli ayarla
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        stationPanel.add(contentPanel, BorderLayout.CENTER);
        return stationPanel;
    }
    private ArrayList<String> getStations(String selectedBusName) {
        return sqLiteConnector.getStationsByBusname(selectedBusName);
    }




    // ---- çizgili text durak varyasyonu ----

    /*

    private void initializeComponents() {
        stationListTextArea = new JTextPane();
        stationListTextArea.setEditable(false);
        stationListTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(stationListTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setTitle("Bus Stations");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void populateDurakList() {
        ArrayList<String> stations = getDuraklar();

        if (!stations.isEmpty()) {
            int currentStationIndex = 3;

            StyledDocument doc = stationListTextArea.getStyledDocument();

            for (int i = 0; i < stations.size(); i++) {
                String durak = stations.get(i);

                SimpleAttributeSet attributes = new SimpleAttributeSet();
                StyleConstants.setForeground(attributes, Color.BLACK);

                if (i == currentStationIndex) {
                    StyleConstants.setForeground(attributes, Color.RED);
                    StyleConstants.setUnderline(attributes, true);
                }

                try {
                    doc.insertString(doc.getLength(), durak, attributes);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

                if (i < stations.size() - 1) {
                    attributes = new SimpleAttributeSet();
                    try {
                        doc.insertString(doc.getLength(), " --> ", attributes);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            stationListTextArea.setText("Station couldn't found");
        }
    }
    private ArrayList<String> getDuraklar() {
        ArrayList<String> station = new ArrayList<>();
        station.add("Durak1");
        station.add("Durak2");
        station.add("Durak3");
        station.add("Durak4");
        station.add("Durak5");
        station.add("Durak6");
        station.add("Durak7");
        station.add("Durak8");
        station.add("Durak9");
        station.add("Durak10");



        return station;
    }

    /*
    private void appendStyledText(String text, Color color, boolean underline, JTextPane durakListTextPane) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, color);
        StyleConstants.setUnderline(attributeSet, underline);

        int length = durakListTextPane.getDocument().getLength();
        durakListTextPane.setCaretPosition(length);
        durakListTextPane.setCharacterAttributes(attributeSet, false);
        durakListTextPane.replaceSelection(text);
    }

     */




    // ---- durakpanel container durak varyasyonu ----

    /*
    private void initializeComponents() {
        durakListTextArea = new JPanel();
        durakListTextArea.setLayout(new BoxLayout(durakListTextArea, BoxLayout.X_AXIS)); // X ekseni boyunca sırala
        JScrollPane scrollPane = new JScrollPane(durakListTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setTitle("Otobüs Durakları");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /*
    private void populateDurakList() {
        ArrayList<String> duraklar = getDuraklar(); // Sizin durak listesini almanız gerekiyor

        if (duraklar != null && !duraklar.isEmpty()) {
            int currentDurakIndex = 0; // Şu anki durak index'i, örneğin 0 olarak kabul edildi.

            for (int i = 0; i < duraklar.size(); i++) {
                String durak = duraklar.get(i);

                JPanel durakPanel = createDurakPanel(durak, i == currentDurakIndex);
                durakListTextArea.add(durakPanel);

                // Son durak değilse --> işaretini ekle
                if (i < duraklar.size() - 1) {
                    JLabel arrowLabel = new JLabel(" --> ");
                    durakListTextArea.add(arrowLabel);
                }
            }
        } else {
            JLabel durakBulunamadiLabel = new JLabel("Durak bulunamadı.");
            durakListTextArea.add(durakBulunamadiLabel);
        }
    }

    private JPanel createDurakPanel(String durak, boolean isCurrentDurak) {
        JPanel durakPanel = new JPanel();
        durakPanel.setPreferredSize(new Dimension(60, 20)); // Sabit genişlik ve yükseklik ayarla
        durakPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK), // Dış çerçeve
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // İç boşluk (margin)
        ));
        durakPanel.setLayout(new BorderLayout());

        JLabel durakLabel = new JLabel(durak);
        if (isCurrentDurak) {
            durakLabel.setForeground(Color.RED);
            durakLabel.setFont(durakLabel.getFont().deriveFont(Font.BOLD));
        }

        durakPanel.add(durakLabel, BorderLayout.CENTER);

        return durakPanel;
    }



    private ArrayList<String> getDuraklar() {
        // Sizin durakları almanız gerekiyor
        ArrayList<String> duraklar = new ArrayList<>();
        duraklar.add("Durak1");
        duraklar.add("Durak2");
        duraklar.add("Durak3");
        duraklar.add("Durak4");
        duraklar.add("Durak5");
        duraklar.add("Durak6");
        duraklar.add("Durak7");
        duraklar.add("Durak8");


        return duraklar;
    }

     */




    // --- Bu uygun gibi ---

    /*
    private void initializeComponents() {
        durakListTextArea = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Durakları sola hizala
        JScrollPane scrollPane = new JScrollPane(durakListTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setTitle("Otobüs Durakları");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void populateDurakList() {
        ArrayList<String> duraklar = getDuraklar(); // Sizin durak listesini almanız gerekiyor

        if (!duraklar.isEmpty()) {
            int currentDurakIndex = 4; // Şu anki durak index'i, örneğin 0 olarak kabul edildi.

            for (int i = 0; i < duraklar.size(); i++) {
                String durak = duraklar.get(i);

                JPanel durakPanel = createDurakPanel(durak, i == currentDurakIndex);
                durakListTextArea.add(durakPanel);

                // Son durak değilse --> işaretini ekle
                if (i < duraklar.size() - 1) {
                    JLabel arrowLabel = new JLabel(" --> ");
                    durakListTextArea.add(arrowLabel);
                }
            }
        } else {
            JLabel durakBulunamadiLabel = new JLabel("Durak bulunamadı.");
            durakListTextArea.add(durakBulunamadiLabel);
        }
    }

    private JPanel createDurakPanel(String durak, boolean isCurrentDurak) {
        JPanel durakPanel = new JPanel();
        durakPanel.setPreferredSize(new Dimension(80, 80)); // Sabit genişlik ve yükseklik ayarla
        durakPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK), // Dış çerçeve
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // İç boşluk (margin)
        ));
        durakPanel.setLayout(new BorderLayout());

        JLabel durakLabel = new JLabel(durak);
        if (isCurrentDurak) {
            durakLabel.setForeground(Color.RED);
            durakLabel.setFont(durakLabel.getFont().deriveFont(Font.BOLD));
        }

        durakPanel.add(durakLabel, BorderLayout.CENTER);

        return durakPanel;
    }
    private ArrayList<String> getDuraklar() {
        // Sizin durakları almanız gerekiyor
        ArrayList<String> duraklar = new ArrayList<>();
        duraklar.add("Durak1");
        duraklar.add("Durak2");
        duraklar.add("Durak3");
        duraklar.add("Durak4");
        duraklar.add("Durak5");
        duraklar.add("Durak6");
        duraklar.add("Durak7");
        duraklar.add("Durak8");


        return duraklar;
    }

     */

}
