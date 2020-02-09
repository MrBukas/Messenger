package com.Bukas.Console;

import com.Bukas.DatabaseConnector;
import com.Bukas.InboxProcessor;
import com.Bukas.User;

import java.util.ArrayList;

public class ConsoleInbox {
    public static void showInboxMessages(User user){
        int receiverId = user.getId();
        user.write("Enter sender username: ");
        String sender = user.read();
        if (DatabaseConnector.checkIfUsernameNotTaken(sender)){
            user.write("This username doesn't exists");
            return;
        }
        int senderId = DatabaseConnector.getUserId(sender);
        ArrayList<String> messages = InboxProcessor.getInbox(receiverId,senderId);
        if (messages != null) {
            for (String message : messages) {
                user.write(message);
            }
        }else {
            user.write("No messages from this user");
        }
    }
}
