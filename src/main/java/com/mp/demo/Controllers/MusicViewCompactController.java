package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Model.MusicModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicViewCompactController implements Initializable {
    @FXML
    private AnchorPane CompactMV;

    @FXML
    private Text album;

    @FXML
    private Text artist;

    @FXML
    private Text genre;

    @FXML
    private Text title;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CompactMV.setOnMouseEntered(e -> {
            CompactMV.setStyle("-fx-background-color: #005CE6;-fx-background-radius: 10px;");
            CompactMV.setCursor(Cursor.HAND);
        });

        CompactMV.setOnMouseExited(e -> CompactMV.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px;"));
    }

    public void setMusic(MusicModel music){
       this.title.setText(music.getTitle());
       this.album.setText(music.getAlbum());
       this.genre.setText(music.getGenre());
       this.artist.setText(music.getArtist());
    }
}
