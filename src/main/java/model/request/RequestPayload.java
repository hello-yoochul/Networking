package main.java.model.request;

import main.java.config.Configuration;
import main.java.exception.HttpException;
import main.java.exception.RequestParserException;
import main.java.model.response.HttpStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Request line, headers, body will be stored here.
 *
 * @author Yoochul Kim
 */
public class RequestPayload {
    Logger LOGGER = Logger.getLogger(RequestPayload.class.getName());
    private static int VALID_REQUEST_LINE_CHUNK_COUNT = 3;

    private RequestLine requestLine;
    // TODO: RequestHeader RequestBody 객체로 바꾸기
    private Hashtable<String, String> requestHeaders;
    private StringBuffer requestBody;
    private RequestParserException exception;
    private String requestLineForLogging;

    public RequestPayload() {
    }

    /**
     * Set the request line.
     *
     * Request line consists of 3 items:
     *  1. A method. The method is a one-word command that tells the server what it should do with the resource.
     *  2. The path component of the URL for the request. The path identifies the resource on the server.
     *  3. The HTTP version number, showing the HTTP specification to which the client has tried to make the message comply.
     *
     * @param requestLine e.g. "GET /software/htp/cics/index.html HTTP/1.1"
     */
    public void setRequestLine(String requestLine) throws RequestParserException {
        requestLineForLogging = requestLine;
        LOGGER.log(Level.INFO, "RequestPayload::setRequestLine BEGIN - request: " + requestLine);
        // TODO: validate the request line
        String[] splitRequestLine = requestLine.split(" ");

        if (splitRequestLine.length != VALID_REQUEST_LINE_CHUNK_COUNT) {
            throw new RequestParserException(HttpStatus.BAD_REQUEST_400, "Invalid request line");
        }

        if (!isAvailableHttpMethod(splitRequestLine[0])) {
            throw new RequestParserException(HttpStatus.NOT_IMPLEMENTED_501, "Invalid protocol");
        } else if (!isValidPath(Paths.get(splitRequestLine[1]))) {
            throw new RequestParserException(HttpStatus.NOT_FOUND_404, "Requested path doesn't exist");
        } else if (!isProtocolSupported(splitRequestLine[2])) {
            throw new RequestParserException(HttpStatus.BAD_REQUEST_400, "Invalid protocol");
        }

        this.requestLine = new RequestLine();
        this.requestLine.setMethod(HttpMethod.valueOf(splitRequestLine[0]));
        this.requestLine.setPath(Paths.get(splitRequestLine[1]));
        this.requestLine.setProtocol(splitRequestLine[2]);
    }

    /**
     * Set the header.
     *
     * @param headers e.g. "Accept-Language: fr, de", "User-Agent: Mozilla/4.0", "Connection: Keep-Alive"
     */
    public void setHeaders(Hashtable<String, String> headers) {
        this.requestHeaders = headers;
    }

    /**
     * Set the request body.
     *
     * Message bodies are appropriate for some request methods and inappropriate for others.
     * For example, a request with the POST method, which sends input data to the server, has a
     * message body containing the data. A request with the GET method, which asks the server to
     * send a resource, does not have a message body.
     *
     * @param requestBody the entity body is the actual content of the message
     */
    public void setRequestBody(StringBuffer requestBody) {
        this.requestBody = requestBody;
    }

    public void setException(RequestParserException exception) {
        this.exception = exception;
    }

    /**
     * @return the http method extracted from the request line.
     */
    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    /**
     * @return the static resource path extracted from the request line.
     */
    public Path getPath() {
        return requestLine.getPath();
    }

    /**
     * @return the protocol extracted from the request line.
     */
    public String getProtocol() {
        return requestLine.getProtocol();
    }

    /**
     * @return the request line.
     */
    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestParserException getException() {
        return exception;
    }

    /**
     * Append the one among header lines to build the header map table.
     *
     * @param header the one among header lines.
     */
    public void appendHeaderParameter(String header) throws HttpException {
        int idx = header.indexOf(":");
        if (idx == -1) {
            // https://serverfault.com/questions/894426/http-status-code-to-signal-bad-or-missing-host-header
            throw new HttpException(HttpStatus.BAD_REQUEST_400, "Invalid Header Parameter: " + header);
        }
        requestHeaders.put(header.substring(0, idx), header.substring(idx + 1, header.length()));
    }

    /**
     * Append the one among body lines to build the body message.
     *
     * @param bodyLine the one among body lines.
     */
    public void appendMessageBody(String bodyLine) {
        requestBody.append(bodyLine).append("\r\n");
    }

    /**
     * @param httpMethod of the request
     * @return true if protocol is available.
     */
    private boolean isAvailableHttpMethod(String httpMethod) {
        return HttpMethod.contains(httpMethod);
    }

    /**
     * @param protocol of the request
     * @return true if protocol is supported.
     */
    private boolean isProtocolSupported(String protocol) {
        return protocol.equals("HTTP/1.1") || protocol.equals("HTTP/1.0");
    }

    /**
     * @param path of the request
     * @return true if path is valid.
     */
    private boolean isValidPath(Path path) {
        return Files.exists(
                Paths.get(
                        Configuration.getWebDirectoryPath() + path.toString()
                )
        );
    }

    public String getRequestLineForLogging() {
        return requestLineForLogging;
    }

    public String toLiteString(){
        return requestLine.toString();
    }

}
