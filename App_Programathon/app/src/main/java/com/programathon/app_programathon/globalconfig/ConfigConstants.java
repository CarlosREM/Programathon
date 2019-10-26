package com.programathon.app_programathon.globalconfig;

import java.lang.String;

public class ConfigConstants{
    private static ConfigConstants instance;


    //Constants......................
    private String PREFS_NAME;
    private String API_PORT;
    private String API_IP;
    private String API_URL;
    private String API_LOGIN_URL;
    private String API_SERVER;

    private ConfigConstants(){
        this.PREFS_NAME = "DefaultPrefs";

        this.API_IP = "192.168.128.21";
        this.API_PORT = "8111";
        this.API_SERVER = "ApiServer";
        this.API_URL = "http://"+this.API_IP+":"+this.API_PORT+"/"+this.API_SERVER+"/api/";
        this.API_LOGIN_URL = "https://"+this.API_IP+":"+this.API_PORT+"/api/login";
    }


    public static ConfigConstants getInstance(){
        if(instance == null){
            instance = new ConfigConstants();
        }
        return instance;
    }

    public String getPREFS_NAME() {
        return PREFS_NAME;
    }

    public String getAPI_PORT() {
        return API_PORT;
    }

    public String getAPI_IP() {
        return API_IP;
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public String getAPI_LOGIN_URL() {
        return API_LOGIN_URL;
    }

    public String getAPI_SERVER() {
        return API_SERVER;
    }
}

//Code referenced: https://stackoverflow.com/questions/1944656/android-global-variable