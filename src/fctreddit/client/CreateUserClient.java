package fctreddit.client;

import fctreddit.api.User;
import fctreddit.client.rest.RestUsersClient;

import java.net.URI;
import java.util.logging.Logger;

public class CreateUserClient {

	private static Logger Log = Logger.getLogger(CreateUserClient.class.getName());
	
	public static void main(String[] args) {
		
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
		
		RestUsersClient client = new RestUsersClient( URI.create( serverUrl ) );
		
		var response = client.createUser( usr );
		if( response.isOK()  )
			Log.info("Created user:" + response.value() );
		else {
			Log.info("Create user failed with error: " + response.error());
		}
	}
	
}
