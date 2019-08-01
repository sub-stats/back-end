package com.subredditstats.subredditstats.dtos;

public class RegistrationStatus {
    private boolean success;
    private String reason;

    public RegistrationStatus() {
    }

    public RegistrationStatus(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
