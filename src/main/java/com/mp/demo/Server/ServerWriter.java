package com.mp.demo.Server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ServerWriter implements Runnable{
    ObjectOutputStream oos;


    public ServerWriter(ObjectOutputStream oos){
        this.oos = oos;
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
