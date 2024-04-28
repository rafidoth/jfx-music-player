package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
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
    }
    public void goToDashboard() throws IOException {
        CentralUser.dashboardController.goBackToDashboardView();
    }
}
