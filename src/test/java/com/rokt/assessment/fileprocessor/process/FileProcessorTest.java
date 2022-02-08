package com.rokt.assessment.fileprocessor.process;

import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    private static final String testSampleFilePath = "sample.txt";

    private FileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        fileProcessor = new FileProcessor(new FileUtil());
    }

    @Test
    void testFileNotFound() {
        assertTrue(fileProcessor.getEntriesWithinTimeRange(
                new EntryFilterRequest("sample7.txt", "2000-01-01T23:59:10Z", "2000-01-04T04:55:02Z")).isEmpty());
    }

    @DisplayName("Invalid Date format - non ISO-8601 compliant")
    @Test
    void testDateTimeWrongFormat() {
        assertTrue(fileProcessor.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-01-01", "2000-01-04")).isEmpty());
    }

    @DisplayName("Invalid Date format - Feb 30th")
    @Test()
    void testInvalidRequestDate() {
        assertTrue(fileProcessor.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-02-30T00:04:40Z", "2000-02-30T06:13:21Z")).isEmpty());
    }

    @Test
    void testGetFilesWithinTimeRange() {
        List<EntryPayload> filteredEntries = fileProcessor.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-01-01T23:59:10Z", "2000-01-04T04:55:02Z"));

        assertFalse(filteredEntries.isEmpty());
        assertEquals(4, filteredEntries.size());
    }
}