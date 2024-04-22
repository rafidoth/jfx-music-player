package com.mp.demo.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.mp.demo.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
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
    MediaPlayer mediaPlayer;
    private static double AudioLength;
    private boolean isChanging = false;
    private int changedSlider = 0;
    public MusicPlayerController(){
        mediaPlayer = new MediaPlayer(new Media("http://localhost:3000/audio/123456"));
//        mediaPlayer = new MediaPlayer(new Media(new File("phone.mp3").toURI().toString()));
        mediaPlayer.setOnReady(()->{
            AudioLength = mediaPlayer.getTotalDuration().toSeconds();

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playbackSlider.setValue(0.0);
        playbackSlider.valueChangingProperty().addListener(((observableValue, number, t1) -> {
            isChanging = t1;
            if(!isChanging){
                mediaPlayer.seek(Duration.seconds(changedSlider));
            }
        }));
        playbackSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            if (isChanging) {
                changedSlider = t1.intValue();
                int newValue = (int) Duration.seconds(t1.intValue()).toSeconds();
                int hour = newValue/3600;
                int minute = (newValue%3600)/60;
                int second = newValue %60;
                timestamp.setText(hour+":"+minute+":"+second);
            } else {
                int change = Math.abs(t1.intValue() - number.intValue());
                if (change > 10) {
                    mediaPlayer.seek(Duration.seconds(t1.intValue()));
                }
            }
        }));

        mediaPlayer.currentTimeProperty().addListener(((observableValue, number, t1) -> {
            if(!isChanging){
                int newValue = (int)t1.toSeconds();
                int hour = newValue/3600;
                int minute = (newValue%3600)/60;
                int second = newValue %60;
                timestamp.setText(hour+":"+minute+":"+second);
                playbackSlider.setValue(newValue);
                System.out.println(t1.toSeconds());
            }
        }));


        isPause.addListener(((observableValue, aBoolean, t1) -> {
            if(t1)
                play_pause_imageView.setImage(Utils.getIcon("play.png"));
            else
                play_pause_imageView.setImage(Utils.getIcon("pause.png"));
        }));
    }


    public void playAudio() throws FileNotFoundException {
        titleLabel.setText("Lorem Ipsum");
        if(isPause.getValue()){
            isPause.set(false);
            playbackSlider.setMax(AudioLength);
            playbackSlider.setMin(0);
            mediaPlayer.play();
        }else {
            isPause.set(true);
            pauseAudio();
        }
    }

    public void pauseAudio(){
        mediaPlayer.pause();
    }




}
