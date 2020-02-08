package com.Bukas;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User {
    String username= null;
    Socket socket;
    Scanner scanner;
    PrintWriter printWriter;
    boolean auth = false;

    public User(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void write(String text){
        printWriter.println(text);
    }

    public String read(){
        return scanner.nextLine();
    }
}
