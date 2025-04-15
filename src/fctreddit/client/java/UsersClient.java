package fctreddit.clients.java;

import java.util.List;

import fctreddit.api.User;
import fctreddit.api.java.Result;
import fctreddit.api.java.Users;

public abstract class UsersClient implements Users {
	
	protected static final int READ_TIMEOUT = 5000;
	protected static final int CONNECT_TIMEOUT = 5000;

	protected static final int MAX_RETRIES = 10;
	protected static final int RETRY_SLEEP = 5000;
	
	abstract public Result<String> createUser(User user);

	abstract public Result<User> getUser(String userId, String password);
	
	abstract public Result<User> updateUser(String userId, String password, User user);

	abstract public Result<User> deleteUser(String userId, String password);

	abstract public Result<List<User>> searchUsers(String pattern);

}
