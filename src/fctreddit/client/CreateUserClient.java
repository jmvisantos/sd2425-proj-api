package fctreddit.client;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.api.java.Result;
import fctreddit.client.grpc.GrpcUsersClient;
import fctreddit.client.java.UsersClient;
import fctreddit.client.rest.RestUsersClient;

public class CreateUserClient {

	private static Logger Log = Logger.getLogger(CreateUserClient.class.getName());
	
	public static void main(String[] args) throws IOException {
		
		if( args.length != 5) {
			System.err.println( "Use: java " + CreateUserClient.class.getCanonicalName() + " url userId fullName email password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String fullName = args[2];
		String email = args[3];
		String password = args[4];
		
		User usr = new User( userId, fullName, email, password);
		
		UsersClient client = null;
		
		if(serverUrl.endsWith("rest"))
			client = new RestUsersClient( URI.create( serverUrl ) );
		else
			client = new GrpcUsersClient( URI.create( serverUrl) );
		
		Result<String> result = client.createUser( usr );
		if( result.isOK()  )
			Log.info("Created user:" + result.value() );
		else
			Log.info("Create user failed with error: " + result.error());

	}
	
}
