package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.App;
import com.mp.demo.CentralUser;
import com.mp.demo.Constants;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class NewUserController {
    UserModel newUser ;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordTextField passwordField;

    @FXML
    private Label error;

    public void goToLogInView() throws IOException {
        Utils.setScene("LogInView.fxml");
    }

    public void createAccount() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = Utils.getHashedPassword(passwordField.getPassword()) ;
        newUser = new UserModel(UUID.randomUUID().toString(),username, password );
        boolean usernameAvailable = newUser.isUsernameAvailable();
        System.out.println(usernameAvailable);
        if(usernameAvailable){
            CentralUser.loggedInUser = newUser;
            newUser.createAccount();
            FXMLLoader loader =  Utils.getLoaderSetScene("Dashboard.fxml");
            CentralUser.dashboardController = loader.getController();
            CentralUser.dashboardController.setProfile();
            new Thread(()->{
                try {
                    CentralUser.oos.writeObject(CentralUser.loggedInUser.id);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        }else{
            error.setText("Username already taken! Try another one please.");
            error.setTextFill(Color.RED);
        }

    }


}
