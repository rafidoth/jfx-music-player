package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicSearchBarController implements Initializable {
    @FXML
    private TextField musicSearchInput;
    @FXML
    public ImageView close;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            CentralUser.dashboardController.showMusicSearchResult(newValue);
        });
        close.setCursor(Cursor.HAND);
        close.setVisible(false);
        close.setOnMouseClicked(e->{
            CentralUser.dashboardController.showMusicSearchResult("");
        });



    }
}
