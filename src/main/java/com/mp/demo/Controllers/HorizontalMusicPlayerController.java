package com.mp.demo.Controllers;

import com.jfoenix.controls.JFXSlider;
import com.mp.demo.App;
import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.NowPlayingModel;
import com.mp.demo.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HorizontalMusicPlayerController implements Initializable {
    @FXML
    private ImageView playbackSwitch;
    @FXML
    private JFXSlider VolumeSlider;
    @FXML
    private ImageView  nextSwitch;
    @FXML
    private ImageView  prevSwitch;
    @FXML
    private Text TimeStamp;
    @FXML
    private JFXSlider PlaybackSlider;
    private boolean isChanging = false;
    public static MediaPlayer mediaPlayer;
    private int changedSlider = 0;
    private SimpleBooleanProperty isPause = new SimpleBooleanProperty(true);
    private static double AudioLength;
    @FXML
    private ImageView albumPoster;
    @FXML
    private Text titleText;
    @FXML
    private Text artistText;
    @FXML
    private Text genreText;
    @FXML
    private HBox leftHbox;

    public HorizontalMusicPlayerController(){


    }
    public void setNewMedia(MusicModel music){
        if(mediaPlayer!= null){
            mediaPlayer.stop();
        }

        mediaPlayer = new MediaPlayer(new Media("http://localhost:3000/audio/"+music.getMusicId()));
        mediaPlayer.setOnReady(()->{
            AudioLength = mediaPlayer.getTotalDuration().toSeconds();
            PlaybackSlider.setMax(AudioLength);
            PlaybackSlider.setMin(0);
            PlaybackSlider.setValue(0.0);
            mediaPlayer.play();
            isPause.set(false);
            NowPlayingModel a = new NowPlayingModel(CentralUser.loggedInUser.id,Integer.toString(music.getMusicId()));
            a.updateNowPlaying();
            CentralUser.nowPlaying = music;
        });
        albumPoster.setImage(Utils.getAlbumImage(Utils.processAlbumName(music.getAlbum())));
        titleText.setText(music.getTitle());
        artistText.setText(music.getArtist());
        genreText.setText(music.getGenre());
        PlaybackSlider.setValue(0.0);

        PlaybackSlider.valueChangingProperty().addListener(((observableValue, number, t1) -> {
            isChanging = t1;
            if(!isChanging){
                mediaPlayer.seek(Duration.seconds(changedSlider));
            }
        }));

        PlaybackSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            if (isChanging) {
                changedSlider = t1.intValue();
                int newValue = (int) Duration.seconds(t1.intValue()).toSeconds();
                int hour = newValue/3600;
                int minute = (newValue%3600)/60;
                int second = newValue %60;
                TimeStamp.setText(hour+":"+minute+":"+second);
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
                TimeStamp.setText(hour+":"+minute+":"+second);
                PlaybackSlider.setValue(newValue);
            }
        }));


        VolumeSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            mediaPlayer.setVolume(VolumeSlider.getValue()*0.01);
        }));

        leftHbox.setCursor(Cursor.HAND);
        leftHbox.setOnMouseClicked(e->{
            CentralUser.dashboardController.setViewToBox4("MusicDetailedView.fxml");
        });

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            PlaybackSlider.setValue(0.0);
            PlaybackSlider.setCursor(Cursor.HAND);
            playbackSwitch.setOnMouseEntered(e -> {
                playbackSwitch.setCursor(Cursor.HAND);
            });

            isPause.addListener(((observableValue, aBoolean, t1) -> {
                if(t1)
                    playbackSwitch.setImage(Utils.getIcon("play.png"));
                else
                    playbackSwitch.setImage(Utils.getIcon("pause.png"));
            }));

            playbackSwitch.setOnMouseClicked(e -> {
                if(mediaPlayer!=null){
                    playAudio();
                }
            });

    }

    public void playAudio(){
        if(isPause.getValue()){
            isPause.set(false);
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
