package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.CentralUser;
import com.mp.demo.Client;
import com.mp.demo.Constants;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordTextField passwordField;
    @FXML
    private Label error;

    public void checkCredentials() throws IOException {
        String username = usernameField.getText();
        String password = Utils.getHashedPassword(passwordField.getPassword());
        UserModel user = new UserModel(username,password);
        UserModel userFromDb = user.isCorrectAccount();
        if(userFromDb!=null){
            CentralUser.loggedInUser = userFromDb;
            Client.connectToSocketServer();
            FXMLLoader loader =  Utils.getLoaderSetScene("Dashboard.fxml");
            CentralUser.dashboardController = loader.getController();
            CentralUser.dashboardController.setProfile();
            System.out.println(userFromDb.username);
            new Thread(()->{
                try {
                    CentralUser.oos.writeObject(CentralUser.loggedInUser.id);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();

        }else{
            error.setText("Wrong credentials. Please check again.");
            error.setTextFill(Color.RED);
        }
    }
    public void goToNewUserView() throws IOException {
        Utils.setScene("NewUserView.fxml");
    }
}
