package com.mp.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
}
