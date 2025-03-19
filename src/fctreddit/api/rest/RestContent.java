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
	 * 
	 * @param post - The Post to be created, that should contain the userId of the author in the appropriate field.
	 * @param password - the password of author of the new post
	 * @return (OK, PostID) if the post was created;
	 * NOT FOUND, if the owner of the short does not exist;
	 * FORBIDDEN, if the password is not correct;
	 * BAD_REQUEST, otherwise.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createPost(Post post, @QueryParam(PASSWORD) String userPassword);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPosts(@QueryParam(TIMESTAMP) long timestamp, @QueryParam(SORTBY) String sortOrder);
	
	@GET
	@Path("{" + POSTID + "}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(@PathParam(POSTID) String postId);
	
	@GET
	@Path("{" + POSTID + "}/" + REPLIES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPostAnswers(@PathParam(POSTID) String postId);
	
	@PUT
	@Path("{" + POSTID + "}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post updatePost(@PathParam(POSTID) String postId, @QueryParam(PASSWORD) String userPassword, Post post);
	
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
