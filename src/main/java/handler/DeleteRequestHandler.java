package main.java.handler;


import main.java.config.Configuration;
import main.java.exception.HttpException;
import main.java.model.request.RequestPayload;
import main.java.model.response.ResponsePayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * If a client request comes with DELETE method, this class will be picked up.
 *
 * @author Yoochul Kim.
 */
public class DeleteRequestHandler extends RequestHandler {

    /**
     * Constructor to create DeleteRequestHandler with
     * given requestDTO: this object includes request
     * information of the client request.
     *
     * @param request of client
     */
    public DeleteRequestHandler(RequestPayload request) throws HttpException {
        super(request);
    }

    /**
     * Delete the request file and generate response.
     */
    @Override
    public void prepareResponse() throws IOException {
        super.prepareResponse();
        deleteFile(request.getPath());
    }

    private void deleteFile(Path path) {
        Path filePath = Paths.get(Configuration.getWebDirectoryPath() + path.toString());
        filePath.toFile().delete();
    }

    @Override
    public ResponsePayload getResponse() {
        return response;
    }
}
