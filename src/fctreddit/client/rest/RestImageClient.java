package fctreddit.client.rest;

import java.net.URI;
import java.util.logging.Logger;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.api.rest.RestImage;
import fctreddit.client.java.ImageClient;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class RestImageClient extends ImageClient {

  @Override
  public Result<String> createImage(String userId, byte[] imageContents, String metadata) {
    Log.info("createImage: user=" + userId);

    if (imageContents == null || imageContents.length == 0) {
      return Result.error(ErrorCode.BAD_REQUEST);
    }

    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.path(userId).request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(imageContents, MediaType.APPLICATION_OCTET_STREAM));

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(r.readEntity(String.class));
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }

  @Override
  public Result<byte[]> getImage(String userId, String imageId) {
    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.path(userId).path(imageId).request()
            .accept(MediaType.APPLICATION_OCTET_STREAM)
            .get();

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(r.readEntity(byte[].class));
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }

  @Override
  public Result<Void> deleteImage(String userId, String imageId, String metadata) {
    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.path(userId).path(imageId).request()
            .accept(MediaType.APPLICATION_JSON)
            .delete();

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(null);
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }
  private static Logger Log = Logger.getLogger(RestUsersClient.class.getName());

	final Client client;
	final ClientConfig config;

	final WebTarget target;

  public RestImageClient(URI serverURI) {

    this.config = new ClientConfig();

    config.property(ClientProperties.READ_TIMEOUT, READ_TIMEOUT);
    config.property(ClientProperties.CONNECT_TIMEOUT, CONNECT_TIMEOUT);

    this.client = ClientBuilder.newClient(config);

    target = client.target(serverURI).path(RestImage.PATH);
  }

  @Override
  public Result<String> createImage(byte[] imageContents) {
    Log.info("createImage: user=");

    if (imageContents == null || imageContents.length == 0) {
      return Result.error(ErrorCode.BAD_REQUEST);
    }

    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(imageContents, MediaType.APPLICATION_OCTET_STREAM));

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(r.readEntity(String.class));
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }

  @Override
  public Result<byte[]> getImage(String imageId) {
    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.path(imageId).request()
            .accept(MediaType.APPLICATION_OCTET_STREAM)
            .get();

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(r.readEntity(byte[].class));
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }

  @Override
  public Result<Void> deleteImage(String imageId) {
    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
        Response r = target.path(imageId).request()
            .accept(MediaType.APPLICATION_JSON)
            .delete();

        int status = r.getStatus();
        if (status != Status.OK.getStatusCode())
          return Result.error(getErrorCodeFrom(status));
        else
          return Result.ok(null);
      } catch (ProcessingException e) {
        Log.warning("ProcessingException: " + e.getMessage());
      }
    }
    return Result.error(ErrorCode.INTERNAL_ERROR);
  }

  public static ErrorCode getErrorCodeFrom(int status) {
		return switch (status) {
			case 200, 209 -> ErrorCode.OK;
			case 409 -> ErrorCode.CONFLICT;
			case 403 -> ErrorCode.FORBIDDEN;
			case 404 -> ErrorCode.NOT_FOUND;
			case 400 -> ErrorCode.BAD_REQUEST;
			case 500 -> ErrorCode.INTERNAL_ERROR;
			case 501 -> ErrorCode.NOT_IMPLEMENTED;
			default -> ErrorCode.INTERNAL_ERROR;
		};
	}
}
