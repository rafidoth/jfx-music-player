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

   public boolean isAlreadyHasAccount() throws SQLException {
       PreparedStatement ps = null;
       ResultSet res = null;
       String sql = "select * from users where id = ? & username = ? and password = ? ";
       try {
           ps = conn.prepareStatement(sql);
           ps.setString(1,id);
           ps.setString(2,username);
           ps.setString(3,password);
           res = ps.executeQuery();
           if(res.next()) return true;
           else return false;

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }finally {
            ps.close();
            res.close();
            conn.close();
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

           try {
               if (conn != null && !conn.isClosed()) {
                   if (!conn.getAutoCommit()) {
                       conn.commit();
                       conn.setAutoCommit(true);
                   }
                   conn.close();
               }
           } catch (SQLException sqle) {
               sqle.printStackTrace();
           }
       }

   }

}
