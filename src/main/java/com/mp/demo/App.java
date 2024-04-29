package com.mp.demo;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class App extends Application {
    public static Stage primaryStage;
    public static Stage secondaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Utils.checkDBConnection();
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
//        Client.connectToSocketServer();// issue take this to login and new user controllers
        stage = Utils.getStage("LogInView.fxml",Constants.Screen1width,Constants.Screen1height,"smotify");
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}