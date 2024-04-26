package com.mp.demo.Server;

import java.util.HashMap;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
public class ChatServer {
    static HashMap<String,SocketConnection> Connections = new HashMap<>();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started");
        while(true){
            Socket socket = serverSocket.accept();
            new CreateConnection(socket);
            System.out.println("Client Connected");
            System.out.println("Now Connected Clients : "+ Connections.size());

        }
    }

    static class CreateConnection implements Runnable{
        Socket clientSocket;
        public CreateConnection(Socket clientSocket){
            this.clientSocket = clientSocket;
            new Thread(this).start();
        }
        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                oos.writeObject("Enter Your Name : ");
                Object clientName = null;
                clientName = ois.readObject();
                System.out.println( (String)clientName + " joined the server");
                SocketConnection conn = new SocketConnection(clientSocket,ois,oos);
                Connections.put(((String) clientName).toLowerCase(),conn);
                oos.writeObject("Connection Saved");
                new ServerReader(conn, Connections);
                new ServerWriter(oos);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
