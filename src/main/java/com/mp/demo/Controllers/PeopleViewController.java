package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Model.FriendshipModel;
import com.mp.demo.Model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PeopleViewController  implements Initializable {

    @FXML
    private StackPane friendshipBtn;

    @FXML
    private Text usernameContainer;
    @FXML
    private Rectangle btnBehind;
    @FXML
    private Text friendshipStatusBtn;
    private UserModel people;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameContainer.setText("");
        friendshipBtn.setCursor(Cursor.HAND);
        friendshipBtn.setOnMouseEntered(e->{
            btnBehind.setFill(Color.web("#00378a"));
        });

        friendshipBtn.setOnMouseExited(e->{
            btnBehind.setFill(Color.web("#005ce6"));
        });

        friendshipBtn.setOnMouseClicked(e->{
            System.out.println(CentralUser.loggedInUser.id+ " sent friend request to "+ this.people.id);
            FriendshipModel fm = new FriendshipModel(CentralUser.loggedInUser, this.people);
            if(!fm.friendshipExists()){
                fm.establishFriendship();
                btnBehind.setWidth(120);
                friendshipStatusBtn.setText("Cancel Request");
                btnBehind.setFill(Color.web("#ef0107"));
                friendshipBtn.setOnMouseEntered(ev->{
                    btnBehind.setFill(Color.web("#bf0106"));
                });

                friendshipBtn.setOnMouseExited(ev->{
                    btnBehind.setFill(Color.web("#ef0107"));
                });

                friendshipBtn.setOnMouseClicked(mouseEvent -> {
//                    System.out.println("clicked to cancel request from init");
                    fm.deleteFriendship();
                    btnBehind.setWidth(90);
                    friendshipStatusBtn.setText("Add Friend");
                    friendshipBtn.setOnMouseEntered(ev->{
                        btnBehind.setFill(Color.web("#00378a"));
                    });

                    friendshipBtn.setOnMouseExited(ev->{
                        btnBehind.setFill(Color.web("#005ce6"));
                    });
                    new Thread(()->{
                        try {
                            CentralUser.oos.writeObject(this.people.id+"##"+"FR");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                });
                new Thread(()->{
                    try {
                        CentralUser.oos.writeObject(this.people.id+"##"+"FR");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }

        });
    }

    public void changeBtn(String color, UserModel user){
        btnBehind.setWidth(120);
        if(color.equals("GREEN")){
            friendshipStatusBtn.setText("Accept Request");
            btnBehind.setFill(Color.web("#1cac78"));
            friendshipBtn.setOnMouseEntered(ev->{
                btnBehind.setFill(Color.web("#0e563c"));
            });

            friendshipBtn.setOnMouseExited(ev->{
                btnBehind.setFill(Color.web("#1cac78"));
            });

            friendshipBtn.setOnMouseClicked(ev->{
                new FriendshipModel().deletePendingRequest(CentralUser.loggedInUser, this.people);
            });

            friendshipBtn.setOnMouseClicked(mouseEvent -> {

            });
        }else if (color.equals("RED")){
            friendshipStatusBtn.setText("Cancel Request");
            btnBehind.setFill(Color.web("#ef0107"));
            friendshipBtn.setOnMouseEntered(ev->{
                btnBehind.setFill(Color.web("#bf0106"));
            });

            friendshipBtn.setOnMouseExited(ev->{
                btnBehind.setFill(Color.web("#ef0107"));
            });

            friendshipBtn.setOnMouseClicked(mouseEvent -> {
                FriendshipModel fm = new FriendshipModel(CentralUser.loggedInUser, user);
                fm.deleteFriendship();
                btnBehind.setWidth(90);
                friendshipStatusBtn.setText("Add Friend");
                friendshipBtn.setOnMouseEntered(ev->{
                    btnBehind.setFill(Color.web("#00378a"));
                });

                friendshipBtn.setOnMouseExited(ev->{
                    btnBehind.setFill(Color.web("#005ce6"));
                });
                new Thread(()->{
                    try {
                        CentralUser.oos.writeObject(this.people.id+"##"+"FR");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            });
        }
    }
    public void setPeopleData(UserModel user){
        usernameContainer.setText(user.username);
        this.people= user;
    }


}
