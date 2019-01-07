package com.face.facepay.pojo;

import com.face.facepay.enums.ResponseCode;

public class PostResponse {
    private int status = 0;
    private String message = "sucess";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public PostResponse error(int code){
        this.setStatus(code);
        this.setMessage(ResponseCode.valueOf(code).getMessage());
        return this;
    }
}
