package com.mp.demo.Controllers;

import com.mp.demo.Model.MusicModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicViewController implements Initializable {
    private MusicModel music;
    @FXML
    private Text title;
    @FXML
    private ImageView albumPoster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setMusic(MusicModel music){
        this.music = music;
        title.setText(music.getTitle());
        albumPoster.setImage(Utils.getAlbumImage(music.getAlbum()+".png"));
    }
}
