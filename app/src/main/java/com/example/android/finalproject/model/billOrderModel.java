package com.example.android.finalproject.model;

import java.util.List;

public class billOrderModel {
    boolean success;
    String message;
    List<billOrder> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<billOrder> getResult() {
        return result;
    }

    public void setResult(List<billOrder> result) {
        this.result = result;
    }
}
