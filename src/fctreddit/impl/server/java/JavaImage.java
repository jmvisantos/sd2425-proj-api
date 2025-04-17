package fctreddit.impl.server.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.api.java.Image;
import fctreddit.api.java.Result;
import fctreddit.api.java.Result.ErrorCode;
import fctreddit.api.java.Users;

public class JavaImage implements Image {

    private static final Logger Log = Logger.getLogger(JavaImage.class.getName());
    private static final String IMAGE_STORAGE_DIR = "images";

    private final Users userServer;

    public JavaImage(Users userServer) {
        this.userServer = userServer;

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
