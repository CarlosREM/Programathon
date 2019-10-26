package com.programathon.app_programathon.model;

public class ModelLogin {

    public String userDNI;
    public String password;

    public ModelLogin(String user, String pw){
        this.userDNI = user;
        this.password = pw;
    }
}
