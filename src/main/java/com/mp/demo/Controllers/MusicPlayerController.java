package com.mp.demo.Controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayerController {
    public void playMusic(){
//        File musicFile = new File("src/main/java/com/mp/music_player_jfx/songs/"+"phone.mp3");
        File musicFile = new File("phone.mp3");
        if(musicFile.exists() && musicFile.isFile()){
            try{
                Media sound = new Media(musicFile.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("File doesn't exitst!!");
        }

    }
}
