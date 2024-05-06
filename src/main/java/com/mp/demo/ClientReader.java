package com.mp.demo;

import com.mp.demo.Model.UserModel;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReader implements Runnable{
    ObjectInputStream ois;
    public ClientReader(ObjectInputStream ois){
        this.ois = ois;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            String serverCommand= null;
            Object serverCommandObj = null;
            try {
                serverCommandObj = ois.readObject();
                if(serverCommandObj!=null){
                    serverCommand =(String) serverCommandObj;
                    System.out.println("server command received : " +  serverCommand);
                    if(serverCommand.equals("FR")){
//                    System.out.println("Update ready receive");
                        CentralUser.listenChangeFR.setValue((int) (Math.random() * Integer.MAX_VALUE));
//                    System.out.println("Update received done ->" + CentralUser.listenChangeFR.getValue());
//                    CentralUser.dashboardController.updatePendingRequest();
                    }else if (serverCommand.equals("CHAT")){
                        CentralUser.listenChangeCHAT.setValue((int) (Math.random() * Integer.MAX_VALUE));
                    }else if (serverCommand.equals("USER_LIST")){
                        CentralUser.onlineFriends = new UserModel().getOnlineUsers();
                        CentralUser.listenChangeUSER_LIST.setValue((int) (Math.random() * Integer.MAX_VALUE));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                break;
//                e.printStackTrace();
            }
        }
    }
}
