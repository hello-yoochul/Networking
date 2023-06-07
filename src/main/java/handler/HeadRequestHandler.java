package main.java.handler;

import main.java.exception.HttpException;
import main.java.model.request.RequestPayload;
import main.java.model.response.ResponsePayload;

import java.io.IOException;

/**
 * If a client request comes with HEAD method, this class will be picked up.
 *
 * @author Yoochul Kim
 */
public class HeadRequestHandler extends RequestHandler {
    /**
     * Constructor to create HeadRequestHandler.
     *
     * @param request of client
     */
    public HeadRequestHandler(RequestPayload request) throws HttpException {
        super(request);
    }

    /**
     * The handling logic for HEAD request is commonly used for others method (GET, DELETE, ...),
     * so just call the prepareResponse method in {@link RequestHandler}
     *
     * @throws IOException
     */
    @Override
    public void prepareResponse() throws IOException {
        super.prepareResponse();
    }

    @Override
    public ResponsePayload getResponse() {
        return response;
    }
}
