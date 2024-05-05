package com.example.login_page;

import java.sql.*;

public  class DatabaseManager {
    static String url = "jdbc:mysql://localhost:3306/planner";
    static String user = "hsh";
    static String password = "password";

    static Connection connection;

    static void createDBConnection (){
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
        }
    }

    static boolean checkLoginData (String login, String password){
        try {
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            System.out.println("Error while connecting to database");
            return false;
        }
    }

}
