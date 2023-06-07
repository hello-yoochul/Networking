package main.java.model.response;

import java.util.HashMap;
import java.util.Map;

public enum HttpStatus {
    OK_200("200 OK"),
    NO_CONTENT_204("204 No Content"),
    BAD_REQUEST_400("400 Bad Request"),
    NOT_FOUND_404("404 Not Found "),
    NOT_IMPLEMENTED_501("501 Not Implemented ");

    public final String value;
    private static Map<String, HttpStatus> map = new HashMap<>();

    static {
        for (HttpStatus resp : HttpStatus.values()) {
            map.put(resp.value, resp);
        }
    }
    HttpStatus(String value) {
        this.value = value;
    }

    public static HttpStatus valueOf(int value) {
        return map.get(value);
    }

    public String getValue() {
        return value;
    }
}