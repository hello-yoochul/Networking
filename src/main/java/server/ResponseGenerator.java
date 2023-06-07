package main.java.server;

import main.java.exception.HttpException;
import main.java.exception.RequestParserException;
import main.java.handler.DeleteRequestHandler;
import main.java.handler.GetRequestHandler;
import main.java.handler.HeadRequestHandler;
import main.java.handler.RequestHandler;
import main.java.model.request.RequestPayload;
import main.java.model.response.ResponseLine;
import main.java.model.response.ResponsePayload;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class ResponseGenerator {
    private static final Logger LOGGER = Logger.getLogger(ResponseGenerator.class.getName());

    /**
     * Generate a response depending on the request method (HEAD, GET, DELETE).
     * TODO: POST 구현하기
     *
     * @param request
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public ResponsePayload createResponse(RequestPayload request) throws HttpException, IOException {
        if (request.getException() != null) {
            RequestParserException exception = request.getException();

//            ResponsePayload response = new ResponsePayload();
//            response = new ResponsePayload();
//            ResponseLine responseLine = new ResponseLine();
//            responseLine.setHttpVersion("HTTP/1.1");
//            responseLine.setResponseCode(exception.getCode());
//            responseLine.setReason(Optional.ofNullable(exception.getReason()));
//            response.setResponseLine(responseLine);

            return ResponsePayload.builder()
                    .responseLine(
                            ResponseLine.builder()
                                    .httpVersion("HTTP/1.1")
                                    .responseCode(exception.getCode())
                                    .reason(Optional.ofNullable(exception.getReason()).toString())
                                    .build()
                    ).build();
        }

        // TODO: Validate the request
        LOGGER.info("ResponseGenerator::createResponse BEGIN");
        RequestHandler requestHandler = null;

        switch (request.getMethod()) {
            case HEAD: { // HEAD index.html
                requestHandler = new HeadRequestHandler(request);
                break;
            }
            case GET: { // GET index.html
                requestHandler = new GetRequestHandler(request);
                break;
            }
            case DELETE: { // DELETE index.html
                requestHandler = new DeleteRequestHandler(request);
                break;
            }
        }

        requestHandler.prepareResponse();

        ResponsePayload response = requestHandler.getResponse();
        LOGGER.info("ResponseGenerator::createResponse END - response: " + response.toLiteString());
        return response;
    }
}
