package com.rokt.assessment.fileprocessor.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileUtil {

    public File loadFile(String filePath) {
        File resourceFile = null;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            resourceFile = File.createTempFile(filePath, "temp");
            Files.copy(fis, resourceFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            resourceFile.deleteOnExit();
        } catch (IOException ex) {
            log.error("Exception thrown while accessing resource: {}", ex.toString());
        }
        return resourceFile;
    }
}
