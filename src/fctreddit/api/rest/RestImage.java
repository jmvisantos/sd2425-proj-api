package fctreddit.api.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path(RestImage.PATH)
public interface RestImage {

	public static final String PATH = "/image";
	public static final String IMAGE_ID = "id";
	public static final String USER_ID = "user";
	public static final String PASSWORD = "pwd";
	/**
	 * Create an image
	 * 
	 * @param imageContent the bytes of the image in PNG format (in the body of the request)
	 * @return 	OK in the case of success returning the URI to access the image. 
	 * 		   	NOT_FOUND if user does not exists
	 * 			FORBIDDEN if user password is incorrect
	 * 		   	BAD_REQUEST if imageContents has a size of zero or password is null
	 */
	@POST
	@Path("{" + USER_ID + "}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	String createImage(@PathParam(USER_ID) String userId, byte[] imageContents, @QueryParam(PASSWORD) String password);

	/**
	 * Gets the contents of an image associated with the imageId
	 * 
	 * @param imageId the identifier of the image
	 * @return OK the case of success returning the bytes of the image exists
	 *  	   NOT_FOUND should be returned if the image does not exists
	 */
	@GET
	@Path("{" + USER_ID + "}/{" + IMAGE_ID + "}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	byte[] getImage(@PathParam(USER_ID) String userId, @PathParam(IMAGE_ID) String imageId);
	
	/**
	 * Deletes an image identified by imageId
	 * 
	 * @param imageId the identifier of the image
	 * @return 	NO_CONTENT in the case of success. 
	 * 			NOT_FOUND if the image or user does not exists
	 * 			FORBIDDEN if user password is incorrect or the user attempting 
	 * 					  to make the operation is not the owner of the image
	 * 		   	BAD_REQUEST password is null
	 */
	@DELETE
	@Path("{" + USER_ID + "}/{" + IMAGE_ID + "}")
	void deleteImage(@PathParam(USER_ID) String userId, @PathParam(IMAGE_ID) String imageId, @QueryParam(PASSWORD) String password);
	
	
	
}