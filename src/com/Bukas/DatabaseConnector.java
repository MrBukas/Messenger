package com.Bukas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {

    static final private String username = "root";
    static final private String password = "1234";
    static final private String connectionUrl = "jdbc:mysql://localhost:3306/messenger?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true";
    static Connection connection = connect();

    static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            //e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkIfUsernameNotTaken(String username){
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
    public static int getUserId(String username){
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select user_id from users where username = \'" + username + "\' COLLATE utf8mb4_0900_as_cs");
            resultSet.first();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String getUsernameById(int id){
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select username from users where user_id = \'" + id + "\'");
            resultSet.first();
            return resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public static int registerUser(String username, String password) {
        //Коды:
        //-1 - Неизвестная ошибка
        //0 - Успешная регистрация
        //1 - Имя пользователя уже существует
        if (DatabaseConnector.checkIfUsernameNotTaken(username)) {
            if (Login.sendRegisterRequest(username, password)) {
                return 0;
            }
        } else {
            return 1;
        }
        return -1;
    }



}
