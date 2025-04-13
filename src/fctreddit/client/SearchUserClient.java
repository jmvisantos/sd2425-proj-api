package fctreddit.client;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import fctreddit.client.rest.RestUsersClient;


public class SearchUserClient {

	private static Logger Log = Logger.getLogger(SearchUserClient.class.getName());

	public static void main(String[] args) {
		
		if( args.length != 2 ) {
			System.err.println( "Use: java " + SearchUserClient.class.getCanonicalName() + " url query" );
			return;
		}
		
		String serverUrl = args[0];
		String query = args[1];
		
		System.out.println("Sending request to server.");

		var client = new RestUsersClient( URI.create(serverUrl) );
		var result =  client.searchUsers(query);

		if( result.isOK() ) {
			var users = result.value();
			Log.info("  ");
		} else {
			System.out.println("Error, HTTP error status: " + result.getError());
		}
	}
	
}
