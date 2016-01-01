package com.akgund.chronos.core.impl;

public class UserHomeProvider {

    public String getUserHome() {
        return System.getProperty("user.home");
    }
}
