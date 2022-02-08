package com.rokt.assessment.fileprocessor.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileSystemLoader implements ResourceLoader {

    public static final String APP_RESOURCE_PATH_PREFIX = "/app/test-files/";


    @Override
    public File getFile(String fileName) throws IOException {
        File resourceFile;
        try (FileInputStream fis = new FileInputStream(APP_RESOURCE_PATH_PREFIX + fileName)) {
            resourceFile = File.createTempFile(fileName, "temp");
            Files.copy(fis, resourceFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            resourceFile.deleteOnExit();
        } catch (IOException ex) {
            log.error("Exception thrown while accessing resource: {}", ex.toString());
            throw ex;
        }
        return resourceFile;
    }

}
