package com.workforce.tracker.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {

    private String message;
    private T data;

    public static <T> CommonResponse<T> success(T data){
        return new CommonResponse<>("Success",data);
    }

    public static <T> CommonResponse<T> failure(String message){
        return new CommonResponse<>(message,null);
    }
}
