package fctreddit.impl.rest;

import java.net.InetAddress;
import java.net.URI;
import java.util.logging.Logger;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import fctreddit.impl.Discovery;

public class ImageServer {

    private static final Logger Log = Logger.getLogger(ImageServer.class.getName());

    static {
		System.setProperty("java.net.preferIPv4Stack", "true");
		System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s\n");
	}

    public static final int PORT = 8081;
    public static final String SERVICE = "Image";
    private static final String SERVER_URI_FMT = "http://%s:%s/rest";

    public static String serverURI;

    public static void main(String[] args) {
        try {
            // Register your JAX-RS resources
            ResourceConfig config = new ResourceConfig();
            config.register(ImageResource.class); // your image REST class

            // Launch HTTP server
            String ip = InetAddress.getLocalHost().getHostAddress();
            serverURI = String.format(SERVER_URI_FMT, ip, PORT);
            JdkHttpServerFactory.createHttpServer(URI.create(serverURI), config);
            
            var discovery = Discovery.getInstance();
            discovery.start();

            Log.info(String.format("%s Server ready @ %s\n", SERVICE, serverURI));

        } catch (Exception e) {
            e.printStackTrace();
            Log.severe(e.getMessage());
        }
    }
}
