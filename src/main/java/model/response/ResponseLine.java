package main.java.model.response;

import java.util.Optional;

/**
 * Status line will consist of HTTP-Version + "/n" + Status-Code + "/n" + Reason-Phrase
 */
public class ResponseLine {
    private String httpVersion;
    private HttpStatus responseCode;
    private Optional<String> reason = Optional.empty();

    public ResponseLine() {
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }

    public void setReason(Optional<String> reason) {
        this.reason = reason;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getResponseCode() {
        return responseCode;
    }

    public Optional<String> getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return httpVersion + " " + responseCode.getValue() + " " + reason.orElse("");
    }

    // ------------------------------ BUILDER --------------------------------

    private ResponseLine(Builder builder) {
        this.httpVersion = builder.httpVersion;
        this.responseCode = builder.responseCode;
        this.reason = builder.reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String httpVersion;
        private HttpStatus responseCode;
        private Optional<String> reason = Optional.empty();

        private Builder() {
            // Default constructor for the builder
        }

        public Builder httpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public Builder responseCode(HttpStatus responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = Optional.ofNullable(reason);
            return this;
        }

        public ResponseLine build() {
            return new ResponseLine(this);
        }
    }

}
