package com.mp.demo.Server;

import java.io.IOException;
import java.util.HashMap;

public class ServerReader implements Runnable{
    SocketConnection conn ;
    HashMap<String,SocketConnection> ConnectionList;
    public ServerReader(SocketConnection conn, HashMap<String,SocketConnection> Connections){
        this.ConnectionList = Connections;
        this.conn = conn;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            try {
                String received = (String)conn.ois.readObject();
                String[] arr = received.split("##");
                SocketConnection receiver = ConnectionList.get(arr[0]);
                System.out.println(arr[0]);
                if(arr[1].equals("FR")){
                    new Thread(()->{
                        try {
                            receiver.oos.writeObject("FR");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
                System.out.println("From Client : "+ conn.userid +"->" + received);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
