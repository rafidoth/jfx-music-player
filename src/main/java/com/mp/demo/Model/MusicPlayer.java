package com.mp.demo.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {
    private static Media sound;
    private static MediaPlayer mediaPlayer;
    private static double AudioLength;
    private static SimpleDoubleProperty currentPos = new SimpleDoubleProperty(0.0);
    private static String Title;
    public static void setMedia(String title){
        String path = title+ ".mp3";
        sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(new Media("http://localhost:3000/audio/123456"));
        mediaPlayer.setOnReady(()->{
            AudioLength = mediaPlayer.getTotalDuration().toSeconds();
            Title = title;
        });
//        AudioLength = mediaPlayer.getTotalDuration().toSeconds();

        mediaPlayer.currentTimeProperty().addListener((Observable,old,newValue)->{
            currentPos.set(newValue.toSeconds());
        });
    }
    public static double getAudioLength(){
        return AudioLength;
    }
    public static void seekTo(double value){
//        mediaPlayer.seek(Duration.millis(value));

    }
    public static void play(){
        mediaPlayer.play();
    }
    public static void pause(){
        mediaPlayer.pause();
    }

    public static SimpleDoubleProperty currentPosistionProperty(){
        return currentPos;
    }
    public static String getTitle(){
        return Title;
    }

}
