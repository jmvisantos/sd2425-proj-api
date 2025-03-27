package fctreddit.api.java;

public interface Image {

	/**
	 * Create an image
	 * 
	 * @param imageContent the bytes of the image in PNG format (in the body of the request)
	 * @return 	<OK, String> in the case of success returning the URI to access the image. 
	 * 		   	NOT_FOUND if user does not exists
	 * 			FORBIDDEN if user password is incorrect
	 * 		   	BAD_REQUEST if imageContents has a size of zero or password is null
	 */
	Result<String> createImage(String userId, byte[] imageContents, String password);

	/**
	 * Gets the contents of an image associated with the imageId
	 * 
	 * @param imageId the identifier of the image
	 * @return <OK, byte[]> the case of success returning the bytes of the image exists
	 *  	   NOT_FOUND should be returned if the image does not exists
	 */
	Result<byte[]> getImage(String userId, String imageId);
	
	/**
	 * Deletes an image identified by imageId
	 * 
	 * @param imageId the identifier of the image
	 * @return 	<OK, Void> in the case of success. 
	 * 			NOT_FOUND if the image or user does not exists
	 * 			FORBIDDEN if user password is incorrect
	 * 		   	BAD_REQUEST password is null
	 */
	Result<Void> deleteImage(String userId, String imageId, String password);
	
	
	
}