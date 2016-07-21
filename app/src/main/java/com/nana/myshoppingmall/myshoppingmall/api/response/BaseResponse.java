package com.nana.myshoppingmall.myshoppingmall.api.response;

/**
 * Created by user on 21/07/2016.
 */
public class BaseResponse {
    private String message;
    private String status;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
