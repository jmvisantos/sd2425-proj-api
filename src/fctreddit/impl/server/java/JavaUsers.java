package fctreddit.impl.server.java;

import java.util.List;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.api.java.Users;
import fctreddit.impl.server.persistence.Hibernate;

public class JavaUsers implements Users {

	private static Logger Log = Logger.getLogger(JavaUsers.class.getName());

	private Hibernate hibernate;

	public JavaUsers() {
		hibernate = Hibernate.getInstance();
	}

	@Override
	public Result<String> createUser(User user) {
		Log.info("createUser : " + user);

		// Check if user data is valid
		if (user.getUserId() == null || user.getPassword() == null || user.getFullName() == null
				|| user.getEmail() == null || user.getPassword().isEmpty()) {
			Log.info("User object invalid.");
			return Result.error(ErrorCode.BAD_REQUEST);
		}

		// Check if user already exists
		User existingUser = null;
		try {
			existingUser = hibernate.get(User.class, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}
		if (existingUser != null) {
			Log.info("User already exists.");
			return Result.error(ErrorCode.CONFLICT);
		}

		try {
			hibernate.persist(user);
		} catch (Exception e) {
			e.printStackTrace(); // Most likely the exception is due to the user already existing...
			Log.info("User already exists.");
			return Result.error(ErrorCode.CONFLICT);
		}

		return Result.ok(user.getUserId());
	}

	@Override
	public Result<User> getUser(String userId, String password) {
		Log.info("getUser : user = " + userId + "; pwd = " + password);

		// Check if user is valid
		if (userId == null) {
			Log.info("UserId null.");
			return Result.error(ErrorCode.BAD_REQUEST);
		}

		if (password == null || password.isEmpty()) {
			Log.info("Password is empty or null.");
			return Result.error(ErrorCode.FORBIDDEN);
		}

		User user = null;

		try {
			user = hibernate.get(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

		// Check if user exists
		if (user == null) {
			Log.info("User does not exist.");
			return Result.error(ErrorCode.NOT_FOUND);
		}

		// Check if the password is correct
		if (!user.getPassword().equals(password)) {
			Log.info("Password is incorrect");
			return Result.error(ErrorCode.FORBIDDEN);
		}

		return Result.ok(user);

	}

	@Override
	public Result<User> updateUser(String userId, String password, User user) {
		Log.info("updateUser : user = " + userId + "; pwd = " + password + " ; userData = " + user);

		// Check if user is valid
		if (userId == null || user == null) {
			Log.info("UserId or password null.");
			return Result.error(ErrorCode.BAD_REQUEST);
		}

		if (password == null || password.isEmpty()) {
			Log.info("Password is empty or null.");
			return Result.error(ErrorCode.FORBIDDEN);
		}

		User existingUser = null;

		try {
			existingUser = hibernate.get(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

		// Check if user exists
		if (existingUser == null) {
			Log.info("User does not exist.");
			return Result.error(ErrorCode.NOT_FOUND);
		}

		// Check if the password is correct
		if (!existingUser.getPassword().equals(password)) {
			Log.info("Password is incorrect");
			return Result.error(ErrorCode.FORBIDDEN);
		}

		if (user.getFullName() != null) {
			existingUser.setFullName(user.getFullName());
		}

		if (user.getEmail() != null) {
			existingUser.setEmail(user.getEmail());
		}

		if (user.getPassword() != null) {
			existingUser.setPassword(user.getPassword());
		}

		if (user.getAvatarUrl() != null) {
			existingUser.setAvatarUrl(user.getAvatarUrl());
		}

		try {
			hibernate.update(existingUser);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

		return Result.ok(existingUser);

	}

	@Override
	public Result<User> deleteUser(String userId, String password) {
		Log.info("deleteUser : user = " + userId + "; pwd = " + password);

		// Check if user is valid
		if (userId == null || password == null || password.isEmpty()) {
			Log.info("UserId or password null.");
			return Result.error(ErrorCode.BAD_REQUEST);
		}

		User user = null;
		try {
			user = hibernate.get(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

		// Check if user exists
		if (user == null) {
			Log.info("User does not exist.");
			return Result.error(ErrorCode.NOT_FOUND);
		}

		// Check if the password is correct
		if (!user.getPassword().equals(password)) {
			Log.info("Password is incorrect");
			return Result.error(ErrorCode.FORBIDDEN);
		}

		try {
			hibernate.delete(user);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

		return Result.ok(user);
	}

	@Override
	public Result<List<User>> searchUsers(String keyword) {
		Log.info("searchUsers : keyword = " + keyword);

		
		List<User> users = null;

		try {
			String jpql = "SELECT u FROM User u WHERE u.userId LIKE '%" + keyword + "%'";
			users = hibernate.jpql(jpql, User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}
		return Result.ok(users);
	}

}
