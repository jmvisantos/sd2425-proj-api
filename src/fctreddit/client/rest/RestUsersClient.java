package fctreddit.client.rest;

import java.net.URI;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import fctreddit.api.User;
import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.api.rest.RestUsers;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


public class RestUsersClient<E> {
	private static Logger Log = Logger.getLogger(RestUsersClient.class.getName());

	protected static final int READ_TIMEOUT = 5000;
	protected static final int CONNECT_TIMEOUT = 5000;

	protected static final int MAX_RETRIES = 10;
	protected static final int RETRY_SLEEP = 5000;

	
	final URI serverURI;
	final Client client;
	final ClientConfig config;

	final WebTarget target;
	
	public RestUsersClient( URI serverURI ) {
		this.serverURI = serverURI;

		this.config = new ClientConfig();
		
		config.property( ClientProperties.READ_TIMEOUT, READ_TIMEOUT);
		config.property( ClientProperties.CONNECT_TIMEOUT, CONNECT_TIMEOUT);

		
		this.client = ClientBuilder.newClient(config);

		target = client.target( serverURI ).path( RestUsers.PATH );
	}
		
	public Result<String> createUser(User user) {
		return repeated(() -> {
			Response response = target.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return Result.ok(response.readEntity(String.class));
			} else {
				return Result.error(getErrorCodeFrom(response.getStatus()));
			}
		});
	}

	public Result<User> getUser(String userId, String pwd) {
		return repeated(() -> {
			Response response = target.path(userId)
				.queryParam(RestUsers.PASSWORD, pwd).request()
				.accept(MediaType.APPLICATION_JSON)
				.get();
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return Result.ok(response.readEntity(User.class));
			} else {
				return Result.error(getErrorCodeFrom(response.getStatus()));
			}
		});
	}
	
	public Result<User> updateUser(String userId, String password, User user) {
		return repeated(() -> {
			Response response = target.path(userId)
				.queryParam(RestUsers.PASSWORD, password).request()
				.accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(user, MediaType.APPLICATION_JSON));
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return Result.ok(response.readEntity(User.class));
			} else {
				return Result.error(getErrorCodeFrom(response.getStatus()));
			}
		});
	}

	public Result<User> deleteUser(String userId, String password) {
		return repeated(() -> {
			Response response = target.path(userId)
				.queryParam(RestUsers.PASSWORD, password)
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.delete();
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return Result.ok(response.readEntity(User.class));
			} else {
				return Result.error(getErrorCodeFrom(response.getStatus()));
			}
		});
	}



	public Result<List<User>> searchUsers(String pattern) {
		return repeated(() -> {
			Response response = target.path("/").queryParam(RestUsers.QUERY, pattern).request()
				.accept(MediaType.APPLICATION_JSON)
				.get();
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return Result.ok(response.readEntity(new GenericType<List<User>>() {}));
			} else {
				return Result.error(getErrorCodeFrom(response.getStatus()));
			}
		});
	}

	public static ErrorCode getErrorCodeFrom(int status) {
		return switch (status) {
		case 200, 209 -> ErrorCode.OK;
		case 409 -> ErrorCode.CONFLICT;
		case 403 -> ErrorCode.FORBIDDEN;
		case 404 -> ErrorCode.NOT_FOUND;
		case 400 -> ErrorCode.BAD_REQUEST;
		case 500 -> ErrorCode.INTERNAL_ERROR;
		case 501 -> ErrorCode.NOT_IMPLEMENTED;
		default -> ErrorCode.INTERNAL_ERROR;
		};
	}

	private <E> Result<E> repeated(Supplier<Result<E>> supplier) {
		for(int i = 0; i < MAX_RETRIES ; i++) {
			try {
					return supplier.get();
			} catch( ProcessingException x ) {
				Log.info(x.getMessage());
				
				try {
					Thread.sleep(RETRY_SLEEP);
				} catch (InterruptedException e) {
					//Nothing to be done here.
				}
			}
			catch( Exception x ) {
				x.printStackTrace();
			}
		}
		return Result.error(  ErrorCode.TIMEOUT );
	}

}

