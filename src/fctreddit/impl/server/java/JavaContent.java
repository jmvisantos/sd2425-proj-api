package fctreddit.impl.server.java;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import fctreddit.api.Post;
import fctreddit.api.User;
import fctreddit.api.java.Content;
import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.client.java.UsersClient;
import fctreddit.client.rest.RestUsersClient;
import fctreddit.impl.Discovery;
import fctreddit.impl.rest.UsersServer;


public class JavaContent implements Content {

    private static final Logger Log = Logger.getLogger(JavaContent.class.getName());
    private static final String CONTENT_STORAGE_DIR = "Content";
	private UsersClient usersClient;
    private final Discovery discovery;

    public JavaContent() {
		discovery = Discovery.getInstance();

		 try {
            // Discover the Users service
            URI usersURI = discovery.knownUrisOf(UsersServer.SERVICE, 1)[0];
            Log.info("Discovered Users service URI: " + usersURI);

            if (!usersURI.isAbsolute()) {
                throw new IllegalArgumentException("Discovered URI is not absolute: " + usersURI);
            }

            usersClient = new RestUsersClient(usersURI);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUsersClient() {
        if (usersClient != null) {
            return; // Already initialized
        }
        try {
            // Discover the Users service
           // URI usersURI = discovery.knownUrisOf(UsersServer.SERVICE, 1)[0];
            //Log.info("Discovered Users service URI: " + usersURI);

            //if (!usersURI.isAbsolute()) {
            //    throw new IllegalArgumentException("Discovered URI is not absolute: " + usersURI);
           // }

          //  usersClient = new RestUsersClient(usersURI);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public Result<String> createPost(Post post, String userPassword) {
		Log.info("createPost: post=" + post);

		if (post == null) {
			return Result.error(ErrorCode.BAD_REQUEST);
		}

		Result<User> userResult = usersClient.getUser(post.getAuthorId(), userPassword);
		if (userResult.isOK()) {
			return Result.error(userResult.error());
		}

		String postId = UUID.randomUUID().toString(); 	
		
		try {
			File userDir = new File(CONTENT_STORAGE_DIR, post.getAuthorId());
			if (!userDir.exists()) {
				userDir.mkdirs();
			}

			File postFile = new File(userDir, postId + ".post");
			try (FileWriter writer = new FileWriter(postFile)) {
				writer.write(post.toString());
			}

			return Result.ok(postId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(ErrorCode.INTERNAL_ERROR);
		}

	}

	@Override
	public Result<List<String>> getPosts(long timestamp, String sortOrder) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPosts'");
	}

	@Override
	public Result<Post> getPost(String postId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPost'");
	}

	@Override
	public Result<List<String>> getPostAnswers(String postId, long maxTimeout) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPostAnswers'");
	}

	@Override
	public Result<Post> updatePost(String postId, String userPassword, Post post) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
	}

	@Override
	public Result<Void> deletePost(String postId, String userPassword) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
	}

	@Override
	public Result<Void> upVotePost(String postId, String userId, String userPassword) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'upVotePost'");
	}

	@Override
	public Result<Void> removeUpVotePost(String postId, String userId, String userPassword) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeUpVotePost'");
	}

	@Override
	public Result<Void> downVotePost(String postId, String userId, String userPassword) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'downVotePost'");
	}

	@Override
	public Result<Void> removeDownVotePost(String postId, String userId, String userPassword) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeDownVotePost'");
	}

	@Override
	public Result<Integer> getupVotes(String postId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getupVotes'");
	}

	@Override
	public Result<Integer> getDownVotes(String postId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDownVotes'");
	}

    
}
