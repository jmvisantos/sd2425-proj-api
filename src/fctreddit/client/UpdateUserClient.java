package fctreddit.client;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.client.rest.RestUsersClient;

public class UpdateUserClient {
	private static Logger Log = Logger.getLogger(UpdateUserClient.class.getName());
	public static void main(String[] args) {
		
		if( args.length != 6) {
			System.err.println( "Use: java " + UpdateUserClient.class.getCanonicalName() + " url userId oldPassword fullName email password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String oldPassword = args[2];
		String fullName = args[3];
		String email = args[4];
		String password = args[5];
		
		User usr = new User( userId, fullName, email, password);
			
		RestUsersClient client = new RestUsersClient(URI.create( serverUrl));
		var response = client.updateUser(userId, oldPassword, usr);

		if (response.isOK()) {
			Log.info("Updated user:" + response.value());
		}
		else {
			Log.info("Update user failed with error:" + response.error());
		}

	}
	
}
