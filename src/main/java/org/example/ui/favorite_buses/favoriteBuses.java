package org.example.ui.favorite_buses;

import org.example.database.sqLiteConnector;
import org.example.Models.BusModel;
import org.example.ui.home.homePage;
import org.example.ui.selected_bus.selectedBuspage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class favoriteBuses extends JFrame {

    private String selectedRoute;
    private JButton backButton;
    private JButton devamEtButton;
    private final String parentPage = "FavoriteBuses";

    public favoriteBuses(String userEmail){
        initializeUI();
        initializeBusList(userEmail);
        buttonClickedListeners(userEmail);
    }
    private void initializeUI() {
        setTitle("Favorite Buses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
    private void initializeBusList(String email) {
        DefaultListModel busListModel = new DefaultListModel();
        List<String> allBusesNames = sqLiteConnector.getFavoriteBusesByEmail(email);
        List<String> nonEmptyBuses = allBusesNames.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());

        for (String busName : nonEmptyBuses) {
            busListModel.addElement(busName);
        }

        if (busListModel.isEmpty()) {
            System.out.println("bus list boş");

            //Buradaki UI kısmını Tekrardan Düzelt!!
            JLabel emptyListLabel = new JLabel("EmptyFavoriteList");
            emptyListLabel.setFont(new Font(emptyListLabel.getFont().getName(), Font.BOLD, 16));
            emptyListLabel.setForeground(Color.RED);

            setLayout(new BorderLayout());
            add(emptyListLabel, BorderLayout.CENTER);
        } else {
            System.out.println("bus list dolu");
            // Liste doluysa
            JList<String> busList = new JList<>(busListModel);
            listSelectionListener(busList, email);

            // Yatay çizgi eklemek için özel hücre düzenleyici kullanma
            busList.setCellRenderer(new CustomListCellRenderer());

            JScrollPane scrollPane = new JScrollPane(busList);

            Font originalFont = busList.getFont();
            Font largerFont = new Font(originalFont.getName(), originalFont.getStyle(), originalFont.getSize() + 3);
            busList.setFont(largerFont);

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = createBackButton();
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    private static BufferedImage loadImage(InputStream path) {
        try {
            return ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static class CustomListCellRenderer implements ListCellRenderer<String> {
        private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Çizgi eklemek için alt boşluğu artırın
            renderer.setBorder(new EmptyBorder(10, 10, 5, 0));

            // Küçük bir resim eklemek için
            String iconPath = "assets/star.png"; // Bu yolu, projenizin kaynaklarına göre güncelleyin
            InputStream iconStream = getClass().getClassLoader().getResourceAsStream(iconPath);

            if (iconStream != null) {
                BufferedImage img = loadImage(iconStream);
                renderer.setIcon(new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            }

            return renderer;
        }
    }
    private void listSelectionListener(JList<String > busList,String userEmail) {
        busList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedBus = busList.getSelectedValue();
                    BusModel busModel = getSelectedBusRoute(selectedBus);
                    //selectedBusNavigation(selectedBus);
                    selectRouteDiaglog(busModel,userEmail);
                }
            }
        });
    }
    private void selectedBusNavigation(String selectedBus,String email,String selectedRoute) {
        selectedBuspage selectedBusPage = new selectedBuspage(selectedBus,"12",email,parentPage,selectedRoute);
        selectedBusPage.setVisible(true);
        favoriteBuses.this.dispose();
    }
    private void selectRouteDiaglog(BusModel selectedBusModel,String email){
        JFrame frame = new JFrame("Select a route");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        initalizeRadioButtons(frame,selectedBusModel,email);

    }
    private JButton createBackButton() {
        backButton = new JButton();

        // Geri gitme ikonunu projenize ekleyin (örneğin, src/main/resources klasörüne ekleyebilirsiniz)
        URL iconURL = getClass().getResource("src/main/resources/assets/station.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            backButton.setIcon(icon);
        } else {
            backButton.setText("<--"); // Eğer ikon yüklenemezse metin göster
        }



        return backButton;
    }
    private void buttonClickedListeners(String userEmail){
        backButton.addActionListener(e ->{
            backButtonNavigation(userEmail);
        });
    }
    private void backButtonNavigation(String userEmail){
        homePage homePage = new homePage(userEmail);
        homePage.setVisible(true);
        favoriteBuses.this.dispose();
    }
    private void initalizeRadioButtons(JFrame frame,BusModel selectedBusModel,String userEmail){


        String firstStation = selectedBusModel.getFirstStation();
        String lastStation = selectedBusModel.getLastStation();

        JRadioButton rota1Button = new JRadioButton(firstStation + "-" + lastStation);
        JRadioButton rota2Button = new JRadioButton(lastStation + "-" + firstStation);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rota1Button);
        buttonGroup.add(rota2Button);

        devamEtButton = getButton(rota1Button, rota2Button, frame,selectedBusModel,userEmail);


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
    private JButton getButton(JRadioButton rota1Button, JRadioButton rota2Button, JFrame frame,BusModel selectedBusModel, String userEmail) {
        // Eğer devamEtButton daha önce oluşturulmadıysa oluştur
        if (devamEtButton == null) {
            devamEtButton = new JButton("Next");
        }
        devamEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rota1Button.isSelected()) {
                    selectedRoute = "First Route";
                } else if (rota2Button.isSelected()) {
                    selectedRoute = "Second Route";
                } else {
                    selectedRoute = null;
                }

                if (selectedRoute != null) {
                    System.out.println("Selected Route: " + selectedRoute);
                    String selectedBusModelBusName = selectedBusModel.getBusName();
                    selectedBusNavigation(selectedBusModelBusName, userEmail, selectedRoute);
                    frame.dispose();

                } else {
                    JOptionPane.showMessageDialog(frame, "Please choose a route.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return devamEtButton;
    }
}

