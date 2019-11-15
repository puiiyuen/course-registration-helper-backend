package com.hci.config;

public class configInfo {

    private static final String devApi = "http://localhost:8080";
    private static final String liveApi = "";

    public static String getApi(int mode) {
        if (mode == 0) {
            return devApi;
        } else if (mode == 1){
            return liveApi;
        } else {
            return "Wrong Mode";
        }
    }

}
