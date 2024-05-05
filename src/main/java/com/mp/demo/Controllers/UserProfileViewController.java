package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Model.FriendshipModel;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class UserProfileViewController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private FlowPane favouriteMusicContainer;

    @FXML
    private FlowPane friendsContainer;

    @FXML
    private Text usernameContainer;

    @FXML
    private StackPane logoutbtn;
    @FXML
    private Text btnText;
    @FXML
    private Rectangle btnBehind;
    private ArrayList<UserModel> friends;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameContainer.setText(CentralUser.loggedInUser.username);
        logoutbtn.setCursor(Cursor.HAND);
        logoutbtn.setOnMouseEntered(ev->{
            btnBehind.setFill(Color.web("#bf0106"));
        });
        logoutbtn.setOnMouseExited(ev->{
            btnBehind.setFill(Color.web("#ef0107"));
        });

        logoutbtn.setOnMouseClicked(event1 -> {
            try {
                CentralUser.logOut();
                Utils.setScene("LogInView.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        friends = new FriendshipModel().getAllFriends();
        if(friends!=null){

            for(UserModel x: friends){
                StackPane stackPane = new StackPane();
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                Text uname = new Text(x.username);
                uname.setFont(Font.font("Inter",30));
                uname.setFill(Color.web("#ffffff"));
                StackPane btnStack = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setArcHeight(15);
                rectangle.setArcWidth(15);
                rectangle.setHeight(30);
                rectangle.setWidth(120);
                Text btnText = new Text("Remove Friend");
                Font font = Font.font("Inter", 15);
                btnText.setFont(font);
                btnStack.setCursor(Cursor.HAND);
                rectangle.setFill(Color.web("#121212"));
                stackPane.setOnMouseEntered(e->{
                    rectangle.setFill(Color.web("#005ce6"));
                });

                stackPane.setOnMouseExited(e->{
                    rectangle.setFill(Color.web("#121212"));
                });

                stackPane.setOnMouseClicked(e->{
                    new FriendshipModel().removeFriend(x);
                    stackPane.setVisible(false);
                });
                rectangle.setStyle("-fx-background-color : transparent");
                btnStack.getChildren().add(rectangle);
                btnStack.getChildren().add(btnText);
                vBox.getChildren().addAll(uname,btnStack);
                vBox.setSpacing(10);
                stackPane.getChildren().add(vBox);
                friendsContainer.getChildren().add(stackPane);
            }
        }

    }
    public void goToDashboard() throws IOException {
        CentralUser.dashboardController.goBackToDashboardView();
    }
}
