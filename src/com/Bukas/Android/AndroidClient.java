package com.Bukas.Android;

import com.Bukas.DatabaseConnector;
import com.Bukas.Login;
import com.Bukas.Main;
import com.Bukas.User;

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
        auth(user);
        System.out.println(user.getUsername() + " login (Android)");
        while (true){
            String command = user.read();
            switch (command){
                case "talkedUsers" : getTalkedUsers(user); break;
                case "dialog" : getDialog(user,user.read());
                default:
                    System.out.println(command);
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
            auth(user);
        }
        user.setUsername(username);
        user.write("0");

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
            ResultSet resultSet = Main.connection.createStatement().executeQuery("SELECT message FROM messages where receiver_id = " + user.getId() +" UNION SELECT message FROM messages where sender_id = " + talkerId);
            resultSet.first();
            do {
                user.write(String.valueOf(resultSet.getString(1)));
            }while (resultSet.next());
            user.write("endl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
