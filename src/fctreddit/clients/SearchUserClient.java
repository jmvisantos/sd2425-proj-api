package fctreddit.clients;

import java.io.IOException;
import java.util.List;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import fctreddit.api.User;
import fctreddit.api.rest.RestUsers;

public class SearchUserClient {

	public static void main(String[] args) throws IOException {
		
		if( args.length != 2) {
			System.err.println( "Use: java " + CreateUserClient.class.getCanonicalName() + " url query");
			return;
		}
		
		String serverUrl = args[0];
		String query = args[1];
		
		System.out.println("Sending request to server.");
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target( serverUrl ).path( RestUsers.PATH );
		
		Response r = target.path("/").queryParam( RestUsers.QUERY, query).request()
				.accept(MediaType.APPLICATION_JSON)
				.get();

		if( r.getStatus() == Status.OK.getStatusCode() && r.hasEntity() ) {
			List<User> users = r.readEntity(new GenericType<List<User>>() {});
			System.out.println("Success: (" + users.size() + " users)");
			users.stream().forEach( u -> System.out.println( u));
		} else
			System.out.println("Error, HTTP error status: " + r.getStatus() );

	}
	
}
