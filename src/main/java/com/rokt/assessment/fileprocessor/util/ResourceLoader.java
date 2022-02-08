package com.rokt.assessment.fileprocessor.util;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface ResourceLoader {

    File getFile(String resourceName) throws IOException;
}
