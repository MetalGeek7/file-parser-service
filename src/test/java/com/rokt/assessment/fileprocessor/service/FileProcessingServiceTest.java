package com.rokt.assessment.fileprocessor.service;

import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.models.EntryPayload;
import com.rokt.assessment.fileprocessor.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class FileProcessingServiceTest {

    public static final String APP_TEST_FILES_PREFIX = "./app/test-files/";
    private static final String testSampleFilePath = "sample.txt";
    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private FileProcessingService fileProcessingService;

    @BeforeEach
    void setUp() {
        String sampleFilePath = this.getClass().getClassLoader()
                .getResource(APP_TEST_FILES_PREFIX + testSampleFilePath)
                .getPath();
        File testFile = new File(sampleFilePath);
        lenient().when(fileUtil.loadFile(APP_TEST_FILES_PREFIX + testSampleFilePath)).thenReturn(testFile);
        //fileProcessingService = new FileProcessingService(fileUtil);
        ReflectionTestUtils.setField(fileProcessingService, "APP_RESOURCE_PATH_PREFIX", APP_TEST_FILES_PREFIX);

    }

    @Test
    void testFileNotFound() {
        lenient().when(fileUtil.loadFile(APP_TEST_FILES_PREFIX + "sample7.txt")).thenReturn(null);
        assertTrue(fileProcessingService.getEntriesWithinTimeRange(
                new EntryFilterRequest("sample7.txt", "2000-01-01T23:59:10Z", "2000-01-04T04:55:02Z")).isEmpty());
    }

    @DisplayName("Invalid Date format - non ISO-8601 compliant")
    @Test
    void testDateTimeWrongFormat() {
        assertTrue(fileProcessingService.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-01-01", "2000-01-04")).isEmpty());
    }

    @DisplayName("Invalid Date format - Feb 30th")
    @Test()
    void testInvalidRequestDate() {
        assertTrue(fileProcessingService.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-02-30T00:04:40Z", "2000-02-30T06:13:21Z")).isEmpty());
    }

    @Test
    void testGetFilesWithinTimeRange() {
        List<EntryPayload> filteredEntries = fileProcessingService.getEntriesWithinTimeRange(
                new EntryFilterRequest(testSampleFilePath, "2000-01-01T23:59:10Z", "2000-01-04T04:55:02Z"));

        assertFalse(filteredEntries.isEmpty());
        assertEquals(4, filteredEntries.size());
    }
}