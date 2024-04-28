package com.mp.demo.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection {
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Socket socket;
    String userid;

    public SocketConnection(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, String userid){
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.userid = userid;
    }
}
