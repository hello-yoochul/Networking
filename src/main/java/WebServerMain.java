package main.java;

import main.java.config.Configuration;
import main.java.server.WebServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class starts the web server with given directory and path.
 *
 * @author Yoochul Kim.
 */
public class WebServerMain {
    private static final int VALID_ARG_LENGTH = 2;

    /**
     * Start the web server if inputs are correct.
     *
     * Example command to run it:
     *      "java WebServerMain src/main/resources/www 12345"
     *
     * @param args directory and port
     */
    public static void main(String[] args) {
        // accept only 2 argument!
        if (args.length != VALID_ARG_LENGTH) {
            System.out.print("Usage: java WebServerMain <document_root> <port>");
            System.exit(1);
        }
        Path resourcePath = Paths.get(args[0]);
        int port = Integer.parseInt(args[1]);

        if (Files.exists(resourcePath)) {
            Configuration.setWebDirectoryPath(resourcePath);
        } else {
            throw new IllegalArgumentException("Invalid directory: " + resourcePath.toString());
        }

        // port range check, ( 0 ~ 65353 )
        if (Configuration.MIN_PORT_NUMBER < port && port < Configuration.MAX_PORT_NUMBER) {
            Configuration.setWebServerPort(port);
        } else {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        try {
            System.out.println("Starting server with a port number of " + Configuration.getWebServerPort()
                    + " and directory path of '" + Configuration.getWebDirectoryPath() + "'...");
            WebServer webServerMain = new WebServer();
            webServerMain.start();
        } catch (IOException ioe) {
            System.out.println("Error occurred while creating the web server...");
            ioe.printStackTrace();
        }
    }
}
