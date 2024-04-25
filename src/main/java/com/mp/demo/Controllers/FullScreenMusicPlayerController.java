package com.mp.demo.Controllers;

import com.mp.demo.App;
import com.mp.demo.Constants;
import com.mp.demo.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FullScreenMusicPlayerController implements Initializable {

    @FXML
    private ImageView backgroundImage;
    @FXML
    private AnchorPane Container;
    @FXML
    private AnchorPane playerContainer;
    @FXML
    private AnchorPane transparentPane;
    private Rectangle rectangle ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
