package fctreddit.impl.server.java;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    private static final String IMAGE_STORAGE_DIR = "Images";
    private UsersClient usersClient;
    private final Discovery discovery;

    public JavaImage() {
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

        // Ensure storage directory exists
        File dir = new File(IMAGE_STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public Result<String> createImage(String userId, byte[] imageContents, String password) {
        Log.info("createImage: user=" + userId);

        if (imageContents == null || imageContents.length == 0) {
            return Result.error(ErrorCode.BAD_REQUEST);
        }

        // Validate user credentials using UsersClient
        Result<User> userResult = usersClient.getUser(userId, password);
        if (!userResult.isOK()) {
            return Result.error(userResult.error());
        }

        try {
            String imageId = UUID.randomUUID().toString();
            Path userDir = Paths.get(IMAGE_STORAGE_DIR, userId);
            Files.createDirectories(userDir);

            Path imagePath = userDir.resolve(imageId + ".img");

            Files.write(imagePath, imageContents);

            // Check if the file was created successfully
            if (!Files.exists(imagePath)) {
                return Result.error(ErrorCode.INTERNAL_ERROR);
            }

            // Convert the image path to an absolute URI
            String baseUrl = "http://localhost/images"; // Base URL for serving images
            String relativePath = userId + "/" + imageId + ".img";
            URI imageUri = URI.create(baseUrl + "/" + relativePath);

            return Result.ok(imageUri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<byte[]> getImage(String userId, String imageId) {
        Log.info("getImage: user=" + userId + ", imageId=" + imageId);

        try {
            if (imageId == null) {
                return Result.error(ErrorCode.BAD_REQUEST);
            }

            // Validate user credentials using UsersClient
            Result<List<User>> userResultList = usersClient.searchUsers(userId);
            if (!userResultList.isOK() || userResultList.value().isEmpty()) {
                return Result.error(userResultList.error());
            }
            User user = userResultList.value().get(0); // Assuming the first user is the desired one
            Result<User> userResult = Result.ok(user);
            if (!userResult.isOK()) {
                return Result.error(userResult.error());
            }

            Path imagePath = Paths.get(IMAGE_STORAGE_DIR, userId, imageId + ".img");
            if (!Files.exists(imagePath)) {
                return Result.error(ErrorCode.NOT_FOUND);
            }

            byte[] data = Files.readAllBytes(imagePath);
            return Result.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ErrorCode.INTERNAL_ERROR);
        }

    }

    @Override
    public Result<Void> deleteImage(String userId, String imageId, String password) {
        Log.info("deleteImage: user=" + userId + ", imageId=" + imageId);

        if (imageId == null) {
            return Result.error(ErrorCode.BAD_REQUEST);
        }

        // Validate user credentials using UsersClient
        Result<User> userResult = usersClient.getUser(userId, password);
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
