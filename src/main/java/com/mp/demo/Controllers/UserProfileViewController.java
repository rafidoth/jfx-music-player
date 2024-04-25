package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileViewController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private FlowPane favouriteMusicContainer;

    @FXML
    private FlowPane friendsContainer;

    @FXML
    private Text usernameContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameContainer.setText(CentralUser.loggedInUser.username);

    }

    public void goToDashboard() throws IOException {
        CentralUser.dashboardController.goBackToDashboardView();
    }
}
