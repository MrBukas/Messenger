package com.Bukas.Console;

import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleMessage {

    public static void processMessage(Scanner scanner, PrintWriter printWriter){
        printWriter.println("Enter receiver username: ");
        String reciever = scanner.nextLine();
        printWriter.println("Enter message: ");
        String message = scanner.nextLine();
    }
    static void sendMessage(String reciever, String message){

    }

}
