package com.mp.demo.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.mp.demo.Model.MusicPlayer;
import com.mp.demo.Utils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicPlayerController implements Initializable {
    @FXML
    private Label currentTimeStampLabel;
    @FXML
    private JFXSlider playbackSlider;
    @FXML
    private ImageView play_pause_imageView;
    private boolean isPaused = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicPlayer.currentPosistionProperty().addListener(((observableValue, number, t1) -> {
            currentTimeStampLabel.setText(t1.toString());
            playbackSlider.setValue((double)t1);
        }));
    }

    public void setAudioFile(String path){
        MusicPlayer.setMedia(path);
    }

    public void playAudio() throws FileNotFoundException {
        if(isPaused){
            isPaused = false;
            System.out.println(MusicPlayer.getAudioLength());
            playbackSlider.setMax(MusicPlayer.getAudioLength());
            playbackSlider.setValue(0);
            playbackSlider.setMin(0);
            play_pause_imageView.setImage(Utils.getIcon("pause.png"));
            MusicPlayer.play();
        }else {
            isPaused = true;
            play_pause_imageView.setImage(Utils.getIcon("play.png"));
            pauseAudio();
        }
    }


    public void pauseAudio(){
        MusicPlayer.pause();
    }




}
