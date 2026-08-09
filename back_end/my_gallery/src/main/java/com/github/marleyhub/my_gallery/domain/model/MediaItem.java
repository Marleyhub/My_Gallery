package com.github.marleyhub.my_gallery.domain.model;

import java.util.Set;

public class MediaItem {
    private static final long MAX_FILE_SIZE_BYTES = 100 * 1024 * 1024; // 100 MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "png", "mp4");

    private final String filename;
    private final long sizeInBytes;
    private final String extension;

    public MediaItem(String filename, long sizeInBytes, String extension) {
        if (filename == null || filename.isBlank()) {
            throw new DomainException("Filename cannot be empty");
        }
        if (sizeInBytes > MAX_FILE_SIZE_BYTES) {
            throw new DomainException("Media exceeds maximum permitted size of 100MB");
        }
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new DomainException("Unsupported media format: " + extension);
        }
        this.filename = filename;
        this.sizeInBytes = sizeInBytes;
        this.extension = extension;
    }
}