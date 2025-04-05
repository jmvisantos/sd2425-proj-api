package fctreddit.api.java;

import java.util.List;

import fctreddit.api.User;

public interface Users {

	/**
	 * Creates a new user.
	 * 
	 * @param user User to be created (in the body of the request)
	 * @return 	<OK,String> and the userId in case of success. 
	 * 			CONFLICT if the userId already exists. 
	 * 			BAD_REQUEST if User is not valid.
	 * 			INTERNAL_SERVER_ERROR if unable to store user
	 */
	Result<String> createUser(User user);

	/**
	 * Obtains the information on the user identified by name.
	 * 
	 * @param userId   the userId of the user
	 * @param password password of the user
	 * @return 	<OK, User> and the user object in the case of success (password is correct)
	 * 			FORBIDDEN if the password is null or incorrect; 
	 * 			NOT_FOUND if no user exists with the provided userId
	 */
	Result<User> getUser(String userId, String password);

	/**
	 * Modifies the information of a user. Values of null in any field of the user
	 * will be considered as if the the fields is not to be modified (the id cannot
	 * be modified).
	 * 
	 * @param userId   the userId of the user
	 * @param password password of the user
	 * @param user     Updated information (in the body of the request)
	 * @return <OK, User> and the updated user object in case of success
	 *         FORBIDDEN if the password is incorrect or null 
	 *         NOT_FOUND if no user exists with the provided userId
	 */
	Result<User> updateUser(String userId, String password, User user);

	/**
	 * Deletes the user identified by userId. All posts of that user should have the
	 * authorId set to null, all upvotes and downvotes of the user should be removed
	 * and if the user has an avatar that avatar should be also removed.
	 * 
	 * @param nauserId the userId of the user
	 * @param password password of the user
	 * @return <OK, User> and the deleted user object in case of success
	 *         FORBIDDEN if the password is incorrect or null 
	 *         NOT_FOUND if no user exists with the provided userId
	 */
	Result<User> deleteUser(String userId, String password);

	/**
	 * Returns the list of users for which the pattern is a substring of the name
	 * (of the user), case-insensitive. The password of the users returned by the
	 * query must be set to the empty string "".
	 * 
	 * @param pattern substring to search (empty pattern translates to all users)
	 * @return <OK,List<User>> and the list of Users matching the search, regardless 
	 * 		    of the number of hits (including 0 hits)
	 */
	Result<List<User>> searchUsers(String pattern);
}
