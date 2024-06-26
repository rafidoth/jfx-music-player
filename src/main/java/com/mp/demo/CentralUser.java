package com.mp.demo;

import com.mp.demo.Controllers.ChatViewController;
import com.mp.demo.Controllers.DashboardController;
import com.mp.demo.Controllers.PendingRequestController;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.UserModel;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CentralUser {
    public static UserModel loggedInUser;
    public static MusicModel nowPlaying;
    public static DashboardController dashboardController;
    public static PendingRequestController pendingRequestController;

    public static Socket socket;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public static SimpleIntegerProperty listenChangeFR = new SimpleIntegerProperty(0);
    public static SimpleIntegerProperty listenChangeCHAT = new SimpleIntegerProperty(0);
    public static SimpleIntegerProperty listenChangeUSER_LIST = new SimpleIntegerProperty(0);
    public static ArrayList<String> onlineFriends = new ArrayList<>();


    public static void logOut(){
        loggedInUser = null;
    }
}
