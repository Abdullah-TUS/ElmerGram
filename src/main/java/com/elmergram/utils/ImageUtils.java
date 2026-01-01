package com.elmergram.utils;

import java.util.Set;

public final class ImageUtils {

    private static final int MAX_IMAGE_SIZE = 8 * 1024 * 1024; // 8 MB
    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png");


    public static void validateImage(byte[] fileBytes, String originalFilename) {
        validateSize(fileBytes);
        validateExtension(originalFilename);
    }

    private static void validateSize(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalArgumentException("Image is empty");
        }

        if (fileBytes.length > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Image size exceeds max allowed size of 8MB"
            );
        }
    }

    private static void validateExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("Invalid image filename");
        }

        String extension = originalFilename
                .substring(originalFilename.lastIndexOf('.') + 1)
                .toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Unsupported image type: " + extension
            );
        }
    }
}
