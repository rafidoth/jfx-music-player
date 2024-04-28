package com.mp.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void connectToSocketServer() {
        try{
            Socket socket = new Socket("127.0.0.1",9999);
            System.out.println("Client Started");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            CentralUser.socket = socket;
            CentralUser.ois = ois;
            CentralUser.oos = oos;
            String confirmation_msg = (String) ois.readObject();
            System.out.println(confirmation_msg);
            new ClientReader(CentralUser.ois);
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
