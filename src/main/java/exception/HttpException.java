package main.java.exception;

import main.java.model.response.HttpStatus;

public class HttpException extends Exception {
    private final HttpStatus code;
    private final String reason;

    public HttpException(HttpStatus code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
