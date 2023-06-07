package main.java.model.request;

import java.util.HashMap;
import java.util.Map;

/**
 *  HTTP Method enum Class.
 *
 * @author 200012756
 */
public enum HttpMethod {
    /**
     * Http Get Method.
     */
    GET,
    /**
     * Http Get Method.
     */
    HEAD,
    /**
     * Http Get Method.
     */
    DELETE;

    /**
     * TODO: Implement POST, PUT, PATCH, OPTIONS, CONNECT, TRACE
     */

    /**
     * Check if request method is contained in the enum.
     *
     * @param method request method will be checked
     * @return true if it contains the parameter
     */
    public static boolean contains(String method) {
        // TODO: Change to static to avoid the operation everytime the request comes
        for (HttpMethod c : HttpMethod.values()) {
            if (c.name().equals(method)) {
                return true;
            }
        }
        return false;
    }


//    public final int value;
//    private static Map<String, HttpMethod> map = new HashMap<>();
//
//    Type(int value) {
//        this.value = value;
//    }
//
//    static {
//        for (HttpMethod method : HttpMethod.values()) {
//            map.put(method.value, method);
//        }
//    }
//
//    public static Type valueOf(int value) {
//        return map.get(value);
//    }
//
//    public int getValue() {
//        return value;
//    }

}
