package com.example.appsale12102022.data.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDTO {
    private Integer result;
    @SerializedName("data")
    @Expose
    private DataDTO dataDTO;
    private String message;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public DataDTO getData() {
        return dataDTO;
    }

    public void setData(DataDTO dataDTO) {
        this.dataDTO = dataDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
