package com.Bukas;

public class Login {
    public static boolean attemptLogin(String username, String password){
        return DatabaseConnector.attemptLogin(username,password);
    }
}
