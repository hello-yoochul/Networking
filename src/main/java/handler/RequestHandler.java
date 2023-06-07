package main.java.handler;

import main.java.config.Configuration;
import main.java.exception.HttpException;
import main.java.model.request.RequestPayload;
import main.java.model.response.HttpStatus;
import main.java.model.response.ResponseHeaders;
import main.java.model.response.ResponseLine;
import main.java.model.response.ResponsePayload;
import main.java.server.WebServer;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.logging.Logger;

import static main.java.model.response.ResponsePayload.*;

/**
 * Interface for HEAD, DELETE, GET handler.
 *
 * TODO
 * 공통 로직 (GET localhost:12345/index.html)
 *      1. HTTP 메서드 유효성 확인
 *      2. 요청한 파일 존재 유무 확인
 *      3. 프로토콜 지원 여부 확인
 *
 *
 * @author Yoochul Kim
 */
public abstract class RequestHandler {
    Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());
    protected RequestPayload request;
    protected ResponsePayload response;

    /**
     * Constructor to create RequestHandler
     */
    protected RequestHandler(RequestPayload request) throws HttpException {
        if(!isProtocolCorrect(request.getProtocol())){
            throw new HttpException(HttpStatus.BAD_REQUEST_400, "Invalid protocol");
        } else if(!isPathCorrect(request.getPath())) {
            throw new HttpException(HttpStatus.NOT_FOUND_404, "Invalid path");
        }
        this.request = request;
        response = new ResponsePayload();
    }

    /**
     * Generates response.
     */
    public void prepareResponse() throws IOException {
//        ResponseLine responseLine = new ResponseLine();
//        responseLine.setResponseCode(HttpStatus.OK_200);
//        responseLine.setHttpVersion(request.getProtocol());

        // Validation has already done in this constructor so always OK_200 at this point

        response.setResponseLine(
                ResponseLine.builder()
                        .responseCode(HttpStatus.OK_200)
                        .httpVersion(request.getProtocol())
                        .build()
        );
        response.setResponseHeaders(createHeaders());
    }

    public abstract ResponsePayload getResponse();

    /**
     * https://www.ibm.com/docs/en/ibm-mq/9.0?topic=headers-content-length-http-entity-header
     * The Content-Length is optional in an HTTP request. For a GET or DELETE the
     * length must be zero. For POST, if Content-Length is specified and it does
     * not match the length of the message-line, the message is either truncated,
     * or padded with nulls to the specified length.
     *
     * @return
     */
    private ResponseHeaders createHeaders() {
        Hashtable<String, String> responseHashTable = new Hashtable<>();
        responseHashTable.put(HEADER_DATE, LocalDateTime.now().toString());
        responseHashTable.put(HEADER_SERVER_NAME, WebServer.SERVER_NAME);
        //responseHashTable.put(HEADER_CONTENT_TYPE, getContentType(request.getPath()));
        //responseHashTable.put(HEADER_CONTENT_LENGTH, getContentLength(request.getPath()));;
        return new ResponseHeaders(responseHashTable);
    }

    /**
     * Get byte of the file (image or html).
     *
     * Sending byte was chosen over Base64.
     * https://stackoverflow.com/questions/4853886/byte-array-versus-base-64-string-in-restful-web-service
     *
     * How to read a file in Java
     * https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
     * 1. Files.readString() – Java 11
     * 2. Files.lines() – Java 8
     * 3. Files.readAllBytes() – Java 7
     * 4. BufferedReader – Java 6
     * 5. Scanner – Java 5
     *
     * @param requestFile of client
     * @return
     * @throws IOException
     */
    protected byte[] getFile(File requestFile) throws IOException {
        return Files.readAllBytes(Paths.get(Configuration.getWebDirectoryPath() + requestFile.toString()));
    }

    /**
     * Get the content type.
     * e.g. text/html and image/jpeg
     *
     * @param resourcePath of the request
     * @return the content type of the request path
     */
    protected String getContentType(Path resourcePath) {
        // Files.probeContentType doesn't work on Mac OS, so used MimetypesFileTypeMap...
        // https://www.baeldung.com/java-file-mime-type
        Path path = Paths.get(Configuration.getWebDirectoryPath().toString() + resourcePath.toString());

        MimetypesFileTypeMap m = null;
        try {
            m = new MimetypesFileTypeMap(path.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return m.getContentType(path.toFile());
    }

    /**
     * Get the content length of requested file.
     *
     * @param filePath of the request
     * @return the content length of the request
     */
    protected String getContentLength(Path filePath) {
        filePath = Paths.get(Configuration.getWebDirectoryPath() + filePath.toString());
        return Files.exists(filePath) ? Long.toString(filePath.toFile().length()) : "0";
    }

    /**
     * Check if the path is correct.
     *
     * @param path of the request
     * @return true if path is correct.
     */
    private boolean isPathCorrect(Path path) {
        return Files.exists(
                Paths.get(
                        Configuration.getWebDirectoryPath() + path.toString()
                )
        );
    }

    /**
     * Check if the request protocol is correct.
     *
     * @param protocol the request protocol information
     * @return true if protocol is correct.
     */
    private boolean isProtocolCorrect(String protocol) {
        return protocol.equals("HTTP/1.1") || protocol.equals("HTTP/1.0");
    }
}
