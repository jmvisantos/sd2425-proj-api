package fctreddit.clients;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import fctreddit.api.rest.RestUsers;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class DeleteUserClient {

	public static void main(String[] args) throws IOException {
		
		if( args.length != 3) {
			System.err.println( "Use: java " + CreateUserClient.class.getCanonicalName() + " url userId password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String password = args[2];
		
		System.out.println("Sending request to server.");
		
		//TODO: complete this client code
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target( serverUrl ).path( RestUsers.PATH );

		Response r = target.path( userId ).path( RestUsers.USER_ID )
				.queryParam(RestUsers.PASSWORD, password).request()
				.delete();

		if( r.getStatus() == Response.Status.OK.getStatusCode() )
			System.out.println("Success, user deleted.");
		else
			System.out.println("Error, HTTP error status: " + r.getStatus() );
	}
	
}
