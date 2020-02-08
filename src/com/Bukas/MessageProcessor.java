package com.Bukas;

import java.sql.Connection;
import java.sql.SQLException;

public class MessageProcessor {
    static Connection connection = Main.connection;
    public static int sendMessage(int senderId, String reciever, String message){
        int receiverId = DatabaseConnector.getUserId(reciever);
        if (receiverId == -1){
            return -1;
        }

        try {
            connection.createStatement().executeUpdate("INSERT INTO messages(sender_id,receiver_id,message)"+" VALUES (\""+senderId+"\",\""+receiverId+"\",\""+message+"\")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
