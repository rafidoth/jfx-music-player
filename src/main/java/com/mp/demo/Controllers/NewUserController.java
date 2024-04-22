package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.App;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
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

    public void createAccount() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = Utils.getHashedPassword(passwordField.getPassword()) ;
        newUser = new UserModel(UUID.randomUUID().toString(),username, password );
        boolean usernameAvailable = newUser.isUsernameAvailable();
        System.out.println(usernameAvailable);
        if(usernameAvailable){
            newUser.createAccount();
            Utils.setStage("MusicPlayerView.fxml",1000,800,"music");

        }else{
            error.setText("Username already taken! Try another one please.");
            error.setTextFill(Color.RED);
        }

    }


}
