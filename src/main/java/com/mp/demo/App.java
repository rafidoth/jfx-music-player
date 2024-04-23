package com.mp.demo;

import atlantafx.base.theme.PrimerDark;
import com.mp.demo.Controllers.MusicPlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class App extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Utils.checkDBConnection();
        MusicPlayerController a = new MusicPlayerController();
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage = Utils.getStage("Dashboard.fxml",Constants.Screen1width,Constants.Screen1height,"Music Player");
        primaryStage = stage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}