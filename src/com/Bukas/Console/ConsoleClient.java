package com.Bukas.Console;

import com.Bukas.Login;
import com.Bukas.Registsration;
import com.Bukas.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient implements Runnable{
    User user;
    public ConsoleClient(User user){
        this.user = user;
    }
    @Override
    public void run() {
        try{
            PrintWriter printWriter = new PrintWriter(user.getSocket().getOutputStream(),true);

            System.out.println("New connection : " + user.getSocket());

            user.write("Select 'login' or 'register'");

            String command = user.read();
            while (!command.toLowerCase().equals("login")&&!command.toLowerCase().equals("register")){
                printWriter.println("Wrong command. Try again");
                command = user.read();
            }
            getUserLogin(command,user);

            do {
                user.write("Enter command 'message', 'inbox' or 'exit': ");
                command = user.read();
                switch (command){
                    case "message" : ConsoleMessage.processMessage(user);break;
                    case "inbox" : ConsoleInbox.showInboxMessages(user);
                }
            }while (!command.equals("exit"));
            user.closeSocket();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getUserLogin(String option,User user){
        String username;
        String password;
        if(option.equals("register")){
            user.write("Enter username: ");
            username = user.read();
            user.write("Enter password: ");
            password = Registsration.hashPassword(user.read());
            int res = Registsration.registerUser(username,password);
            if (res == 0){
                user.write("Registered successfully");
            }else if (res == 1){
                user.write("Selected username already exists. Try another one");
                getUserLogin(option,user);
            }else {
                user.write("Error " + res);
            }
        }
        //Logging in
        user.write("Enter username: ");
        username = user.read();
        user.write("Enter password: ");
        password = Registsration.hashPassword(user.read());
        if (Login.attemptLogin(username,password)){
            user.setUsername(username);
            user.write("You're logged in!");
            System.out.println(user.getUsername() + " logged in");
        }else {
            user.write("Failed to login. Try again");
            System.out.println(username + " failed to login");
            getUserLogin("login",user);
        }
        return;
    }
}
