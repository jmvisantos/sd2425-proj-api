package fctreddit.api.rest;

import java.util.List;

import fctreddit.api.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path(RestUsers.PATH)
public interface RestUsers {

	public static final String PATH = "/users";
	public static final String QUERY = "query";
	public static final String USER_ID = "userId";
	public static final String PASSWORD = "password";

	/**
	 * Creates a new user.
	 * 
	 * @param user User to be created (in the body of the request)
	 * @return 	OK and the userId in case of success. 
	 * 			CONFLICT if the userId already exists. 
	 * 			BAD_REQUEST if User is not valid.
	 * 			INTERNAL_SERVER_ERROR if unable to store used
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	String createUser(User user);

	/**
	 * Obtains the information on the user identified by name.
	 * 
	 * @param userId   the userId of the user
	 * @param password password of the user
	 * @return 	OK and the user object in the case of success (password is correct)
	 * 			FORBIDDEN if the password is null or incorrect; 
	 * 			NOT_FOUND if no user exists with the provided userId
	 */
	@GET
	@Path("/{" + USER_ID + "}")
	@Produces(MediaType.APPLICATION_JSON)
	User getUser(@PathParam(USER_ID) String userId, @QueryParam(PASSWORD) String password);

	/**
	 * Modifies the information of a user. Values of null in any field of the user
	 * will be considered as if the the fields is not to be modified (the id cannot
	 * be modified).
	 * 
	 * @param userId   the userId of the user
	 * @param password password of the user
	 * @param user     Updated information (in the body of the request)
	 * @return OK and the updated user object in case of success
	 *         FORBIDDEN if the password is incorrect or null 
	 *         NOT_FOUND if no user exists with the provided userId
	 */
	@PUT
	@Path("/{" + USER_ID + "}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	User updateUser(@PathParam(USER_ID) String userId, @QueryParam(PASSWORD) String password, User user);

	/**
	 * Deletes the user identified by userId. The spreadsheets owned by the user
	 * should be eventually removed (asynchronous deletion is ok).
	 * 
	 * @param nauserId the userId of the user
	 * @param password password of the user
	 * @return OK and the deleted user object in case of success
	 *         FORBIDDEN if the password is incorrect or null 
	 *         NOT_FOUND if no user exists with the provided userId
	 */
	@DELETE
	@Path("/{" + USER_ID + "}")
	@Produces(MediaType.APPLICATION_JSON)
	User deleteUser(@PathParam(USER_ID) String userId, @QueryParam(PASSWORD) String password);

	/**
	 * Returns the list of users for which the pattern is a substring of the name
	 * (of the user), case-insensitive. The password of the users returned by the
	 * query must be set to the empty string "".
	 * 
	 * @param pattern substring to search (empty pattern translates to all users)
	 * @return OK and the list of Users matching the search, regardless of the number of hits
	 *         (including 0 hits)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<User> searchUsers(@QueryParam(QUERY) String pattern);
}