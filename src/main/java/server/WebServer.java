package main.java.server;

import main.java.config.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the first place getting client's socket connections and
 * continues to serve the clients' requests as long as there are no errors.
 * To support multi-thread, it will manage the number of thread for
 * {@link ConnectionHandler} creation.
 *
 * @author Yoochul Kim.
 */
public class WebServer {
    private Socket connection;

    public static final String SERVER_NAME = "YOOCHUL SERVER";

    private final ExecutorService executorService;

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    /**
     * Constructor to create the web server.
     *
     * As long as there are no errors, it continues
     * to serve the web client request.
     *
     * @throws IOException when socket or server socket connection is out of service
     */
    public WebServer() {
        // To handle multiple requests
        executorService = Executors.newFixedThreadPool(Configuration.MAX_NUMBER_OF_CLIENT_CONNECTION);
    }

    /**
     * Start the webserver to handle incoming clients' requests
     * and continue to provide the service until the server stop.
     *
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(Configuration.getWebServerPort())) {
            // Continue to serve the client request as long as there are no errors
            while (true) {
                LOGGER.log(Level.INFO, "\nWaiting for client request ... ");
                // Wait for client connection (blocking until new connection)
                connection = serverSocket.accept();
                LOGGER.log(Level.INFO, "----------------------------- Server got new connection -------------------------------");
                LOGGER.log(Level.INFO, "Request from " + connection.getInetAddress());

                // Handle the request in a multithreading fashion
                ConnectionHandler wch = new ConnectionHandler(connection);
                executorService.submit(wch); // Call the run() in the ConnectionHandler
            }
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "WebServer: server error occur..., ERROR: " + ioe.getMessage());
            connection.close();
        }
    }
}
