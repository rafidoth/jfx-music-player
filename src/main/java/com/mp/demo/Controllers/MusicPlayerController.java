package com.mp.demo.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.mp.demo.Model.MusicPlayer;
import com.mp.demo.Utils;
import javafx.beans.property.SimpleBooleanProperty;
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
    private JFXSlider playbackSlider;
    @FXML
    private ImageView play_pause_imageView;
    private boolean isPaused = true;
    private SimpleBooleanProperty isPause = new SimpleBooleanProperty(true);
    @FXML
    private Label timestamp;
    @FXML
    private Label titleLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playbackSlider.setValue(0.0);
        MusicPlayer.currentPosistionProperty().addListener(((observableValue, number, t1) -> {
            int hour = t1.intValue() /3600;
            int minute = (t1.intValue()%3600)/60;
            int second = t1.intValue() %60;
            timestamp.setText(hour+":"+minute+":"+second);
            playbackSlider.setValue((double)t1);
        }));

        isPause.addListener(((observableValue, aBoolean, t1) -> {
            if(t1)
                play_pause_imageView.setImage(Utils.getIcon("play.png"));
            else
                play_pause_imageView.setImage(Utils.getIcon("pause.png"));
        }));
    }

    public void setAudioFile(String name){
        MusicPlayer.setMedia(name);
    }

    public void playAudio() throws FileNotFoundException {
        titleLabel.setText(MusicPlayer.getTitle());
        if(isPause.getValue()){
            isPause.set(false);
            playbackSlider.setMax(MusicPlayer.getAudioLength());
            playbackSlider.setValue(0);
            playbackSlider.setMin(0);
            MusicPlayer.play();
        }else {
            isPause.set(true);
            pauseAudio();
        }
    }


    public void pauseAudio(){
        MusicPlayer.pause();
    }




}
