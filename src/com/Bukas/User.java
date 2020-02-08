package com.Bukas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User {
    String username= null;
    Socket socket;
    Scanner scanner;
    PrintWriter printWriter;
    int id;
    boolean auth = false;

    public User(Socket socket) {
        this.socket = socket;
        try {
            scanner = new Scanner(getSocket().getInputStream());
            printWriter = new PrintWriter(getSocket().getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        id = DatabaseConnector.getUserId(username);
    }

    public Socket getSocket() {
        return socket;
    }

    public int getId() {
        return id;
    }


    public void write(String text){
        printWriter.println(text);
    }

    public String read(){
        return scanner.nextLine();
    }

    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
