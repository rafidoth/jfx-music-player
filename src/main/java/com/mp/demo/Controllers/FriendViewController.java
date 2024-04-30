package com.mp.demo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendViewController implements Initializable {
    @FXML
    private Label friendName;

    @FXML
    ImageView onlinestatus;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onlinestatus.setVisible(false);
    }

    public void setImage(String url){
    }

    public void setName(String name){
        friendName.setText(name);
    }
}
