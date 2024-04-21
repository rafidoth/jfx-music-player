package com.mp.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class Utils {
    public static Stage getStage(String fxmlFileName, int width, int height, String stageTitle) throws IOException {
        System.out.println("hello1");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
        System.out.println("Hello 2");
        Scene scene = new Scene(fxmlLoader.load(),width,height);
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        System.out.println("ok");
        return  stage;
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
}
