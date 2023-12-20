package org.example.database;

import org.example.Models.userModel;

import java.sql.*;

public class sqLiteConnector {

    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\imtekmuhendislik\\Downloads\\sqlite-tools-win-x64-3440200\\deneme.db";

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
            System.out.println("SQLite bağlantısı başarıyla oluşturuldu.");
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
    public static void getAllUsers (Connection connection) {
        try {
            String sql = "SELECT * FROM Users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        System.out.println( "Email: " + email + "Password : "+ password);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public static boolean createUserSqlite(String email,String password){
        String query = "INSERT INTO Users (email, password) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcı oluşturulurken bir hata oluştu: " + e.getMessage(), e);
        }

    }
    public static userModel currentProfileModel(){
        String email = "berke";
        userModel currentUser = null;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?")) {

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("try içerisinde");
                    // Verileri çek
                    String email2 = resultSet.getString("email");

                    String password = resultSet.getString("password");

                    // UserModel oluştur
                     currentUser = new userModel(email, password);

                    System.out.println("current user = " + currentUser);

                } else {
                    System.out.println("Kullanıcı bulunamadı.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Hata durumunda uygun bir şekilde işleyin
        }

        return currentUser;
    }

}
