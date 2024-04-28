package com.mp.demo.Model;

import com.mp.demo.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicModel {
    Connection conn;
    String title;
    int musicId;
    String artist;
    String genre;
    String album;

    public String getTitle() {
        return title;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public MusicModel(){
        conn = Utils.getDBConnection();
    }
    public ArrayList<MusicModel> getAllMusicsFromOneArtist(String artistName){
        ArrayList<MusicModel> musics = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT * FROM musics WHERE artist = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, artistName);
            res = ps.executeQuery();
            while (res.next()) {
                MusicModel music = new MusicModel();
                music.setMusicId(res.getInt("musicId"));
                music.setArtist(res.getString("artist"));
                music.setGenre(res.getString("genre"));
                music.setAlbum(res.getString("album"));
                music.setTitle(res.getString("title"));
                musics.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }

        return musics;
    }

    public ArrayList<MusicModel> getAllMusics(){
        ArrayList<MusicModel> musics = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT * FROM musics";
        try {
            ps = conn.prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                MusicModel music = new MusicModel();
                music.setMusicId(res.getInt("musicId"));
                music.setArtist(res.getString("artist"));
                music.setGenre(res.getString("genre"));
                music.setAlbum(res.getString("album"));
                music.setTitle(res.getString("title"));
                musics.add(music);
//                System.out.println(res.getString("album").replaceAll("-", "").replaceAll("\\s", "").toLowerCase());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }

        return musics;
    }

    public ArrayList<MusicModel> searchMusic(String searchString) {
        ArrayList<MusicModel> musics = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet res = null;
        String sql = "SELECT * FROM musics WHERE title LIKE ? OR artist LIKE ? OR genre LIKE ? OR album LIKE ?";
        try {
            ps = conn.prepareStatement(sql);
            String likeSearch = "%" + searchString + "%";
            ps.setString(1, likeSearch);
            ps.setString(2, likeSearch);
            ps.setString(3, likeSearch);
            ps.setString(4, likeSearch);
            res = ps.executeQuery();
            while (res.next()) {
                MusicModel music = new MusicModel();
                music.setMusicId(res.getInt("musicId"));
                music.setArtist(res.getString("artist"));
                music.setGenre(res.getString("genre"));
                music.setAlbum(res.getString("album"));
                music.setTitle(res.getString("title"));
                musics.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            // Close resources
            Utils.closeResultSet(res);
            Utils.closeStatement(ps);
        }

        return musics;
    }
}
