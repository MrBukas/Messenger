package com.Bukas.Android;

import com.Bukas.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AndroidClient implements Runnable{
    static User user;
    public AndroidClient(User user){
        this.user = user;
    }

    @Override
    public void run() {
        String loginOrReg = user.read();
        System.out.println(loginOrReg);

        switch (loginOrReg){
            case "login" : auth(user); break;
            case "reg" : reg(user); break;
        }
        System.out.println(user.getUsername() + " login (Android)");
        while (true){
            String command = user.read();
            System.out.println(command);
            switch (command){
                case "talkedUsers" : getTalkedUsers(user); break;
                case "dialog" : getDialog(user,user.read()); break;
                case "message" : sendMessage(user,user.read(),user.read()); break;
                default:
                    System.out.println(command + " no command");
            }
        }
    }
    void auth(User user){
        String username;
        String password;
        username = user.read();
        password = user.read();
        if (!Login.attemptLogin(username,password)){
            user.write("1");
            System.out.println(username + " failed login (Android)");
            user.read();
            auth(user);
        }
        user.setUsername(username);
        user.write("0");

    }
    void auth(String username, String password){
        if (!Login.attemptLogin(username,password)){
            user.write("1");
            System.out.println(username + " failed login (Android)");
            auth(user);
        }
        user.setUsername(username);
        user.write("0");

    }
    void reg(User user){
        String username;
        String password;
        username = user.read();
        password = user.read();
        if (DatabaseConnector.checkIfUsernameNotTaken(username)) {
            if (Login.sendRegisterRequest(username, password)) {
                user.write("0");//Регистрация успешна
                auth(user);
            }
        } else {
            user.write("1");//Имя занято
        }
        user.write("-1");// ошибка
        //user.setUsername(username);
        //user.write("0");

    }
    void getTalkedUsers(User user){
        List<Integer> IDs = new ArrayList<>();
        try {
            ResultSet resultSet = Main.connection.createStatement().executeQuery("SELECT sender_id FROM messages where receiver_id = " + user.getId() +" UNION SELECT receiver_id FROM messages where sender_id = " + user.getId());
            resultSet.first();
            do {
                IDs.add(resultSet.getInt(1));
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> usernames = new ArrayList<>();
        for (int id: IDs) {
            usernames.add(DatabaseConnector.getUsernameById(id));
        }
        int size = IDs.size();
        user.write(String.valueOf(size));
        for (int i = 0; i < size; i++) {
            user.write(String.valueOf(IDs.get(i)));
            user.write(usernames.get(i));
        }
        System.out.println(size);
    }
    void getDialog(User user, String talker){
        int talkerId = DatabaseConnector.getUserId(talker);
        try {
            ResultSet resultSet = Main.connection.createStatement().executeQuery("SELECT message,sender_id,created_on FROM messages where (receiver_id = " + user.getId() + " and sender_id = " + talkerId + ") or (receiver_id =  " + talkerId + " and sender_id = "+user.getId() +")");

            user.write(String.valueOf(user.getId()));
            if (resultSet.first()){

            do {
               // System.out.println("line");
                user.write(String.valueOf(resultSet.getString("message")));
                user.write(String.valueOf(resultSet.getInt("sender_id")));
            }while (resultSet.next());
            }
            user.write("endl");
            System.out.println("endl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void sendMessage(User user, String receiver, String message){
        System.out.println("message Android");
        MessageProcessor.sendMessage(user.getId(),receiver,message);
    }
}
