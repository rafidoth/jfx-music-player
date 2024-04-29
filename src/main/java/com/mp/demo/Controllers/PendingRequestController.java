package com.mp.demo.Controllers;

import com.mp.demo.Model.FriendshipModel;
import com.mp.demo.Model.UserModel;
import com.mp.demo.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PendingRequestController implements Initializable {
    @FXML
    private VBox PeopleViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updatePendingRequests(){
        Platform.runLater(()->{
            System.out.println("Updating pending requests...");
            ArrayList<UserModel> pendingRequests = new FriendshipModel().getUsersWhoSentMeRequest();
//            System.out.println(pendingRequests);
            PeopleViewContainer.getChildren().clear();
//            System.out.println(PeopleViewContainer.getChildren());
            for(int i = 0;i<Math.min(pendingRequests.size(),8);i++){
                FXMLLoader loader = Utils.loadFXML("PendingPeople.fxml");
                AnchorPane ap;
                try {
                    ap = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                PendingPeopleController controller = loader.getController();
                controller.setData(pendingRequests.get(i));
//                System.out.println("people in pending request added : "+pendingRequests.get(i).username );
                PeopleViewContainer.getChildren().add(ap);
            }
        });
    }
}
