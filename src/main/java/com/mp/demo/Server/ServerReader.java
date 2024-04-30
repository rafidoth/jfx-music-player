package com.mp.demo.Server;

import com.mp.demo.Model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerReader implements Runnable{
    SocketConnection conn ;
    ConcurrentHashMap<String,SocketConnection> ConnectionList;
    public ServerReader(SocketConnection conn, ConcurrentHashMap<String,SocketConnection> Connections){
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
                }else if (arr[1].equals("CHAT")){
                    new Thread(()->{
                        try {
                            receiver.oos.writeObject("CHAT");
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
                System.out.println("From Client : "+ conn.userid +"->" + received);
            } catch (IOException | ClassNotFoundException e) {
                if (Server.Connections.containsKey(conn.userid)) {
                    Server.removeDisconnectedClient(conn.userid);
                    new UserModel().updateUserStatus(conn.userid,"offline");
                    new Thread(()->{
                        for (Map.Entry<String, SocketConnection> entry : ConnectionList.entrySet()) {
                            String userId = entry.getKey();
                            SocketConnection socketConnection = entry.getValue();
                            try {
                                socketConnection.oos.writeObject("USER_LIST");
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                                throw new RuntimeException(ex);
                            }
                        }
                    }).start();
//                    Server.changedList.setValue((int) (Math.random() * Integer.MAX_VALUE));
//                    System.err.println("Client " + conn.userid + " disconnected.");
                }
                System.err.println(e.getMessage());
                break;
            }
        }
    }
}
