package com.mp.demo.Model;


import com.mp.demo.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryModel {
    Connection conn;

    public HistoryModel() {
        this.conn = Utils.getDBConnection();
    }

    public void storeHistory(String userId, String musicId) {
        String sql = "INSERT INTO history (userId, musicId, timestamp) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, musicId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }
    }
    public ArrayList<MusicModel> getLastFiveMusics(String userId) {
        ArrayList<MusicModel> lastMusics = new ArrayList<>();
        String sql = "SELECT m.* FROM musics m JOIN (SELECT musicId FROM History WHERE userId = ? ORDER BY timestamp DESC LIMIT 5) h ON m.musicId = h.musicId";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MusicModel music = new MusicModel();
                    music.setMusicId(rs.getInt("musicId"));
                    music.setArtist(rs.getString("artist"));
                    music.setGenre(rs.getString("genre"));
                    music.setAlbum(rs.getString("album"));
                    music.setTitle(rs.getString("title"));
                    lastMusics.add(music);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }

        return lastMusics;
    }
}
