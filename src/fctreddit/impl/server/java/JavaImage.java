package fctreddit.impl.server.java;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.api.java.Image;
import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.client.java.UsersClient;
import fctreddit.client.rest.RestUsersClient;
import fctreddit.impl.Discovery;
import fctreddit.impl.rest.UsersServer;

public class JavaImage implements Image {

    private static final Logger Log = Logger.getLogger(JavaImage.class.getName());
    private static final String IMAGE_STORAGE_DIR = "images";
    private UsersClient userServer;

    public JavaImage() {
        try {
            // Access the static Discovery instance from UsersServer
            Discovery discovery = UsersServer.discovery;

            if (discovery == null) {
                throw new IllegalStateException("Discovery instance is not initialized in UsersServer!");
            }

            // Discover the Users service
            URI[] usersURI = discovery.knownUrisOf(UsersServer.SERVICE, 1);
            if (usersURI.length > 0) {
                Log.info("Found Users service at: " + usersURI[0]);
                // Initialize the UsersClient with the discovered URI
                userServer = new RestUsersClient(usersURI[0]);
            } else {
                throw new IllegalStateException("No Users service found via Discovery!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JavaImage due to Discovery issues.", e);
        }

        // Ensure storage directory exists
        File dir = new File(IMAGE_STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public Result<String> createImage(String userId, byte[] imageContents, String password) {
        Log.info("createImage: user=" + userId);

        if (userId == null || password == null || imageContents == null || imageContents.length == 0) {
            return Result.error(ErrorCode.BAD_REQUEST);
        }

        // Validate user credentials using UsersClient
        Result<User> userResult = userServer.getUser(userId, password);
        if (!userResult.isOK()) {
            return Result.error(userResult.error());
        }

        try {
            String imageId = UUID.randomUUID().toString();
            Path userDir = Paths.get(IMAGE_STORAGE_DIR, userId);
            Files.createDirectories(userDir);

            Path imagePath = userDir.resolve(imageId + ".img");
            Files.write(imagePath, imageContents);

            return Result.ok(imageId);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<byte[]> getImage(String userId, String imageId) {
        Log.info("getImage: user=" + userId + ", imageId=" + imageId);

        if (userId == null || imageId == null) {
            return Result.error(ErrorCode.BAD_REQUEST);
        }

        Path imagePath = Paths.get(IMAGE_STORAGE_DIR, userId, imageId + ".img");

        if (!Files.exists(imagePath)) {
            return Result.error(ErrorCode.NOT_FOUND);
        }

        try {
            byte[] data = Files.readAllBytes(imagePath);
            return Result.ok(data);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<Void> deleteImage(String userId, String imageId, String password) {
        Log.info("deleteImage: user=" + userId + ", imageId=" + imageId);

        if (userId == null || imageId == null || password == null) {
            return Result.error(ErrorCode.BAD_REQUEST);
        }

        // Validate user credentials using UsersClient
        Result<User> userResult = userServer.getUser(userId, password);
        if (!userResult.isOK()) {
            return Result.error(userResult.error());
        }

        Path imagePath = Paths.get(IMAGE_STORAGE_DIR, userId, imageId + ".img");

        if (!Files.exists(imagePath)) {
            return Result.error(ErrorCode.NOT_FOUND);
        }

        try {
            Files.delete(imagePath);
            return Result.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ErrorCode.INTERNAL_ERROR);
        }
    }
}
