package fctreddit.impl.rest;

import java.util.logging.Logger;

import fctreddit.api.java.Image;
import fctreddit.api.java.Result;
import fctreddit.api.rest.RestImage;
import fctreddit.impl.server.java.JavaImage;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class ImageResource implements RestImage{
  private static Logger Log = Logger.getLogger(ImageResource.class.getName());

  final Image impl;

  public ImageResource() {
    impl = new JavaImage();
  }

  @Override
  public String createImage(String userId, byte[] imageContents, String password) {
    Log.info("createImage : user" + userId + "; pwd = " + password);
    Result<String> res = impl.createImage(userId, imageContents, password);

    if(!res.isOK()) {
      throw new WebApplicationException(errorCodeToStatus(res.error()));
    }
    return res.value();
  }



  @Override
  public byte[] getImage(String userId, String imageId) {
    Log.info("getImage : user = " + userId + "; imageId = " + imageId);
    Result<byte[]> res = impl.getImage(userId, imageId);

    if(!res.isOK()) {
      System.out.println("getImage : user = " + userId + "; imageId = " + imageId + "; error = " + res.error());
      throw new WebApplicationException(errorCodeToStatus(res.error()));
    }
    return res.value();
  }



  @Override
  public void deleteImage(String userId, String imageId, String password) {
    Log.info("deleteImage : user = " + userId + "; imageId = " + imageId + "; pwd = " + password);
    Result<Void> res = impl.deleteImage(userId, imageId, password);

    if(!res.isOK()) {
      throw new WebApplicationException(errorCodeToStatus(res.error()));
    }
  }

	protected static Status errorCodeToStatus( Result.ErrorCode error ) {
    Status status =  switch( error) {
    case NOT_FOUND -> Status.NOT_FOUND; 
    case CONFLICT -> Status.CONFLICT;
    case FORBIDDEN -> Status.FORBIDDEN;
    case NOT_IMPLEMENTED -> Status.NOT_IMPLEMENTED;
    case BAD_REQUEST -> Status.BAD_REQUEST;
    default -> Status.INTERNAL_SERVER_ERROR;
    };
    
    return status;
  }

}
