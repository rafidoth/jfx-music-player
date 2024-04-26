package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private AnchorPane mainDashboardView;
    private AnchorPane storedMaindashboardView;

    @FXML
    private AnchorPane searchBarContainer;
    @FXML
    private VBox searchResult;
    @FXML
    private AnchorPane box4;
    private AnchorPane storedBox4;

    ArrayList<MusicModel> musicsList;
    @FXML
    private StackPane profile;

    public HorizontalMusicPlayerController hmp;
    public MusicSearchBarController msc;
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
            setViewToBox4("FindFriendsView.fxml");
        });



        //Friends View
        for(int i =1;i<50;i+=10){
            addFriendView("https://i.pravatar.cc/150?img="+i, i+"fasdf"+i*2);
        }

        musicsList = new MusicModel().getAllMusics();
        System.out.println(musicsList);
        for(int i=0;i<5;i++){
            addMusicView(musicsList.get(i));
        }

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

    public void addFriendView(String url, String name){
        try {
            FXMLLoader loader = Utils.loadFXML("friendView.fxml");
            HBox friendView = loader.load();
            FriendViewController friendViewController = loader.getController();
            friendViewController.setImage(url);
            friendViewController.setName(name);

            //style
            makeButtonFromHBOX(friendView);

            friendsView.getChildren().add(friendView);
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
        hBox.setOnMouseClicked(e -> System.out.println("Button clicked!"));
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

    public void setViewToBox4(String fxmlFilename){
        FXMLLoader loader = Utils.loadFXML(fxmlFilename);
        try {
            AnchorPane temp = loader.load();
            AnchorPane.setTopAnchor(temp, 0.0);
            AnchorPane.setRightAnchor(temp, 0.0);
            AnchorPane.setBottomAnchor(temp, 0.0);
            AnchorPane.setLeftAnchor(temp, 0.0);
            box4.getChildren().clear();
            box4.getChildren().add(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDashboardViewToBox4(){
        AnchorPane.setTopAnchor(storedBox4, 0.0);
        AnchorPane.setRightAnchor(storedBox4, 0.0);
        AnchorPane.setBottomAnchor(storedBox4, 0.0);
        AnchorPane.setLeftAnchor(storedBox4, 0.0);
        box4.getChildren().clear();
        box4.getChildren().add(storedBox4);
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



