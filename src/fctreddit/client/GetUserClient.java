package fctreddit.client;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import fctreddit.client.rest.RestUsersClient;

public class GetUserClient {
	
	private static Logger Log = Logger.getLogger(GetUserClient.class.getName());


	public static void main(String[] args) throws IOException {
		
		if( args.length != 3) {
			System.err.println( "Use: java " + CreateUserClient.class.getCanonicalName() + " url userId password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String password = args[2];
		
		RestUsersClient client = new RestUsersClient( URI.create( serverUrl ) );

		var response = client.getUser(userId, password);
		if( response.isOK()  )
			Log.info("Get user:" + response.value() );
		else
			Log.info("Get user failed with error: " + response.error());
		
	}
	
}
