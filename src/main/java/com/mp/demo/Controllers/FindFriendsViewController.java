package com.mp.demo.Controllers;
import com.mp.demo.CentralUser;
import com.mp.demo.Model.FriendshipModel;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FindFriendsViewController implements Initializable {
    @FXML
    private VBox PeopleViewContainer;

    @FXML
    private ImageView close;

    @FXML
    private TextField userSearchInput;

    private ArrayList<UserModel> usersISentRequest;
    private ArrayList<UserModel> usersWhoSentMeRequest;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setCursor(Cursor.HAND);
        close.setOnMouseClicked(e->{
            PeopleViewContainer.getChildren().clear();
            userSearchInput.clear();
        });
        userSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue.equals("")){
                PeopleViewContainer.getChildren().clear();
                close.setVisible(false);
           }else{
               PeopleViewContainer.getChildren().clear();
               ArrayList<UserModel> searchResult = new UserModel().searchUser(newValue);
               if(searchResult.size()>0){
                   close.setVisible(true);
                   int len = Math.min(4,searchResult.size());
                   for(int i =0;i<len;i++){
                       if(searchResult.get(i).username.equals(CentralUser.loggedInUser.username)){
                           continue;
                       }
                       FXMLLoader loader = Utils.loadFXML("PeopleView.fxml");
                       AnchorPane ap = null;
                       try {
                           ap = loader.load();
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                       PeopleViewController contr = loader.getController();
                       contr.setPeopleData(searchResult.get(i));

                       ArrayList<UserModel> usersWhoSentMeRequest = new FriendshipModel().getUsersWhoSentMeRequest();

                       for(int j=0;j<usersWhoSentMeRequest.size();j++){
                           if(usersWhoSentMeRequest.get(j).id.equals(searchResult.get(i).id)){
                               contr.changeBtn("GREEN", usersWhoSentMeRequest.get(j));
                           }
                       }
                       ArrayList<UserModel> usersISentRequest = new FriendshipModel().getUsersISentRequest();

                       for(int j=0;j<usersISentRequest.size();j++){
                           if(usersISentRequest.get(j).id.equals(searchResult.get(i).id)){
                               contr.changeBtn("RED",usersISentRequest.get(j));
                           }
                       }
                       ArrayList<UserModel> myFriends = new FriendshipModel().getAllFriends();
//                       for(UserModel x : myFriends) System.out.println(x.username);
                       for(int j=0;j<myFriends.size();j++){
                           if(myFriends.get(j).id.equals(searchResult.get(i).id)){
                               contr.changeBtn("REMOVEFRIEND",myFriends.get(j));
                           }
                       }
                       PeopleViewContainer.getChildren().add(ap);
                   }
               }


           }

        });;
    }


}
