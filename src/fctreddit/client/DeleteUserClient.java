package fctreddit.client;


import fctreddit.client.rest.RestUsersClient;

import java.net.URI;

public class DeleteUserClient {

	public static void main(String[] args) {
		
		if( args.length != 3) {
			System.err.println( "Use: java " + DeleteUserClient.class.getCanonicalName() + " url userId password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String password = args[2];
		
		System.out.println("Sending request to server.");

		RestUsersClient client = new RestUsersClient( URI.create( serverUrl ) );
		var result = client.deleteUser(userId, password);

		if( result.isOK()  )
			System.out.println("Deleted user:" + result.value() );
		else {
			System.out.println("Delete user failed with error: " + result.error());
		}
	}
	
}
