package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.CurrentUITracks.CurrentFriendlist;
import com.mp.demo.Model.FriendshipModel;
import com.mp.demo.Model.HistoryModel;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Server.Server;
import com.mp.demo.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane ParentContainer;

    @FXML
    private AnchorPane bottomPlayer;
    @FXML
    private HBox homebtn;
    @FXML
    private HBox libBtn;
    @FXML
    private HBox findFriendsBtn;
    @FXML
    private VBox friendsView;
    @FXML
    private HBox musicBox;
    @FXML
    private HBox recentlyplayed;
    @FXML
    private AnchorPane mainDashboardView;
    private AnchorPane storedMaindashboardView;

    @FXML
    private AnchorPane searchBarContainer;
    @FXML
    private VBox searchResult;
    @FXML
    private AnchorPane box4;
    @FXML
    private AnchorPane box4Container;
    private AnchorPane storedBox4;

    ArrayList<MusicModel> musicsList;
    @FXML
    private StackPane profile;

    @FXML
    private StackPane pendingRequest;
    @FXML
    public Text pendingRequestText;

    @FXML
    private Rectangle pendingRequestBackground;

    public HorizontalMusicPlayerController hmp;
    public MusicSearchBarController msc;
    public MusicDetailedViewController mdvc;
    public String nowOnBox4 = "";
    public ArrayList<UserModel> myFriends;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchResult.setVisible(false);
        storedMaindashboardView = mainDashboardView;
        storedBox4 = box4;
        FXMLLoader loader = Utils.loadFXML("HorizontalMusicPlayer.fxml");
        try {
            AnchorPane temp = loader.load();
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            bottomPlayer.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hmp = loader.getController();

        loader = Utils.loadFXML("MusicSearchBar.fxml");
        try {
            AnchorPane temp = loader.load();
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            searchBarContainer.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        msc =loader.getController();



        // homebtn
        makeButtonFromHBOX(homebtn);
        homebtn.setOnMouseClicked(e->{
            setDashboardViewToBox4();
        });



        // lib btn
        makeButtonFromHBOX(libBtn);


        // findFriends
        makeButtonFromHBOX(findFriendsBtn);
        findFriendsBtn.setOnMouseClicked(e->{
            FXMLLoader loader2 = setViewToBox4("FindFriendsView.fxml");
        });



        //Friends View
        myFriends = new FriendshipModel().getAllFriends();
        int len = Math.min(12,myFriends.size());
        for(int i =0;i<len;i++){
            addFriendView(myFriends.get(i));
        }





        // adding to recently played
        ///////////////////////////////
        ArrayList<MusicModel> recentyPlayedFive = new HistoryModel().getLastFiveMusics(CentralUser.loggedInUser.id);
        HashSet<Integer> seenIds = new HashSet<>();
        ArrayList<MusicModel> uniqueList = new ArrayList<>();

        for (MusicModel obj : recentyPlayedFive) {
            if (!seenIds.contains(obj.getMusicId())) {
                seenIds.add(obj.getMusicId());
                uniqueList.add(obj);
            }
        }

        for(int i=0;i<uniqueList.size();i++){
            addMusicViewToRecentlyPlayed(uniqueList.get(i));
        }



        // You may love music list
        musicsList = new MusicModel().getAllMusics();
        ArrayList<MusicModel> you_may_love = new ArrayList<>();
        if(uniqueList.size()!=0){
            for(int i=0;i<musicsList.size();i++){
                for(int j=0;j<uniqueList.size();j++){
                    if(!Integer.toString(musicsList.get(i).getMusicId()).equals(uniqueList.get(j).getMusicId())){
                        you_may_love.add(musicsList.get(i));
                    }
                }
            }
        }else{
            you_may_love = musicsList;
        }
        Collections.shuffle(you_may_love);
        for(int i = 0; i<Math.min(5,you_may_love.size());i++){
            addMusicView(you_may_love.get(i));
        }



        // Pending Request
        pendingRequestText.setText(Integer.toString(new FriendshipModel().countPendingFriendshipRequests(CentralUser.loggedInUser.id))+" pending request");
        pendingRequest.setCursor(Cursor.HAND);
        pendingRequest.setOnMouseEntered(e->{
            pendingRequestBackground.setFill(Color.web("#00378a"));
        });

        pendingRequest.setOnMouseExited(e->{
            pendingRequestBackground.setFill(Color.web("#005ce6"));
        });

        pendingRequest.setOnMouseClicked(e->{
            FXMLLoader loader2 = setViewToBox4("PendingRequest.fxml");
            PendingRequestController controller = loader2.getController();
            CentralUser.pendingRequestController = controller;
//            System.out.println("Pending request controller initialized: " + (CentralUser.pendingRequestController != null));
            controller.updatePendingRequests();
        });


        // friendship table changed
        CentralUser.listenChangeFR.addListener(((observableValue, oldValue, newValue) -> {
//            System.out.println(newValue);
            Platform.runLater(()->{
                pendingRequestText.setText(Integer.toString(new FriendshipModel().countPendingFriendshipRequests(CentralUser.loggedInUser.id))+" pending request");
                updateFriendList();
            });
        }));

        // Messages Table Changed
        CentralUser.listenChangeCHAT.addListener(((observableValue, oldValue, newValue) -> {
            if(nowOnBox4.equals("ChatView.fxml")){
                ChatViewTracker.chatViewController.updateChatView(ChatViewTracker.friend);
            }
        }));


        // users table changed
        CentralUser.listenChangeUSER_LIST.addListener(((observableValue, oldValue, newValue) -> {
            updateFriendList();
        }));

        // friendlist online status update
        // CurrentFriendlist.controllers.addListener();

       friendListUpdateScheduler = Executors.newSingleThreadScheduledExecutor();
       scheduledUpdateFriendList();
    }

    private ScheduledExecutorService friendListUpdateScheduler;
    void scheduledUpdateFriendList(){
       Runnable updateTask = ()-> {
            updateFriendList();
            updatePendingRequestCountTopBar();
        };
       friendListUpdateScheduler.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        System.out.println("Time Task got shutdown");
        if (friendListUpdateScheduler != null && !friendListUpdateScheduler.isShutdown()) {
            friendListUpdateScheduler.shutdown();
        }
    }


    public void updatePendingRequestCountTopBar(){
        if(pendingRequestText!=null){
            pendingRequestText.setText(Integer.toString(new FriendshipModel().countPendingFriendshipRequests(CentralUser.loggedInUser.id))+" pending request");
        }
    }



    public void updatePendingRequestList(){
        FXMLLoader loader2 = setViewToBox4("PendingRequest.fxml");
        PendingRequestController controller = loader2.getController();
        CentralUser.pendingRequestController = controller;
//        System.out.println("Pending request controller initialized: " + (CentralUser.pendingRequestController != null));
        controller.updatePendingRequests();
    }

    public void updateFriendList(){
        Platform.runLater(()->{
            friendsView.getChildren().clear();
            myFriends = new FriendshipModel().getAllFriends();
//        System.out.println("all friends: "+ myFriends);
            int len = Math.min(7,myFriends.size());
            for(int i =0;i<len;i++){
                addFriendView(myFriends.get(i));
            }
        });
    }
    public void addFriendView( UserModel friend){
        Platform.runLater(()->{
            try {
                FXMLLoader loader = Utils.loadFXML("friendView.fxml");
                HBox friendView = loader.load();
                FriendViewController friendViewController = loader.getController();
                friendViewController.setName(friend.username);
                ArrayList<UserModel> online = new FriendshipModel().getAllOnlineFriends();
                for(UserModel x: online){
                    if(x.id.equals(friend.id)){
                        friendViewController.onlinestatus.setVisible(true);
                    }
                }
//                if(CentralUser.onlineFriends != null && CentralUser.onlineFriends.size()!=0){
//                    System.out.println(CentralUser.onlineFriends);
//                    if(CentralUser.onlineFriends.contains(friend.id)){
//                        friendViewController.onlinestatus.setVisible(true);
//                    }
//                }
                makeButtonFromHBOX(friendView);
                friendView.setOnMouseClicked(e->{
                    FXMLLoader loader1 = setViewToBox4("ChatView.fxml");
                    ChatViewController controller = loader1.getController();
                    ChatViewTracker.chatViewController = controller;
                    ChatViewTracker.friend = friend;
                    controller.updateChatView(friend);
                });
                friendsView.getChildren().add(friendView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void addMusicView(MusicModel music) {
        try {
            FXMLLoader loader = Utils.loadFXML("MusicView.fxml");
            AnchorPane musicView = loader.load();
            MusicViewController musicViewController = loader.getController();
            musicViewController.setMusic(music);
            musicView.setOnMouseEntered(e -> {
                musicView.setStyle("-fx-background-color: #272727;-fx-background-radius: 10px; -fx-padding : 10px;");
                musicView.setCursor(Cursor.HAND);
            });
            musicView.setOnMouseExited(e -> {
                musicView.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;");
            });
            musicView.setOnMouseClicked(e->{
                hmp.setNewMedia(music);
            });
            musicBox.getChildren().add(musicView);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addMusicViewToRecentlyPlayed(MusicModel music) {
        try {
            FXMLLoader loader = Utils.loadFXML("MusicView.fxml");
            AnchorPane musicView = loader.load();
            MusicViewController musicViewController = loader.getController();
            musicViewController.setMusic(music);
            musicView.setOnMouseEntered(e -> {
                musicView.setStyle("-fx-background-color: #272727;-fx-background-radius: 10px; -fx-padding : 10px;");
                musicView.setCursor(Cursor.HAND);
            });
            musicView.setOnMouseExited(e -> {
                musicView.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;");
            });
            musicView.setOnMouseClicked(e->{
                hmp.setNewMedia(music);
            });
            recentlyplayed.getChildren().add(musicView);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void  makeButtonFromHBOX(HBox hBox){
        hBox.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;");
        hBox.setOnMouseEntered(e -> {
            hBox.setStyle("-fx-background-color: #005CE6;-fx-background-radius: 10px; -fx-padding : 10px;");
            hBox.setCursor(Cursor.HAND);
        });
        hBox.setOnMouseExited(e -> hBox.setStyle("-fx-background-color: transparent;-fx-background-radius: 10px; -fx-padding : 10px;"));
    }

    public void setProfile(){
        char ch = CentralUser.loggedInUser.username.toUpperCase().charAt(0);
        Text text = new Text(Character.toString(ch));
        text.setFont(Font.font("Fixedsys Excelsior 3.01", FontWeight.BOLD, 30));
        //Fixedsys Excelsior 3.01
        text.setFill(Color.WHITE);
        profile.getChildren().add(text);
        profile.setOnMouseEntered(e->{
            profile.setCursor(Cursor.HAND);
        });

        profile.setOnMouseClicked(e->{
            setToParentContainer("UserProfileView.fxml");
        });
    }

    public void setToParentContainer(String fxmlFilename){
        FXMLLoader loader = Utils.loadFXML(fxmlFilename);
        try {
            AnchorPane temp = loader.load();
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            ParentContainer.getChildren().clear();
            ParentContainer.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBackToDashboardView(){
        AnchorPane.setTopAnchor(storedMaindashboardView, 0.0);
        AnchorPane.setRightAnchor(storedMaindashboardView, 0.0);
        AnchorPane.setBottomAnchor(storedMaindashboardView, 0.0);
        AnchorPane.setLeftAnchor(storedMaindashboardView, 0.0);
        ParentContainer.getChildren().clear();
        ParentContainer.getChildren().add(storedMaindashboardView);
    }

    // sets the input fxml file to the right-bottom corner of the dashboard
    public FXMLLoader setViewToBox4(String fxmlFilename){
        FXMLLoader loader = Utils.loadFXML(fxmlFilename);
        CentralUser.dashboardController.shutdown();
        try {
            AnchorPane temp = loader.load();
            if(fxmlFilename.equals("MusicDetailedView.fxml")){
                mdvc = loader.getController();
                System.out.println(mdvc);
            }
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            box4Container.getChildren().clear();
            box4Container.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nowOnBox4 = fxmlFilename;
        return loader;
    }

    public void setDashboardViewToBox4(){
        AnchorPane.setTopAnchor(storedBox4, 0.0);
        AnchorPane.setRightAnchor(storedBox4, 0.0);
        AnchorPane.setBottomAnchor(storedBox4, 0.0);
        AnchorPane.setLeftAnchor(storedBox4, 0.0);
        box4Container.getChildren().clear();
        box4Container.getChildren().add(storedBox4);
        if(nowOnBox4.equals("MusicDetailedView.fxml")){
            System.out.println(mdvc);
            if(mdvc!=null){
                mdvc.shutdown();
            }
        }
        nowOnBox4 = "";
    }
    

    public void showMusicSearchResult(String text)  {
        if(text.equals("")){
            searchResult.setVisible(false);
            msc.close.setVisible(false);
            searchResult.getChildren().clear();
        }else{
            searchResult.getChildren().clear();
            ArrayList<MusicModel> mlist = new MusicModel().searchMusic(text);
//            System.out.println(mlist);
            if(mlist.size()>0){
                searchResult.setVisible(true);
                msc.close.setVisible(true);
                int len = Math.min(5, mlist.size());
                for(int i =0;i<len;i++){
                    FXMLLoader loader = Utils.loadFXML("MusicViewCompact.fxml");
                    AnchorPane ap = null;
                    try {
                        ap = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final int I = i;
                    ap.setOnMouseClicked(e -> CentralUser.dashboardController.playMusicInHorizontalPlayer(mlist.get(I)));
                    MusicViewCompactController contr = loader.getController();
                    contr.setMusic(mlist.get(i));
                    searchResult.getChildren().add(ap);
                }
            }

        }
    }

    public void playMusicInHorizontalPlayer(MusicModel music){
        hmp.setNewMedia(music);
    }

}


