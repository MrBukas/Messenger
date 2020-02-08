package com.Bukas.Console;

import com.Bukas.MessageProcessor;
import com.Bukas.User;

import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleMessage {

    public static void processMessage(User user){
        user.write("Enter receiver username: ");
        String reciever = user.read();
        if (reciever.equals("")){
            user.write("Empty user");
            return;
        }
        user.write("Enter message: ");
        String message = user.read();
        if (message.equals("")){
            user.write("Can't send empty message");
            return;
        }
        int res = MessageProcessor.sendMessage(user.getId(),reciever,message);
        switch (res){
            case 0: user.write("Message sent"); break;
            case -1: user.write("No such user"); break;
        }
    }


}
