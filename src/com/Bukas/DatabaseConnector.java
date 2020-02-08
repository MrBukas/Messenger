package com.Bukas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {

    static final private String userName = "root";
    static final private String password = "1234";
    static final private String connectionUrl = "jdbc:mysql://localhost:3306/messenger?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&zeroDateTimeBehavior=CONVERT_TO_NULL";
    static Connection connection = connect();

    static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Connected");
        try {
            return DriverManager.getConnection(connectionUrl,userName,password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }
    }
    static boolean checkIfUsernameNotTaken(String username){
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select COUNT(username) from users where username = \'" + username + "\'");
            resultSet.first();
            int count = resultSet.getInt(1);
            return count == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;//Не уверен что это обрабатывается так
        }
    }
    static boolean sendRegisterRequest(String username, String password){
        try {
            connection.createStatement().executeUpdate("INSERT INTO users(username,password)"+" VALUES (\""+username+"\",\""+password+"\")");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    static boolean attemptLogin(String username,String password){
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery("select COUNT(*) from users where username = \'" + username + "\' and password = \'" + password + "\'");
            resultSet.first();
            if (resultSet.getInt(1) == 1){
                return true;
            }else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
