package com.Bukas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InboxProcessor {
    public static ArrayList<String> getInbox(int receiverId, int senderId){
        ArrayList<String> messages = new ArrayList<>();
        try {
            ResultSet resultSet = Main.connection.createStatement().executeQuery("select message from messages where receiver_id = " + receiverId + " and sender_id = " + senderId);
            resultSet.first();
            do {
                messages.add(resultSet.getString(1));
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return messages;
    }
}
