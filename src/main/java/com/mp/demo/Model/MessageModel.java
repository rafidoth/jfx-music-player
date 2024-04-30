package com.mp.demo.Model;

import com.mp.demo.CentralUser;
import com.mp.demo.Utils;

import java.sql.*;
import java.util.ArrayList;

public class MessageModel {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    Connection conn;
    private String content;
    private String sender;
    private String recipient;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Timestamp timestamp;
    public MessageModel(String content, String sender, String recipient) {
        conn = Utils.getDBConnection();
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public MessageModel(String content, String sender, String recipient, Timestamp timestamp) {
        conn = Utils.getDBConnection();
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
    }

    public MessageModel() {
        conn = Utils.getDBConnection();
    }

    public void insertMessage() {
        PreparedStatement ps = null;
        String sql = "INSERT INTO messages (content, sender, recipient) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.content);
            ps.setString(2, this.sender);
            ps.setString(3, this.recipient);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }


    public ArrayList<MessageModel> getMessagesBetweenUsers(UserModel friend) {
        ArrayList<MessageModel> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY timestamp";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CentralUser.loggedInUser.id);
            pstmt.setString(2, friend.id);
            pstmt.setString(3, friend.id);
            pstmt.setString(4, CentralUser.loggedInUser.id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String content = rs.getString("content");
                String sender = rs.getString("sender");
                String recipient = rs.getString("recipient");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                MessageModel message = new MessageModel(content, sender, recipient, timestamp);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        }

        return messages;
    }

}
