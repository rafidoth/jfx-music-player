package com.mp.demo.Controllers;

import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane bottomPlayer;
    @FXML
    private HBox homebtn;
    @FXML
    private HBox libBtn;
    @FXML
    private VBox friendsView;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnchorPane temp = Utils.loadFXML("HorizontalMusicPlayer.fxml").load();
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            bottomPlayer.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // homebtn
        homebtn.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;");
        homebtn.setOnMouseEntered(e -> {
            homebtn.setStyle("-fx-background-color: #1DB954;-fx-background-radius: 10px; -fx-padding : 10px;");
            homebtn.setCursor(Cursor.HAND);
        });
        homebtn.setOnMouseExited(e -> homebtn.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;"));
        homebtn.setOnMouseClicked(e -> System.out.println("Button clicked!"));
        // lib btn
        libBtn.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;");
        libBtn.setOnMouseEntered(e -> {
            libBtn.setStyle("-fx-background-color: #1DB954;-fx-background-radius: 10px; -fx-padding : 10px;");
            libBtn.setCursor(Cursor.HAND);
        });
        libBtn.setOnMouseExited(e -> libBtn.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;"));
        libBtn.setOnMouseClicked(e -> System.out.println("Button clicked!"));

        //Friends View
        for(int i =1;i<50;i+=10){
            addFriendView("https://i.pravatar.cc/150?img="+i, i+"fasdf"+i*2);
        }

    }

    public void addFriendView(String url, String name){
        try {
            FXMLLoader loader = Utils.loadFXML("friendView.fxml");
            HBox friendView = loader.load();
            FriendViewController friendViewController = loader.getController();
            friendViewController.setImage(url);
            friendViewController.setName(name);
            friendsView.getChildren().add(friendView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



