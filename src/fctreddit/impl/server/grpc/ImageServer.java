package fctreddit.impl.server.grpc;

import java.net.InetAddress;
import java.util.logging.Logger;

import fctreddit.impl.Discovery;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerCredentials;

public class ImageServer {
public static final int PORT = 9001;

	private static final String GRPC_CTX = "/grpc";
	private static final String SERVICE = "Image";
	private static final String SERVER_BASE_URI = "grpc://%s:%s%s";
	
	private static Logger Log = Logger.getLogger(ImageServer.class.getName());
	
	public static void main(String[] args) throws Exception {
		
		GrpcImageServerStub stub = new GrpcImageServerStub();
		ServerCredentials cred = InsecureServerCredentials.create();
		Server server = Grpc.newServerBuilderForPort(PORT, cred) .addService(stub).build();
		String serverURI = String.format(SERVER_BASE_URI, InetAddress.getLocalHost().getHostAddress(), PORT, GRPC_CTX);

		Discovery discovery = Discovery.getInstance(Discovery.DISCOVERY_ADDR, SERVICE, serverURI);
		discovery.start();

		Log.info(String.format("Image gRPC Server ready @ %s\n", serverURI));
		server.start().awaitTermination();
	}
}

