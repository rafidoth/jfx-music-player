package com.mp.demo.Controllers;

import com.mp.demo.App;
import com.mp.demo.CentralUser;
import com.mp.demo.LyricLine;
import com.mp.demo.LyricParser;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.NowPlayingModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class MusicDetailedViewController implements Initializable {
    @FXML
    private Text listenerCounter;
    @FXML
private Text lyricsHolder;
    private ScheduledExecutorService scheduler1;
    private ScheduledExecutorService scheduler2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduler1 = Executors.newSingleThreadScheduledExecutor();
        startListenerCounterUpdate();
        scheduler2 = Executors.newSingleThreadScheduledExecutor();
        updateLyrics();
    }


    private void updateLyrics(){
        ArrayList<LyricLine> lyrics;
        try {
//            lyrics = LyricParser.parseLyricsFile("11231006.txt");
            lyrics = LyricParser.parseLyricsFile(CentralUser.nowPlaying.getMusicId()+".txt");
            if(lyrics== null){
                lyricsHolder.setText("No Lyric File Found");
            }
        } catch (IOException e) {
            lyricsHolder.setText("No Lyric File Found");
            return;
        }

        Runnable updateTask = () -> {
            long currentTime = (long) (HorizontalMusicPlayerController.mediaPlayer.getCurrentTime().toMillis());
            for (LyricLine lyricLine : lyrics) {
                if (currentTime >= lyricLine.getStartTime()) {
                    Platform.runLater(() -> lyricsHolder.setText(lyricLine.getLyricText()));
                }
            }
//            System.out.println("Lyrics : updateTask(_) running "+ (int) (Math.random() * Integer.MAX_VALUE));
        };
        scheduler2.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.SECONDS);
    }
    private void startListenerCounterUpdate() {
        Runnable updateTask = () -> {
            int newListenerCount;
            if(CentralUser.nowPlaying!=null){
                newListenerCount = fetchListenerCount(CentralUser.nowPlaying);
            } else {
                newListenerCount = 0;
            }
            if(newListenerCount >0){
                Platform.runLater(() -> listenerCounter.setText("Currently "+ newListenerCount + " people listening to this song"));
            }

//            System.out.println("listeningCounter : updateTask(_) running "+ (int) (Math.random() * Integer.MAX_VALUE));

        };
        scheduler1.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.SECONDS);
    }

    private int fetchListenerCount(MusicModel music) {
        return NowPlayingModel.countListeners(music);
    }

    // cleanup
    public void shutdown() {
        System.out.println("Time Task got shutdown");
        if (scheduler1 != null && !scheduler1.isShutdown()) {
            scheduler1.shutdown();
        }

        if(scheduler2!=null && !scheduler2.isShutdown()){
            scheduler2.shutdown();
        }
    }
}
