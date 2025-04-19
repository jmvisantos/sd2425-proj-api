package fctreddit.impl.server.java;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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

    private static final String IMAGE_STORAGE_DIR = "image";
    private UsersClient usersClient;
    private final Discovery discovery;
    private static final String SERVER_URI_FMT = "http://%s:%s/rest";
    public static final int PORT = 8081;
    public static String serverURI;


    public JavaImage() {
        discovery = Discovery.getInstance();

        try {
            // Discover the Users service
            URI usersURI = discovery.knownUrisOf(UsersServer.SERVICE, 1)[0];

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
            Path userDir = Paths.get(userId);
            Files.createDirectories(userDir);

            Path imagePath = userDir.resolve(imageId + ".img");

            Files.write(imagePath, imageContents);
            
            // Check if the file was created successfully
            if (!Files.exists(imagePath)) {
                return Result.error(ErrorCode.INTERNAL_ERROR);
            }

            // Convert the image path to an absolute URI
            String ip = InetAddress.getLocalHost().getHostAddress();

            serverURI = String.format(SERVER_URI_FMT, ip, PORT);

            // Construct the URI for the image
            String baseUrl = serverURI + "/image";

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

            Path imagePath = Paths.get(userId, imageId);

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

        Path imagePath = Paths.get(IMAGE_STORAGE_DIR, userId, imageId);

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
