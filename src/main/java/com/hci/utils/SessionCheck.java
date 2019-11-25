package com.hci.utils;

import javax.servlet.http.HttpSession;

public class SessionCheck {
    public static boolean isOnline(HttpSession session){
        return session.getAttribute("userId") != null ;
    }
}
