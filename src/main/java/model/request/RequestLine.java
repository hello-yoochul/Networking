package main.java.model.request;

import java.nio.file.Path;

public class RequestLine {
    private HttpMethod method;
    private Path path;
    private String protocol;

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }


    @Override
    public String toString() {
        return method.toString() + " " + path.toString() + " " + protocol.toString();
    }
}
