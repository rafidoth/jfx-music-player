package com.mp.demo.Model;

import com.mp.demo.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    Connection conn;
    private String id;
    private String username;
    private String password;

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

    public boolean isCorrectAccount() {
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            res = ps.executeQuery();
            if (res.next()) {
                int count = res.getInt(1);
                return count == 1; // Return true if there's a match
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }
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




    public boolean createAccount() throws SQLException {
       PreparedStatement ps = null;
       String query = "INSERT INTO users (username, password, id) \n" + "VALUES (?,?,?);";
       try{
           System.out.println("hello world 3");
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
