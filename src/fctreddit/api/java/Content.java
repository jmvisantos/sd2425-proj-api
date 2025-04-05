package fctreddit.api.java;

import java.util.List;

import fctreddit.api.Post;

public interface Content {

	/**
	 * The following constants are the values that can be sent for the query parameter SORTBY
	 **/
	public static final String MOST_UP_VOTES = "votes";
	public static final String MOST_REPLIES = "replies";
	
	
	/**
	 * Creates a new Post (that can be an answer to another Post), generating its unique identifier. 
	 * The result should be the identifier of the Post in case of success.
	 * The creation timestamp of the post should be set to be the time in the server when the request
	 * was received.
	 * 
	 * @param post - The Post to be created, that should contain the userId of the author in the appropriate field.
	 * @param password - the password of author of the new post
	 * @return OK and PostID if the post was created;
	 * NOT FOUND, if the owner of the short does not exist;
	 * FORBIDDEN, if the password is not correct;
	 * BAD_REQUEST, otherwise.
	 */
	public Result<String> createPost(Post post, String userPassword);
	
	/**
	 * Retrieves a list with all top-level Posts unique identifiers (i.e., Posts that have no parent Post).
	 * By default (i.e., when no query parameter is passed) all top-level posts should be returned in the 
	 * order in which they were created. The effects of both optional parameters can be combined to affect
	 * the answer.
	 * 
	 * @param timestamp this is an optional parameter, if it is defined then the returned list
	 * should only contain Posts whose creation timestamp is equal or above the provided timestamp.
	 * @param sortOrder this is an optional parameter, the admissible values are on constants MOST_UP_VOTES
	 * and MOST_REPLIES, if the first is indicated, posts IDs should be ordered from the Post with more votes
	 * to the one with less votes. If the second is provided posts IDs should be ordered from the Post with 
	 * more replies to the one with less replies.
	 * @return 	OK and the List of PostIds that match all options in the right order 
	 * 			
	 */
	public Result<List<String>> getPosts(long timestamp, String sortOrder);
	
	/**
	 * Retrieves a given post.
	 * 
	 * @param postId the unique identifier of the short to be retrieved
	 * @return 	OK and the Post in case of success 
	 * 			NOT_FOUND if postId does not match an existing Post
	 */
	public Result<Post> getPost(String postId);
	
	/**
	 * Retrieves a list with all unique identifiers of posts that have the post
	 * identified by the postId as their ancestor (i.e., the replies to that post),
	 * the order should be the creation order of those posts.
	 * @param postId the postId for which answers want to be obtained
	 * @param timeout (optional) indicates the maximum amount of time that this operation should
	 * 		  wait (before returning a reply to the client) for a new answer to be added
	 * 		  to the post. If a new answer is added to the target post after the start of 
	 * 		  the execution of this operation and before the timeout expires an answer should
	 * 		  be sent to the client at that time. 		   
	 * @return 	OK and the List of PostIds that are answers to the post ordered by creationTime 
	 * 			NOT_FOUND if postId does not match an existing Post	
	 * 		
	 */
	public Result<List<String>> getPostAnswers(String postId, long maxTimeout);
	
	/**
	 * Updates the contents of a post restricted to the fields:
	 * - content
	 * - mediaUrl
	 * @param postId the post that should be updated
	 * @param userPassword the password, it is assumed that only the author of the post 
	 * can updated it, and as such, the password sent in the operation should belong to 
	 * that user.
	 * @param post A post object with the fields to be updated
	 * @return 	OK the updated post, in case of success.
	 * 			FORBIDDEN, if the password is not correct;
	 * 			BAD_REQUEST, otherwise.
	 */
	public Result<Post> updatePost(String postId, String userPassword, Post post);
	
	/**
	 * Deletes a given Post, only the author of the Post can do this operation. A successful delete will also remove
	 * any reply to this post (or replies to those replies) even if performed by different authors.
	 * 
	 * @param postId the unique identifier of the Post to be deleted
	 * @return 	NO_CONTENT in case of success 
	 * 			NOT_FOUND if postId does not match an existing post
	 * 			FORBIDDEN if the password is not correct (it should always be considered the authorId 
	 * 					  of the post as the user that is attempting to execute this operation);
	 *			CONFLICT  if this post already has any other content referencing it (is the parent of
	 *					  another post or if there is at least one upvote or a downvote to this post.
	 */	
	public Result<Void> deletePost(String postId, String userPassword);
	
	/**
	 * Adds an upvote to a given post made by a specific user (might be different from the author
	 * of the post). The vote must be authenticated by the password of the user adding the upvote. 
	 * The upvote on a post can be only be made once by an user, and the user must not have a downvote
	 * on that post.
	 * @param postId unique identifier of the post over which the upvote is made
	 * @param userId unique identifier of the user making the upvote
	 * @param userPassword Password of user making the upvote
	 * @return 	NO_CONTENT in case of success
	 * 			NOT_FOUND if the postId does not match an existing post or the user does not exists
	 * 			FORBIDDEN if the password is not correct
	 * 			CONFLICT if the user already has made an upvote or downvote on the post
	 *			BAD_REQUEST otherwise
	 */
	public Result<Void> upVotePost(String postId, String userId, String userPassword);
	
	/**
	 * Removes a previously added upvote to a given post made by a specific user (might be different from the author
	 * of the post). The action must be authenticated by the password of the user removing the upvote.
	 * @param postId unique identifier of the post over which the upvote is removed
	 * @param userId unique identifier of the user removing the upvote
	 * @param userPassword Password of user removing the upvote
	 * @return 	NO_CONTENT in case of success
	 * 			NOT_FOUND if the postId does not match an existing post or the user does not exists
	 * 			FORBIDDEN if the password is not correct
	 * 			CONFLICT if the user had not made an upvote on this post previously
	 *			BAD_REQUEST otherwise
	 */
	public Result<Void> removeUpVotePost(String postId, String userId, String userPassword);
	
	/**
	 * Adds an downvote to a given post made by a specific user (might be different from the author
	 * of the post). The vote must be authenticated by the password of the user adding the downvote. 
	 * The downvote on a post can be only be made once by an user, and the user must not have a upvote
	 * on that post.
	 * @param postId unique identifier of the post over which the downvote is made
	 * @param userId unique identifier of the user making the downvote
	 * @param userPassword Password of user making the downvote
	 * @return 	NO_CONTENT in case of success
	 * 			NOT_FOUND if the postId does not match an existing post or the user does not exists
	 * 			FORBIDDEN if the password is not correct
	 * 			CONFLICT if the user already has made an upvote or downvote on the post
	 *			BAD_REQUEST otherwise
	 */
	public Result<Void> downVotePost(String postId, String userId, String userPassword);
	
	/**
	 * Removes a previously added downvote to a given post made by a specific user (might be different from the author
	 * of the post). The action must be authenticated by the password of the user removing the downvote. 
	 * @param postId unique identifier of the post over which the downvote is removed
	 * @param userId unique identifier of the user removing the downvote
	 * @param userPassword Password of user removing the downvote
	 * @return 	NO_CONTENT in case of success
	 * 			NOT_FOUND if the postId does not match an existing post or the user does not exists
	 * 			FORBIDDEN if the password is not correct
	 * 			CONFLICT if the user had not made an downvote on this post previously
	 *			BAD_REQUEST otherwise
	 */
	public Result<Void> removeDownVotePost(String postId, String userId, String userPassword);
	
	/**
	 * Exposes the number of upvotes currently associated with a given post
	 * @param postId the post that is targeted by this operation
	 * @return	OK and the number of upvotes in case of success
	 * 			NOT_FOUND if the postId does not match an existing post
	 */
	public Result<Integer> getupVotes(String postId);
	
	/**
	 * Exposes the number of downvotes currently associated with a given post
	 * @param postId the post that is targeted by this operation
	 * @return	OK and the number of downvotes in case of success
	 * 			NOT_FOUND if the postId does not match an existing post
	 */
	public Result<Integer> getDownVotes(String postId);

}
