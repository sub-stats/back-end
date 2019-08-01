package com.subredditstats.subredditstats.models;

public class LoginRequest extends AccessRequest {
    public LoginRequest(String username, String password) {
        super(username, password);
    }
}
