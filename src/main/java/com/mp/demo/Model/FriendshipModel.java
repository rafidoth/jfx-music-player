package com.mp.demo.Model;

import com.mp.demo.CentralUser;
import com.mp.demo.Utils;

import java.sql.*;
import java.util.ArrayList;

public class FriendshipModel {
    static Connection conn;
    UserModel user1;
    UserModel user2;
    public FriendshipModel(UserModel user1, UserModel user2){
        conn = Utils.getDBConnection();
        this.user1 = user1;
        this.user2 = user2;
    }

    public FriendshipModel(){
        conn = Utils.getDBConnection();

    }



    //sent request -> pending?
    public ArrayList<UserModel> getUsersWhoSentMeRequest() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserModel> usersWithPendingRequests = new ArrayList<>();
        String sql = "SELECT user1 FROM Friendship WHERE user2 = ? AND status = 'pending'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, CentralUser.loggedInUser.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String senderUserId = rs.getString("user1");
                // Fetch user details using senderUserId and add to the list
                UserModel senderUser = new UserModel().getUserById(senderUserId);
                if (senderUser != null) {
                    usersWithPendingRequests.add(senderUser);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return usersWithPendingRequests;
    }
    public boolean friendshipExists() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean friendshipExists = false;
        String sql = "SELECT COUNT(*) FROM Friendship WHERE (user1 = ? AND user2 = ? OR user1 = ? AND user2 = ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.user1.id);
            ps.setString(2, this.user2.id);
            ps.setString(3, this.user2.id);
            ps.setString(4, this.user1.id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                friendshipExists = count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return friendshipExists;
    }

    public void deleteFriendship() {
        PreparedStatement ps = null;
        String sql = "DELETE FROM Friendship WHERE (user1 = ? AND user2 = ?) OR (user1 = ? AND user2 = ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.user1.id);
            ps.setString(2, this.user2.id);
            ps.setString(3, this.user2.id);
            ps.setString(4, this.user1.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }

    public ArrayList<UserModel> getAllFriends() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserModel> friendsList = new ArrayList<>();
        String sql = "SELECT * FROM Friendship WHERE (user1 = ? OR user2 = ?) AND status = 'established'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, CentralUser.loggedInUser.id);
            ps.setString(2, CentralUser.loggedInUser.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String friendId = rs.getString("user1").equals(CentralUser.loggedInUser.id) ? rs.getString("user2") : rs.getString("user1");
                UserModel friend = new UserModel().getUserById(friendId);
                if (friend != null) {
                    friendsList.add(friend);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return friendsList;
    }

    public ArrayList<UserModel> getUsersISentRequest() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserModel> usersWithPendingRequests = new ArrayList<>();
        String sql = "SELECT user2 FROM Friendship WHERE user1 = ? AND status = 'pending'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, CentralUser.loggedInUser.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String senderUserId = rs.getString("user2");
                // Fetch user details using senderUserId and add to the list
                UserModel senderUser = new UserModel().getUserById(senderUserId);
                if (senderUser != null) {
                    usersWithPendingRequests.add(senderUser);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return usersWithPendingRequests;
    }


    public void sendFriendRequest() {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Friendship (user1, user2, status) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.user1.id);
            ps.setString(2, this.user2.id);
            ps.setString(3, "pending");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }

    public void removeFriend(UserModel user) {
        PreparedStatement ps = null;
        String sql = "DELETE FROM Friendship WHERE (user1 = ? AND user2 = ?) OR (user1 = ? AND user2 = ?) AND status = 'established'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, CentralUser.loggedInUser.id);
            ps.setString(2, user.id);
            ps.setString(3, user.id);
            ps.setString(4, CentralUser.loggedInUser.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }


    public void acceptFriendshipRequest(UserModel userToBeAccepted) {
        PreparedStatement ps = null;
        String sql = "UPDATE Friendship SET status = 'established' WHERE user1 = ? AND user2 = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userToBeAccepted.id);
            ps.setString(2, CentralUser.loggedInUser.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }


    public  int countPendingFriendshipRequests(String userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Friendship WHERE user2 = ? AND status = 'pending'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }
        return count;
    }

    public void deletePendingRequest(UserModel u1, UserModel u2) {
        PreparedStatement ps = null;
        String sql = "DELETE FROM Friendship WHERE user1 = ? AND user2 = ? AND status = 'pending'";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, u1.id);
            ps.setString(2, u2.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }
}
