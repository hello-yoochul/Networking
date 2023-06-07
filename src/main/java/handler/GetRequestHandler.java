package main.java.handler;

import main.java.exception.HttpException;
import main.java.model.request.RequestPayload;
import main.java.model.response.ResponseBody;
import main.java.model.response.ResponsePayload;

import java.io.IOException;

/**
 * If a client request comes with GET method, this class will be picked up.
 *
 * @author Yoochul Kim
 */
public class GetRequestHandler extends RequestHandler {
    ResponseBody responseBody;
    /**
     * Constructor to create GetRequestHandler.
     *
     * @param request of client
     */
    public GetRequestHandler(RequestPayload request) throws HttpException {
        super(request);
        responseBody = new ResponseBody();
    }

    /**
     * Generate server response of image or html file.
     */
    public void prepareResponse() throws IOException {
        super.prepareResponse();
        responseBody.setBody(getFile(request.getPath().toFile()));
        response.setResponseBody(responseBody);
    }

    @Override
    public ResponsePayload getResponse() {
        return response;
    }
}
