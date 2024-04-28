package com.mp.demo;

import com.mp.demo.Controllers.DashboardController;
import com.mp.demo.Model.UserModel;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CentralUser {
    public static UserModel loggedInUser;
    public static DashboardController dashboardController;
    public static Socket socket;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public static SimpleIntegerProperty listenChangeFR = new SimpleIntegerProperty(0);



}
