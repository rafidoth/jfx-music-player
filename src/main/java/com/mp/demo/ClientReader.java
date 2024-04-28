package com.mp.demo;

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
            try {
                serverCommand =(String) ois.readObject();
                System.out.println( serverCommand);
                if(serverCommand.equals("FR")){
                    CentralUser.listenChangeFR.setValue((CentralUser.listenChangeFR.get()+1)%5);
//                    CentralUser.dashboardController.updatePendingRequest();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
