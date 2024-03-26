package com.mp.demo.Model;

public class UserModel {
    private String id;
    private String username;
    private String password;
    private int completedChapters;

   public UserModel(String id, String username, String password, int completedChapters){
       this.username = username;
       this.password = password;
       this.id = id;
       this.completedChapters = completedChapters;
   }

   public void createAccount(){

   }

}
