package com.imchatserver.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code; // 状态码
    private String message; // 返回消息
    private T data; // 返回数据

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200,  "Successful operation", data);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}
