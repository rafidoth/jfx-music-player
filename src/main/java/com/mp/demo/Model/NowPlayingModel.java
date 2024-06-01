package com.mp.demo.Model;

import com.mp.demo.CentralUser;
import com.mp.demo.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NowPlayingModel {
    String userId;
    String musicId;
    static Connection conn ;


    public NowPlayingModel(String userId, String musicId) {
        this.userId = userId;
        this.musicId = musicId;
        this.conn = Utils.getDBConnection();
    }

    public NowPlayingModel() {
        this.conn = Utils.getDBConnection();
    }
    public static int countListeners(MusicModel music) {
        int listenerCount = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS count FROM NowPlaying WHERE musicId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(music.getMusicId()));
            rs = ps.executeQuery();

            if (rs.next()) {
                listenerCount = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }

        return listenerCount;
    }

    public void updateNowPlaying() {
        PreparedStatement ps = null;
        String sql = "INSERT INTO NowPlaying (userId, musicId) VALUES (?, ?) " +
                "ON CONFLICT(userId) DO UPDATE SET musicId = excluded.musicId";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, musicId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }


    public static void deleteUserNowPlaying() {
        PreparedStatement ps = null;
        String sql = "DELETE FROM NowPlaying WHERE userId = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, CentralUser.loggedInUser.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeStatement(ps);
        }
    }

    public static MusicModel userListeningNow(String userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MusicModel music = null;
        String sql = "SELECT musicId FROM NowPlaying WHERE userId = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String musicId = rs.getString("musicId");
                music = new MusicModel().getMusicById(Integer.parseInt(musicId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(ps);
        }

        return music;
    }

}
