package com.subredditstats.subredditstats.models;

public class RegisterRequest extends AccessRequest {
    public RegisterRequest(String username, String password) {
        super(username, password);
    }
}
