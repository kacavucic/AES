package com.naprednoprogramiranje.aes.service.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Provides storage access and options file system manipulation
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Service
public interface StorageService {
    /**
     * Initializes the storage service with working directory
     */
    void init();

    /**
     * Stores provided file in upload directory
     * @param file File to be stored
     * @return Path of the newly stored file
     */
    Path store(MultipartFile file);

    /**
     * Resolves path based on provided filename
     * @param filename Name with the extension of the file whose path will be retrieved
     * @return Resolved path
     */
    Path load(String filename);

    //Resource loadAsResource(String filename);

    /**
     * Deletes all files from the upload directory
     */
    void deleteAll();
}
