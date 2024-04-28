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
        Client.connectToSocketServer();
        stage = Utils.getStage("NewUserView.fxml",Constants.Screen2width,Constants.Screen2height,"Music Player");
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}