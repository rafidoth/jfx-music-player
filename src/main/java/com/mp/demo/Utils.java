package com.mp.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {
    public static Stage getStage(String fxmlFileName, int width, int height, String stageTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(),width,height);
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        return  stage;
    }

    public static FXMLLoader loadFXML(String file){
        return new FXMLLoader(App.class.getResource(file));
    }

    public static void setScene(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(),Constants.Screen1width,Constants.Screen1height);
        App.primaryStage.setTitle("smotify");
        App.primaryStage.setScene(scene);
    }

    public static FXMLLoader getLoaderSetScene(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(),Constants.Screen1width,Constants.Screen1height);
        App.primaryStage.setTitle("smotify");
        App.primaryStage.setScene(scene);
        return  fxmlLoader;
    }


    public static void setStage(String fxmlFileName, int width, int height, String stageTitle) throws IOException {
        Stage stage = getStage(fxmlFileName,width,height,stageTitle);
        if(App.primaryStage!=null){
            App.primaryStage.close();
        }
        App.primaryStage = stage;
        App.primaryStage.show();
    }



    public static String getHashedPassword(String plainPassword){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainPassword.getBytes());
            byte[] res = md.digest();
            StringBuilder hashed = new StringBuilder();
            for(byte x:res) hashed.append(String.format("%02x",x));
            return hashed.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    public static Connection getDBConnection(){
        return DatabaseConnection.getConnectionInstance();
    }

    public static void checkDBConnection(){
        Connection connection = DatabaseConnection.getConnectionInstance();
        try{
            if(connection.isClosed()){
                System.out.println("No Connection");
            }else{
                System.out.println("Connection ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Image getIcon(String iconName){
        String path = App.class.getResource("icons/"+iconName).toString();
//        System.out.println(path);
        Image img = new Image(path);
//        System.out.println(img.getUrl());
        return img;
    }

    public static Image getImage(String imageFileName){
        String path = App.class.getResource("images/"+imageFileName).toString();
        Image img = new Image(path);
        return img;
    }

    public static Image getWall(String imageFileName){
        String path = App.class.getResource("fullscreenwalls/"+imageFileName).toString();
        Image img = new Image(path);
        return img;
    }

    public static Image getAlbumImage(String imageFileName){
        String path = null;
        try {
            path = App.class.getResource("albumImages/"+imageFileName).toString();
        } catch (Exception e) {
            return Utils.getImage("default_player_album.png");

        }
        Image img = new Image(path);
        return img;
    }

    public static String processAlbumName(String albumName){
        return albumName.replaceAll("-", "").replaceAll("\\s", "").toLowerCase()+".png";
    }

    public static void closeResultSet(ResultSet res) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


