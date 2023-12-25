package org.example.database;

import org.example.Models.userModel;
import org.example.Models.BusModel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class sqLiteConnector {

    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\aatak\\Desktop\\sqlite\\ridewave.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("SQLite bağlantısı oluşturulurken hata oluştu: " + e.getMessage());
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("SQLite bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            System.err.println("SQLite bağlantısı kapatılırken hata oluştu: " + e.getMessage());
        }
    }
    //check the return
    public static String getUserByName(String email) {
        String password;
        String email2;
        try {
            String sql = "SELECT * FROM Users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        email2 = resultSet.getString("email");
                        password = resultSet.getString("password");
                        System.out.println("Email: " + email + ", Paasword: " + password);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "abc";



    }
    public static List<String> getAllUsers () {

        List<String> allUsersNames = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Users";
            try (PreparedStatement statement = connect().prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String email = resultSet.getString("email");
                        allUsersNames.add(email);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsersNames;
    }
    public static boolean authentication(String email, String password){
        try (Connection connection = connect()) {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createUserSqlite(String email,String password,String cardNumber){
        String query = "INSERT INTO Users (email, password, cardNumber, balance) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, cardNumber);
            preparedStatement.setString(4, String.valueOf((int) (Math.random() * 100) + 1));
            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcı oluşturulurken bir hata oluştu: " + e.getMessage(), e);
        }

    }
    public static userModel currentProfileModel(String userEmail){
        //Burada dışarıdan bir email verilmeli

        userModel currentUser = null;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?")) {

            preparedStatement.setString(1, userEmail);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("try içerisinde");
                    String email2 = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String cardNumber = resultSet.getString("cardNumber");
                    String balance = resultSet.getString("balance");

                     currentUser = new userModel(userEmail, password,cardNumber,balance);
                    System.out.println("current user = " + currentUser);
                } else {
                    System.out.println("Kullanıcı bulunamadı.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentUser;
    }
    public static List<String> getAllBusNames() {
        List<String> busNames = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT busName FROM AllBuses";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    busNames.add(resultSet.getString("busName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busNames;
    }
    public static BusModel retrieveBusDataFromDatabase(String busName) {
        BusModel busModel = null;

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM AllBuses WHERE busName LIKE ?")) {
            preparedStatement.setString(1, "%" + busName + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the result set and get the first bus data
                if (resultSet.next()) {
                    busModel = new BusModel();
                    busModel.setBusName(resultSet.getString("busName"));
                    busModel.setFirstStation(resultSet.getString("firstStation"));
                    busModel.setLastStation(resultSet.getString("lastStation"));
                    busModel.setCurrentStation(resultSet.getString("currentStation"));
                    busModel.setBusCrowd(resultSet.getInt("busCrowd"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return busModel;
    }
    public static ArrayList<String> getStationsByBusname(String busCode){

        ArrayList<String> stationsList = new ArrayList<>();
        try (Connection connection = connect()) {
            String query = "SELECT busName, stations FROM Stations WHERE busName = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, busCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String busName = resultSet.getString("busName");
                        String stationsString = resultSet.getString("stations");

                        String[] stations = stationsString.split(", ");
                        stationsList.addAll(Arrays.asList(stations));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stationsList;
    }
    public static boolean addUserToFavoriteBuses(String userEmail){

        try (Connection connection = connect()) {
            String query = "INSERT INTO FavoriteBuses (userEmail, favoriteBuses) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userEmail);
                preparedStatement.setString(2, "");
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static void addFavoriteBus(String email, String newFavoriteBus) {
        try (Connection connection = connect()) {
            String selectQuery = "SELECT favoriteBuses FROM FavoriteBuses WHERE userEmail = ?";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, email);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String currentFavoriteBuses = resultSet.getString("favoriteBuses");

                        List<String> currentBusesList = new ArrayList<>(splitFavoriteBuses(currentFavoriteBuses));

                        if (!currentBusesList.contains(newFavoriteBus)) {
                            currentBusesList.add(newFavoriteBus);

                            String updatedFavoriteBuses = String.join(", ", currentBusesList);
                            System.out.println("UPDATED FAVORITE LIST = " + updatedFavoriteBuses);

                            String updateQuery = "UPDATE FavoriteBuses SET favoriteBuses = ? WHERE userEmail = ?";

                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, updatedFavoriteBuses);
                                updateStatement.setString(2, email);

                                updateStatement.executeUpdate();

                                System.out.println("Favorite Bus updated successfully.");
                            }
                        } else {
                            System.out.println("Error: The bus already exists in favorites.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeFavoriteBus(String email, String favoriteBusToRemove) {
        try (Connection connection = connect()) {
            String selectQuery = "SELECT favoriteBuses FROM FavoriteBuses WHERE userEmail = ?";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, email);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String currentFavoriteBuses = resultSet.getString("favoriteBuses");

                        List<String> currentBusesList = new ArrayList<>(splitFavoriteBuses(currentFavoriteBuses));

                        if (currentBusesList.contains(favoriteBusToRemove)) {
                            currentBusesList.remove(favoriteBusToRemove);

                            String updatedFavoriteBuses = String.join(", ", currentBusesList);
                            System.out.println("UPDATED FAVORITE LIST = " + updatedFavoriteBuses);

                            String updateQuery = "UPDATE FavoriteBuses SET favoriteBuses = ? WHERE userEmail = ?";

                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, updatedFavoriteBuses);
                                updateStatement.setString(2, email);

                                updateStatement.executeUpdate();

                                System.out.println("Favorite Bus removed successfully.");
                            }
                        } else {
                            System.out.println("Error: The specified bus does not exist in favorites.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static ArrayList<String> splitFavoriteBuses(String favoriteBuses) {
        if (favoriteBuses != null && !favoriteBuses.isEmpty()) {
            return new ArrayList<>(Arrays.asList(favoriteBuses.split(",\\s*")));
        }
        return new ArrayList<>();
    }
    public static List<String> getFavoriteBusesByEmail(String userEmail){
        List<String> favoriteBusesList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String query = "SELECT favoriteBuses FROM FavoriteBuses WHERE userEmail = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userEmail);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // favoriteBuses sütununu al ve virgül ile bölecek şekilde listeye ekle
                        String favoriteBusesString = resultSet.getString("favoriteBuses");
                        String[] favoriteBusesArray = favoriteBusesString.split(", ");
                        favoriteBusesList.addAll(Arrays.asList(favoriteBusesArray));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteBusesList;

    }
}

