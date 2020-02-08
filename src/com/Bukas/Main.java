package com.Bukas;


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
        try(ServerSocket serverSocket = new ServerSocket(5678)){
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new ConsoleClient(new User(socket))).start();
            }
        }
    }
}
