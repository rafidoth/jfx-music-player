package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PeopleViewController  implements Initializable {

    @FXML
    private StackPane addFriendBtn;

    @FXML
    private Text usernameContainer;
    @FXML
    private Rectangle btnBehind;
    private UserModel people;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameContainer.setText("");
        addFriendBtn.setCursor(Cursor.HAND);
        addFriendBtn.setOnMouseEntered(e->{
            btnBehind.setFill(Color.web("#00378a"));
        });

        addFriendBtn.setOnMouseExited(e->{
            btnBehind.setFill(Color.web("#005ce6"));
        });

        addFriendBtn.setOnMouseClicked(e->{
            System.out.println(CentralUser.loggedInUser.username+ " sent friend request to "+ this.people.username);
        });
    }


    public void setPeopleData(UserModel user){
        usernameContainer.setText(user.username);
        this.people= user;
    }
}
