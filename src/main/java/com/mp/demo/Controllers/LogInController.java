package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.Constants;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
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

    public void checkCredentials(){
        String username = usernameField.getText();
        String password = Utils.getHashedPassword(passwordField.getPassword());
        UserModel user = new UserModel(username,password);
        if(user.isCorrectAccount()){
            System.out.println("switch to a scene");
        }else{
            error.setText("Wrong credentials. Please check again.");
            error.setTextFill(Color.RED);
        }
    }
    public void goToNewUserView() throws IOException {
        Utils.setScene("NewUserView.fxml", Constants.Screen2width, Constants.Screen2height,"Log In");
    }
}
