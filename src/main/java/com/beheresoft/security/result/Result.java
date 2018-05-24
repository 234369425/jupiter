package com.beheresoft.security.result;

import lombok.Data;

/**
 * @author Aladi
 */
@Data
public class Result {

    private int code;
    private String message;
    private Object data;

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result ok() {
        return new Result(0, null, null);
    }

    public static Result ok(Object o) {
        return new Result(0, null, o);
    }

    public static Result err() {
        return new Result(1, null, null);
    }

    public static Result err(String message) {
        return new Result(1, message, null);
    }

}
