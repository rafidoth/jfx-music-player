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

public class PendingPeopleController implements Initializable {
    @FXML
    private StackPane friendshipBtn;

    @FXML
    private StackPane friendshipBtn1;


    @FXML
    private Text usernameContainer;
    @FXML
    private Rectangle btnBehind;
    @FXML
    private Rectangle btnBehind1;
    @FXML
    private Text rejectbtn;
    @FXML
    private Text friendshipStatusBtn1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(UserModel user){
        usernameContainer.setText(user.username);
        friendshipBtn.setCursor(Cursor.HAND);
        friendshipBtn1.setCursor(Cursor.HAND);

        friendshipBtn.setOnMouseClicked(e->{
            //reject
            new FriendshipModel(user, CentralUser.loggedInUser).deleteFriendship();
            new Thread(()->{
                try {
                    CentralUser.oos.writeObject(user.id+"##"+"FR");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        });
        friendshipBtn1.setOnMouseClicked(e->{
            //accept
            new FriendshipModel().acceptFriendshipRequest(user);
            CentralUser.dashboardController.updatePendingRequestCountTopBar();
            CentralUser.dashboardController.updatePendingRequestList();
            CentralUser.dashboardController.updateFriendList();
            new Thread(()->{
                try {
                    CentralUser.oos.writeObject(user.id+"##"+"FR");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        });

        rejectbtn.setText("Reject");
        btnBehind.setFill(Color.web("#ef0107"));
        rejectbtn.setOnMouseEntered(e->{
            btnBehind.setFill(Color.web("#bf0106"));
        });

        rejectbtn.setOnMouseExited(e->{
            btnBehind.setFill(Color.web("#ef0107"));
        });

        friendshipStatusBtn1.setText("Accept");
        btnBehind1.setFill(Color.web("#1cac78"));
        friendshipStatusBtn1.setOnMouseEntered(e->{
            btnBehind1.setFill(Color.web("#0e563c"));
        });
        friendshipStatusBtn1.setOnMouseExited(e->{
            btnBehind1.setFill(Color.web("#1cac78"));
        });

    }
}
