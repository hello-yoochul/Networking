package main.java.config;

import java.nio.file.Path;

/**
 * This class holds general web configuration information such as
 *  directory path, port, maximum client connection, etc.
 *
 *  Available ports:
 *  Ports 0 through 1023 are defined as well-known ports. Registered ports are from 1024 to 49151.
 *  The remainder of the ports from 49152 to 65535 can be used dynamically by applications.
 *
 * @author Yoochul Kim.
 */
public class Configuration {
    /**
     * minimum port number
     */
    public static final int MIN_PORT_NUMBER = 49153;
    /**
     * maximum port number
     */
    public static final int MAX_PORT_NUMBER = 65353;
    /**
     * available connection (thread limit)
     */
    public static final int MAX_NUMBER_OF_CLIENT_CONNECTION = 2;
    /**
     * web service port
     */
    private static int webServerPort;
    /**
     * web service directory
     */
    private static Path webDirectoryPath;

    /**
     * Get the web service resource path.
     *
     * @return path of web service
     */
    public static Path getWebDirectoryPath() {
        return webDirectoryPath;
    }
    /**
     * Set the web service resource path.
     *
     * @param webDirectoryPath path of web service
     */
    public static void setWebDirectoryPath(Path webDirectoryPath) {
        Configuration.webDirectoryPath = webDirectoryPath;
    }
    /**
     * Get the web service port.
     *
     * @return port of web service
     */
    public static int getWebServerPort() {
        return webServerPort;
    }
    /**
     * Set the web service port.
     *
     * @param webServerPort port of web service
     */
    public static void setWebServerPort(int webServerPort) {
        Configuration.webServerPort = webServerPort;
    }
}
