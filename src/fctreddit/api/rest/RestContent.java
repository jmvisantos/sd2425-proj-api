package fctreddit.api.rest;

import java.util.List;

import fctreddit.api.Post;
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

@Path(RestContent.PATH)
public interface RestContent {

	public static final String PATH = "/posts";
	public static final String PASSWORD = "pwd";
	public static final String POSTID = "postId";
	public static final String TIMESTAMP = "timestamp";
	public static final String REPLIES = "replies";
	public static final String UPVOTE = "upvote";
	public static final String DOWNVOTE = "downvote";
	public static final String USERID = "userId";
	public static final String SORTBY = "sortBy";
	
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
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createPost(Post post, @QueryParam(PASSWORD) String userPassword);
	
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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPosts(@QueryParam(TIMESTAMP) long timestamp, @QueryParam(SORTBY) String sortOrder);
	
	/**
	 * Retrieves a given post.
	 * 
	 * @param postId the unique identifier of the short to be retrieved
	 * @return 	OK and the Post in case of success 
	 * 			NOT_FOUND if postId does not match an existing Post
	 */
	@GET
	@Path("{" + POSTID + "}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(@PathParam(POSTID) String postId);
	
	/**
	 * Retrieves a list with all unique identifiers of posts that have the post
	 * identified by the postId as their ancestor (i.e., the replies to that post),
	 * the order should be the creation order of those posts.
	 * @return 	OK and the List of PostIds that match all options in the right order 
	 * 			NOT_FOUND if postId does not match an existing Post	
	 * 		
	 */
	@GET
	@Path("{" + POSTID + "}/" + REPLIES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPostAnswers(@PathParam(POSTID) String postId);
	
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
	@PUT
	@Path("{" + POSTID + "}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post updatePost(@PathParam(POSTID) String postId, @QueryParam(PASSWORD) String userPassword, Post post);
	
	/**
	 * Deletes a given Post, only the author of the Post can do this operation. A successful delete will also remove
	 * any reply to this post (or replies to those replies) even if performed by different authors.
	 * 
	 * @param postId the unique identifier of the Post to be deleted
	 * @return 	NO_CONTENT in case of success 
	 * 			NOT_FOUND if postId does not match an existing post
	 * 			FORBIDDEN if the password is not correct (it should always be considered the authorId 
	 * 					  of the post as the user that is attempting to execute this operation);
	 */	
	@DELETE
	@Path("{" + POSTID + "}")
	public void deletePost(@PathParam(POSTID) String postId, @QueryParam(PASSWORD) String userPassword);
	
	@PUT
	@Path("{" + POSTID + "}/" + UPVOTE + "/{" + USERID + "}" )
	public void upVotePost(@PathParam(POSTID) String postId, @PathParam(USERID) String userId, @QueryParam(PASSWORD) String userPassword);
	
	@PUT
	@Path("{" + POSTID + "}/" + UPVOTE + "/{" + USERID + "}" )
	public void removeUpVotePost(@PathParam(POSTID) String postId, @PathParam(USERID) String userId, @QueryParam(PASSWORD) String userPassword);
	
	@PUT
	@Path("{" + POSTID + "}/" + DOWNVOTE + "/{" + USERID + "}" )
	public void downVotePost(@PathParam(POSTID) String postId, @PathParam(USERID) String userId, @QueryParam(PASSWORD) String userPassword);
	
	@PUT
	@Path("{" + POSTID + "}/" + DOWNVOTE + "/{" + USERID + "}" )
	public void removeDownVotePost(@PathParam(POSTID) String postId, @PathParam(USERID) String userId, @QueryParam(PASSWORD) String userPassword);
	
	@GET
	@Path("{" + POSTID + "}/" + UPVOTE)
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer getupVotes(@PathParam(POSTID) String postId);
	
	@GET
	@Path("{" + POSTID + "}/" + DOWNVOTE)
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer getDownVotes(@PathParam(POSTID) String postId);

}
