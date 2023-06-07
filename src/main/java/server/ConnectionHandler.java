package main.java.server;

import main.java.exception.HttpException;
import main.java.exception.RequestParserException;
import main.java.handler.RequestHandler;
import main.java.model.request.RequestPayload;
import main.java.model.response.ResponsePayload;
import main.java.utils.LogUtil;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class mainly has three function:
 * - Parsing a client request and mapping to {@link RequestPayload}
 * - Generating response with help of {@link ResponseGenerator}
 * - Logging the request and response with some info such as IP address and date/time.
 *
 * @author Yoochul Kim.
 */
public class ConnectionHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ConnectionHandler.class.getName());

    // Connection socket for each client
    private final Socket connectedSocket;

    // Reader for a client main.java.model.request
    private BufferedReader bufferedReader;

    // Steam for sending response
    private DataOutputStream dataOutputStream;

    /**
     * Constructor to initialize BufferedReader for reading client request and
     * DataOutputStream for sending response.
     *
     * @param connectedSocket connected client socket.
     */
    public ConnectionHandler(Socket connectedSocket) {
        this.connectedSocket = connectedSocket;
        try {
            dataOutputStream = new DataOutputStream(connectedSocket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            if (LOGGER.getLevel() == Level.FINEST) e.printStackTrace();
        }
    }

    /**
     * Start handling the request, if an error occurs clean up by
     * closing all the streams and socket.
     */
    @Override
    public void run() {
        try {
            startRequestHandling();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "ConnectionHandler::run - error occurred during request handling:" + e);
            e.printStackTrace();
            cleanup();
        }
    }

    /**
     * Process client requests.
     */
    private void startRequestHandling() throws IOException {
        LOGGER.log(Level.INFO, "ConnectionHandler::startRequestHandling BEGIN");
        RequestPayload request = null;
        ResponsePayload response = null;
        try {
            request = parseRequest();
            response = createResponse(request);
            sendResponse(response);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IOException - Error occurred during request handling, ERROR:" + e);
            if (LOGGER.getLevel() == Level.FINEST) e.printStackTrace();
        } catch (HttpException e) {
            LOGGER.log(Level.WARNING, "HttpException - Error occurred during request handling. " +
                    "Code: " + e.getCode() +
                    ", Reason:" + e.getReason());
            if (LOGGER.getLevel() == Level.FINEST) e.printStackTrace();
            /*LOGGER.log(Level.INFO, "Client request: " + request.toLiteString());
            LOGGER.log(Level.INFO, "Server response: " + response.toLiteString());
            LogUtil.printLog(connectedSocket.getInetAddress(), request, response);*/
        }  finally {
            //LOGGER.log(Level.INFO, "Server response: " + response.toLiteString());
            LogUtil.printLog(connectedSocket.getInetAddress(), request, response);
            /*if (response.getResponseLine().getResponseCode().equals(HttpStatus.OK_200)) {
                LogUtil.printLog(connectedSocket.getInetAddress(), request, response);
            } else {
                LogUtil.printLogOnError(connectedSocket.getInetAddress(), request, response);
            }*/
            cleanup();
        }
        LOGGER.log(Level.INFO, "ConnectionHandler::startRequestHandling END...");
    }

    private void sendResponse(ResponsePayload response) throws IOException {
        LOGGER.log(Level.INFO, "ConnectionHandler::sendResponse BEGIN - response: " + response.toLiteString());
        try {
            dataOutputStream.writeBytes(response.getResponseLine().toString());
            if (response.getResponseHeaders() != null) {
                dataOutputStream.writeBytes("\n");
                dataOutputStream.writeBytes(response.getResponseHeaders().toString());
            }

            if (response.getResponseBody() != null) {
                dataOutputStream.writeBytes("\n\n");
                dataOutputStream.write(response.getResponseBody().getBody());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            dataOutputStream.flush();
        }
    }

    /**
     * Parse the request and map to {@link RequestPayload}.
     *
     * The class will not validate the request is correct, yet {@link RequestHandler} will do.
     *
     * Idea from
     * - https://www.ibm.com/docs/en/cics-ts/5.3?topic=protocol-http-requests
     * - https://stackoverflow.com/questions/13255622/parsing-raw-http-request
     *
     * @return RequestPayload parsed from the request string
     */
    private RequestPayload parseRequest() throws IOException {
        LOGGER.log(Level.INFO, "ConnectionHandler::parseRequest BEGIN");

        RequestPayload request = new RequestPayload();
        try {
            request.setRequestLine(bufferedReader.readLine()); // e.g., GET index.html HTTP/1.1
        } catch (RequestParserException e) {
            request.setException(e);
            return request;
        }

        // TODO: Uncomment out below if Header and Body are needed

        /*Hashtable<String, String> headers = new Hashtable<>();

        String header = bufferedReader.readLine();
        while (header.length() > 0) {
            int idx = header.indexOf(":");
            if (idx == -1) {
                // https://serverfault.com/questions/894426/http-status-code-to-signal-bad-or-missing-host-header
                throw new HttpException(ResponseStatusCode.BAD_REQUEST_400, "Invalid Header Parameter: " + header);
            }
            headers.put(header.substring(0, idx), header.substring(idx + 1, header.length()));
            header = bufferedReader.readLine();
        }

        request.setHeaders(headers);

        String header = bufferedReader.readLine();
        while (header.length() > 0) {
            request.appendHeaderParameter(header);
            header = bufferedReader.readLine();
        }

        StringBuffer bodyLine = new StringBuffer(bufferedReader.readLine());
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            bodyLine.append(str).append("\r\n");
        }
        request.setRequestBody(bodyLine);

        String bodyLine = bufferedReader.readLine();
        while (bodyLine != null) {
            request.appendMessageBody(bodyLine);
            bodyLine = bufferedReader.readLine();
        }*/
        return request;
    }

    /**
     * Pass the response generation work to the ResponsePayload.
     */
    private ResponsePayload createResponse(RequestPayload request) throws IOException, HttpException {
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return responseGenerator.createResponse(request);
    }

//    private void logRequest(Socket connectedSocket, RequestPayload request) {
//        String clientIPAddress = connectedSocket.getInetAddress().toString();
//        LogUtil.printLog(connectedSocket.getInetAddress(), response, request.getMethod().toString(), request.getPath());
//    }

    /**
     * Closes all stream, reader, and socket.
     */
    private void cleanup() {
        LOGGER.log(Level.INFO, "... cleaning up ... ");
        try {
            bufferedReader.close();
            dataOutputStream.close();
            connectedSocket.close();
        } catch (IOException ioe) {
            LOGGER.log(Level.INFO, "ConnectionHandler - Error occurred during clean up: " + ioe.getMessage());
        }
    }
}
