package com.mp.demo.Model;

import com.mp.demo.Utils;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    Connection conn;
    public String id;
    public String username;
    public String password;

   public UserModel(String id, String username, String password){
       conn = Utils.getDBConnection();
       this.username = username;
       this.password = password;
       this.id = id;
   }

   public UserModel(String username, String password){
       conn = Utils.getDBConnection();
       this.username = username;
       this.password = password;
   }
   public UserModel(){
       conn = Utils.getDBConnection();
   }

    public UserModel getUserById(String userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT username, password FROM users WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                // If a matching user is found, create a UserModel object and return it
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new UserModel(userId, username, password);
            }
            return null; // Return null if no matching user is found
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
    }
   public  ArrayList<UserModel> searchUser(String searchString) {
        PreparedStatement ps = null;
        ResultSet res = null;
        ArrayList<UserModel> userList = new ArrayList<>();
        String sql = "SELECT id, username, password FROM users WHERE username LIKE ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchString + "%");
            res = ps.executeQuery();
            while (res.next()) {
                String id = res.getString("id");
                String username = res.getString("username");
                String password = res.getString("password");
                UserModel user = new UserModel(id, username, password);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }
        return userList;
    }

//    public boolean isCorrectAccount() {
//        PreparedStatement ps = null;
//        ResultSet res = null;
//        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
//        try {
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, username);
//            ps.setString(2, password);
//            res = ps.executeQuery();
//            if (res.next()) {
//                int count = res.getInt(1);
//                return count == 1; // Return true if there's a match
//            }
//            return false;
//        } catch (SQLException e) {
//            throw new RuntimeException("Error executing SQL query", e);
//        } finally {
//            // Close resources
//            Utils.closeResultSet(res);
//            Utils.closeStatement(ps);
//        }
//    }

    public void updateUserStatus(String userId, String status) {
        PreparedStatement ps = null;
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user status", e);
        } finally {
            // Close resources
            Utils.closeStatement(ps);
        }
    }

    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id, username, password FROM users WHERE status = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "online");
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                onlineUsers.add(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return onlineUsers;
    }

    public boolean isUsernameAvailable() {
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            res = ps.executeQuery();
            if (res.next()) {
                int count = res.getInt(1);
                return count == 0;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public UserModel isCorrectAccount() {
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT username, password, id FROM users WHERE username = ? AND password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            res = ps.executeQuery();
            if (res.next()) {
                // If a matching user is found, create a UserModel object and return it
                String  userId = res.getString("id"); // Retrieve userId as an integer
                String username = res.getString("username");
                String password = res.getString("password");
                return new UserModel(userId, username, password);
            }
            return null; // Return null if no matching user is found
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }
    }





    public boolean createAccount() throws SQLException {
       PreparedStatement ps = null;
       String query = "INSERT INTO users (username, password, id) \n" + "VALUES (?,?,?);";
       try{
//           System.out.println("hello world 3");
           ps = conn.prepareStatement(query);
           ps.setString(1,username);
           ps.setString(2,password);
           ps.setString(3,id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }finally {
           if (ps != null) {
               try {
                   ps.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }

   }

}
