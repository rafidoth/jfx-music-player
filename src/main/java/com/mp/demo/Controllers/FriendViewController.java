package com.mp.demo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendViewController implements Initializable {
    @FXML
    private Circle circle;
    Image img;
    @FXML
    private Label friendName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        circle.setFill(new ImagePattern(img));
    }

    public void setImage(String url){
        img = new Image(url);
        circle.setFill(new ImagePattern(img));
    }

    public void setName(String name){
        friendName.setText(name);
    }
}
