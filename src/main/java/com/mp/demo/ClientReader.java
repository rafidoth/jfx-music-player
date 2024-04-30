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
//                    System.out.println("Update ready receive");
                    CentralUser.listenChangeFR.setValue((int) (Math.random() * Integer.MAX_VALUE));
//                    System.out.println("Update received done ->" + CentralUser.listenChangeFR.getValue());
//                    CentralUser.dashboardController.updatePendingRequest();
                }else if (serverCommand.equals("CHAT")){
                    CentralUser.listenChangeCHAT.setValue((int) (Math.random() * Integer.MAX_VALUE));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
