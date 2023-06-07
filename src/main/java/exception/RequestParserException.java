package main.java.exception;

import main.java.model.response.HttpStatus;

public class RequestParserException extends Exception {
    private final HttpStatus code;
    private final String reason;

    public RequestParserException(HttpStatus code, String reason) {
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
