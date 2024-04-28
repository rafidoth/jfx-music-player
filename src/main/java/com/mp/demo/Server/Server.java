package com.mp.demo.Server;

import java.util.HashMap;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static HashMap<String,SocketConnection> Connections = new HashMap<>();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server started");
        while(true){
            Socket socket = serverSocket.accept();
            new CreateConnection(socket);
            System.out.println("Client Connected");


        }
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
                oos.writeObject("Connection Saved");
                new ServerReader(conn, Connections);
                new ServerWriter(oos, Connections);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
