package main.java.model.response;

/**
 * An HTTP response contains:
 *  1. A status line.
 *  2. A series of HTTP headers, or header fields.
 *  3. A message body, which is usually needed.
 */
public class ResponsePayload {

    public static final String HEADER_CONTENT_TYPE = "Content-Type"; // e.g., text/html
    public static final String HEADER_DATE = "Date"; // e.g., timeout=15, max=100
    public static final String HEADER_SERVER_NAME = "Server-Name";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length"; // e.g., 35
    public static final String HEADER_CONNECTION = "Connection"; // e.g., Keep-Alive
    public static final String HEADER_KEEP_ALIVE = "Keep-Alive"; // e.g., timeout=15, max=100

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;


    public ResponsePayload() {

    }

    public void setResponseHeaders(ResponseHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseLine(ResponseLine responseLine) {
        this.responseLine = responseLine;
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }


    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    /**
     * @return only the response line and the header, i.e., except for the body
     */
    public String toLiteString(){
        return responseLine.toString();
    }

    // ------------------------------ BUILDER --------------------------------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ResponseLine responseLine;
        private ResponseHeaders responseHeaders;
        private ResponseBody responseBody;

        private Builder() {
        }

        public Builder responseLine(ResponseLine responseLine) {
            this.responseLine = responseLine;
            return this;
        }

        public Builder responseHeaders(ResponseHeaders responseHeaders) {
            this.responseHeaders = responseHeaders;
            return this;
        }

        public Builder responseBody(ResponseBody responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public ResponsePayload build() {
            ResponsePayload responsePayload = new ResponsePayload();
            responsePayload.responseLine = this.responseLine;
            responsePayload.responseHeaders = this.responseHeaders;
            responsePayload.responseBody = this.responseBody;
            return responsePayload;
        }
    }

    /*public ResponsePayload(Builder builder) {
        responseLine = builder.responseLine;
        responseHeaders = builder.responseHeaders;
        responseBody = builder.responseBody;
    }

    public static class Builder {

        private ResponseLine responseLine;
        private ResponseHeaders responseHeaders;
        private ResponseBody responseBody;

        private String responseLine;
        private Hashtable<String, String> responseHeaders;
        private StringBuffer responseBody;

        public Builder method(String responseLine) {
            this.responseLine = responseLine;
            return this;
        }

        public Builder path(Hashtable<String, String> responseHeaders) {
            this.responseHeaders = responseHeaders;
            return this;
        }

        public Builder protocol(StringBuffer responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public ResponsePayload build(){
            return new ResponsePayload(this);
        }
    }*/
}
