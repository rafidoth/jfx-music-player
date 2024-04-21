package com.mp.demo.Controllers;

import atlantafx.base.controls.PasswordTextField;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.UUID;

public class NewUserController {
    UserModel newUser ;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordTextField passwordField;
    @FXML
    private Button createAccountBtn;

    public void createAccount() throws SQLException {
        String username = usernameField.getText();
        String password = Utils.getHashedPassword(passwordField.getPassword()) ;
        newUser = new UserModel(UUID.randomUUID().toString(),username, password );
        newUser.createAccount();
    }


}
