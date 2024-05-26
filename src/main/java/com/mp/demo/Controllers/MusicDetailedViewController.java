package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.NowPlayingModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MusicDetailedViewController implements Initializable {
    @FXML
    private Text listenerCounter;
    private ScheduledExecutorService scheduler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduler = Executors.newScheduledThreadPool(1);
        startListenerCounterUpdate();
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
        };
        // Schedule the task to run every 1 second
        scheduler.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.SECONDS);
    }

    // Method to fetch the listener count (dummy implementation)
    private int fetchListenerCount(MusicModel music) {
        return NowPlayingModel.countListeners(music);
    }

    // Clean up the scheduler when the controller is no longer needed
    public void shutdown() {
        System.out.println("Time Task got shutdown");
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
