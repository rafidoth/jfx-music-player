package com.mp.demo.Model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {
    private static Media sound;
    private static MediaPlayer mediaPlayer;
    private static double AudioLength;
    private static SimpleDoubleProperty currentPos = new SimpleDoubleProperty(0.0);


    public static void setMedia(String path){
        sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnReady(()->{
            AudioLength = mediaPlayer.getTotalDuration().toMillis();
        });
        AudioLength = mediaPlayer.getTotalDuration().toMillis();

        mediaPlayer.currentTimeProperty().addListener((Observable,old,newValue)->{
            currentPos.set(newValue.toMillis());
        });
    }
    public static double getAudioLength(){
        return AudioLength;
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


}
