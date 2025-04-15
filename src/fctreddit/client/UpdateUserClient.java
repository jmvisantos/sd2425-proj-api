package fctreddit.clients;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import fctreddit.api.User;
import fctreddit.api.rest.RestUsers;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class UpdateUserClient {

	public static void main(String[] args) throws IOException {
		
		if( args.length != 6) {
			System.err.println( "Use: java " + CreateUserClient.class.getCanonicalName() + " url userId oldpwd fullName email password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String oldpwd = args[2];
		String fullName = args[3];
		String email = args[4];
		String password = args[5];
		
		User usr = new User( userId, fullName, email, password);
		
		System.out.println("Sending request to server.");
		
		//TODO complete this client code
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target( serverUrl ).path( RestUsers.PATH );

		Response r = target.path(userId)
				.queryParam(RestUsers.PASSWORD, oldpwd).request()
				.accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(usr, MediaType.APPLICATION_JSON));

		if( r.getStatus() == Response.Status.NO_CONTENT.getStatusCode() )
			System.out.println("Success.");
		else
			System.out.println("Error, HTTP error status: " + r.getStatus() );
	}
	
}
