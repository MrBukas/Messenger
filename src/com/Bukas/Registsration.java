package com.Bukas;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registsration {
    public static String hashPassword(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Change this to UTF-16 if needed
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }
    public static int registerUser(String username, String password){
        //Коды:
        //-1 - Неизвестная ошибка
        //0 - Успешная регистрация
        //1 - Имя пользователя уже существует
        if (DatabaseConnector.checkIfUsernameNotTaken(username)){
            if (Login.sendRegisterRequest(username,password)){
                return 0;
            }
        }else {
            return 1;
        }
        return -1;
    }
}
