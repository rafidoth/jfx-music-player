package com.mp.demo.Server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerWriter implements Runnable{
    ObjectOutputStream oos;
    HashMap<String,SocketConnection> ConnectionList;

    public ServerWriter(ObjectOutputStream oos, HashMap<String,SocketConnection> ConnectionList){
        this.oos = oos;
        this.ConnectionList= ConnectionList;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        while(true){
            try {
                oos.writeObject(in.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
