package com.example.android.finalproject.model;

import java.util.List;

public class CategoryProductModel {
    boolean success;
    String message;
    List<CategoryProduct> result;

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

    public List<CategoryProduct> getResult() {
        return result;
    }

    public void setResult(List<CategoryProduct> result) {
        this.result = result;
    }
}
