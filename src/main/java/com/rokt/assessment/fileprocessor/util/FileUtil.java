package com.rokt.assessment.fileprocessor.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileUtil {

    public File getFileForParsing(String filename) {
        FileSystemLoader fileLoader = new FileSystemLoader();
        try {
            return fileLoader.getFile(filename);
        } catch (IOException e) {
            InputStream cpResource = this.getClass().getClassLoader().getResourceAsStream("app/test-files/" + filename);
            File tmpFile = null;
            try {
                if (cpResource != null) {
                    tmpFile = File.createTempFile(filename, "temp");
                    Files.copy(cpResource, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    tmpFile.deleteOnExit();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return tmpFile;
        }
    }

}
