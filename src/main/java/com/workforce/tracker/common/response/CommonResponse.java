package com.workforce.tracker.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(T data){
        return new CommonResponse<>(true,"Success",data);
    }

    public static <T> CommonResponse<T> failure(String message){
        return new CommonResponse<>(false,message,null);
    }
}
