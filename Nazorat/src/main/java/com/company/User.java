package com.company;

public class User {
    private String email;
    private String password;
    private String verificationCode;
    private boolean verified;

    public User(String email, String password, String verificationCode) {
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.verified = false; // Default tasdiqlanmagan
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}