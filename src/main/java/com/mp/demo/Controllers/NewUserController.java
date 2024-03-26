package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.Model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewUserController {
    UserModel newUser ;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordTextField passwordField;
    @FXML
    private Button createAccountBtn;

    public void createAccount(){
        String username = usernameField.getText();
        String password = passwordField.getPassword();
        System.out.println(username+ " "+ password);
    }


}
