package com.Bukas;


import com.Bukas.Android.AndroidClient;
import com.Bukas.Console.ConsoleClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<User> users = new ArrayList<>();// Сомневаюсь что оно нужно, оставил чтобы не забыть
    public static Connection connection = DatabaseConnector.connect();

    public static void main(String[] args) throws IOException {
        new Thread(new AndroidSocket()).start();
        try(ServerSocket serverSocket = new ServerSocket(5678)){
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("new client(Console)");
                new Thread(new ConsoleClient(new User(socket))).start();
            }
        }
    }
}
class AndroidSocket implements Runnable{
    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(5679)){
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("new client (Android)");
                new Thread(new AndroidClient(new User(socket))).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
