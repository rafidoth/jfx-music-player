package com.mp.demo.Server;

import com.mp.demo.Model.UserModel;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    static ConcurrentHashMap<String,SocketConnection> Connections = new ConcurrentHashMap<>();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server started");
        while(true){
            Socket socket = serverSocket.accept();
            new CreateConnection(socket);
            System.out.println("Client Connected");


        }
    }

    public static ArrayList<String> getConnectedUsers() {
        ArrayList<String> connectedUsers = new ArrayList<>();
        for (Map.Entry<String, SocketConnection> entry : Connections.entrySet()) {
            connectedUsers.add(entry.getKey());
        }
        return connectedUsers;
    }

    public static void removeDisconnectedClient(String userid) {
        Connections.remove(userid);
    }

    static class CreateConnection implements Runnable{
        Socket clientSocket;
        String userid;
        public CreateConnection(Socket clientSocket){
            this.clientSocket = clientSocket;
            new Thread(this).start();
        }
        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                oos.writeObject("Confirmation from server.");
                Object UserId = ois.readObject();
                userid = (String) UserId;
                System.out.println( userid + " joined the server");
                System.out.println("Now Connected Clients : "+ Connections.size());
                SocketConnection conn = new SocketConnection(clientSocket,ois,oos, userid);
                Connections.put(userid,conn);
                new UserModel().updateUserStatus(userid,"online");
                new Thread(()->{
                    for (Map.Entry<String, SocketConnection> entry : Connections.entrySet()) {
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
                oos.writeObject("Connection Saved");
                new ServerReader(conn, Connections);
//                new ServerWriter(oos, Connections);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
