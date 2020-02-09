package com.Bukas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    static Connection connection = Main.connection;
    public static boolean attemptLogin(String username,String password){
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery("select COUNT(*) from users where username = \'" + username + "\' COLLATE utf8mb4_0900_as_cs and password = \'" + password + "\' COLLATE utf8mb4_0900_as_cs");
            resultSet.first();
            if (resultSet.getInt(1) == 1){
                return true;
            }else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

}
