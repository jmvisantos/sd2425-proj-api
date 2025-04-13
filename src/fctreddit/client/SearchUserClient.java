package fctreddit.client;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import fctreddit.client.rest.RestUsersClient;

public class SearchUserClient {

	private static Logger Log = Logger.getLogger(SearchUserClient.class.getName());

	public static void main(String[] args) {
		
		if ( args.length != 2 ) {
			System.err.println( "Use: java " + SearchUserClient.class.getCanonicalName() + " url query" );
			return;
		}
		
		String serverUrl = args[0];
		String query = args[1];
		
		System.out.println("Sending request to server.");

		var client = new RestUsersClient( URI.create(serverUrl) );
		var result =  client.searchUsers(query);

		if( result.isOK() ) {
			@SuppressWarnings("unchecked")
			var userList = (List<String>) result.value();
			Log.info("Found the following users in the database: ");
			userList.forEach(System.out::println);
		} else {
			System.out.println("Error, HTTP error status: " + result.error());
		}
	}
	
}
