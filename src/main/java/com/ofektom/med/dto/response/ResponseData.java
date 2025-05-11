package com.ofektom.med.dto.response;


public class ResponseData {
    private UserResponse user;

    public ResponseData(UserResponse user) {
        this.user = user;
    }

    public ResponseData() {
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}