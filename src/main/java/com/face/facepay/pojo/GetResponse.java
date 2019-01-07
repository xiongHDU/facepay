package com.face.facepay.pojo;

import com.face.facepay.enums.ResponseCode;

public class GetResponse<T> extends PostResponse{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public GetResponse<T> error(int code){
        this.setStatus(code);
        this.setMessage(ResponseCode.valueOf(code).getMessage());
        data = null;
        return this;
    }
    public GetResponse<T> sucess(T data){
        this.setData(data);
        return this;
    }
}
