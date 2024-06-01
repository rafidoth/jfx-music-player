package com.mp.demo.Controllers;

import com.mp.demo.CentralUser;
import com.mp.demo.Model.MessageModel;
import com.mp.demo.Model.MusicModel;
import com.mp.demo.Model.NowPlayingModel;
import com.mp.demo.Model.UserModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatViewController implements Initializable {

    @FXML
    private Rectangle rectangle;

    @FXML
    private Rectangle rectangle1;

    @FXML
    private Text text;

    @FXML
    private Text text1;

    @FXML
    private Text usernameContainer;

    @FXML
    public StackPane sendbtn;

    @FXML
    private TextArea message;
    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Text nowmusic;
    private ScheduledExecutorService chatScheduler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatScheduler = Executors.newSingleThreadScheduledExecutor();
        chatUpdater();
    }

    public void chatUpdater(){
        Runnable updateTask= ()->{
            if(ChatViewTracker.friend!=null){
                updateChatView(ChatViewTracker.friend);
            }
        };
        chatScheduler.scheduleAtFixedRate(updateTask,0,1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        if(chatScheduler!=null && !chatScheduler.isShutdown()){
            chatScheduler.shutdown();
        }
    }

    public void updateChatView(UserModel friend){
        Platform.runLater(()->{
            vbox.getChildren().clear();
            vbox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
        });
        text.setText(friend.username);
        MusicModel musicModel = new NowPlayingModel().userListeningNow(friend.id);
        if(musicModel!=null){
            nowmusic.setText(musicModel.getTitle());
            nowmusic.setFill(Color.web("#d79921"));
        }
        sendbtn.setOnMouseEntered(e->{
            rectangle1.setFill(Color.web("#00378a"));
        });

        sendbtn.setOnMouseExited(e->{
            rectangle1.setFill(Color.web("#005ce6"));
        });

        sendbtn.setOnMouseClicked(e->{
            if(!message.getText().isEmpty()){
                MessageModel messageModel = new MessageModel(message.getText().trim(), CentralUser.loggedInUser.id, friend.id);
                messageModel.insertMessage();
                message.setText("");
                //adding the message into own view
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setPadding(new Insets(5,20,5,20));
                Text t = new Text();
                Font font = Font.font("Inter SemiBold", 15);
                t.setFont(font);
                t.setText(messageModel.getContent());
                TextFlow tf = new TextFlow(t);
                int len = "adsmhgadsjk dsjkhjdksah sdahjkasdh dsajh".length();
                if(messageModel.getContent().length()> len){
                    tf.setPrefWidth(350);
                }
                tf.setStyle("-fx-background-color : #00378a;"+ "-fx-background-radius: 20px;");
                tf.setPadding(new Insets(5,10,5,10));
                hbox.getChildren().add(tf);
                vbox.getChildren().add(hbox);
                vbox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

                new Thread(()->{
                    try {
                        System.out.println("new thread from chatview controller");
                        CentralUser.oos.writeObject(friend.id+"##"+"CHAT");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        });


        ArrayList<MessageModel> messages = new MessageModel().getMessagesBetweenUsers(friend);
        for(MessageModel x : messages){
//            System.out.println(x.getContent() + "  "+ x.getSender() + "  "+x.getRecipient() + "  "+ x.getTimestamp().toString());
            if(x.getSender().equals(friend.id)){
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPadding(new Insets(5,20,5,20));
                Text t = new Text();
                Font font = Font.font("Inter SemiBold", 15);
                t.setFont(font);
                t.setText(x.getContent());
                t.setTextAlignment(TextAlignment.LEFT);
                TextFlow tf = new TextFlow(t);
                int len = "adsmhgadsjk dsjkhjdksah sdahjkasdh dsajh".length();
                if(x.getContent().length()> len){
                    tf.setPrefWidth(350);
                }
                tf.setStyle("-fx-background-color : #00378a;"+ "-fx-background-radius: 20px;");
                tf.setPadding(new Insets(5,10,5,10));
                hbox.getChildren().add(tf);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vbox.getChildren().add(hbox);

                    }
                });

            }else{
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setPadding(new Insets(5,20,5,20));
                Text t = new Text();
                Font font = Font.font("Inter SemiBold", 15);
                t.setFont(font);
                t.setText(x.getContent());
                TextFlow tf = new TextFlow(t);
                int len = "adsmhgadsjk dsjkhjdksah sdahjkasdh dsajh".length();
                if(x.getContent().length()> len){
                    tf.setPrefWidth(350);
                }
                tf.setStyle("-fx-background-color : #00378a;"+ "-fx-background-radius: 20px;");
                tf.setPadding(new Insets(5,10,5,10));
                hbox.getChildren().add(tf);


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vbox.getChildren().add(hbox);
                    }
                });
            }
        }
    }
}
