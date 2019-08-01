package com.subredditstats.subredditstats.dtos;

public class LoginStatus {
    private boolean success;
    private String token;
    private String reason;

    public LoginStatus() {
    }

    public LoginStatus(boolean success, String token, String reason) {
        this.success = success;
        this.token = token;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
