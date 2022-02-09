package com.rokt.assessment.fileprocessor.service;

import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.models.EntryPayload;
import com.rokt.assessment.fileprocessor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Spring service class that performs business logic.
 */
@Slf4j
@Service
public class FileProcessingService {

    private final FileUtil fileUtil;

    @Value("${data-directory.prefix}")
    private String APP_RESOURCE_PATH_PREFIX;

    public FileProcessingService(FileUtil util) {
        fileUtil = util;
        log.info("Starting Service component class");
    }

    public List<EntryPayload> getEntriesWithinTimeRange(EntryFilterRequest request) {

        List<EntryPayload> validEntries = new ArrayList<>();
        try {
            long startTime = Instant.now().toEpochMilli();
            File file = fileUtil.loadFile(Paths.get(APP_RESOURCE_PATH_PREFIX, request.getFilename()).toString());
            if (file == null) {
                log.error("File: " + request.getFilename() + " not found in app server");
                return validEntries;
            }
            OffsetDateTime start = OffsetDateTime.parse(request.getFrom());
            OffsetDateTime end = OffsetDateTime.parse(request.getTo());

            String fileEntry;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((fileEntry = bufferedReader.readLine()) != null) {
                Optional<OffsetDateTime> entryDateTimeOptional = getEntryDateTime(fileEntry);
                if (entryDateTimeOptional.isPresent()) {
                    if (checkEntryTimeWithinRange(start, end, entryDateTimeOptional.get())) {
                        validEntries.add(parseEntry(fileEntry));
                    }
                    if (checkEntryTimeBeyondEndTime(end, entryDateTimeOptional.get())) break;
                }
            }
            log.info("{} entries parsed in {} ms", validEntries.size(), Instant.now().toEpochMilli() - startTime);

        } catch (Exception e) {
            log.error("Exception while parsing file entries: {}", e.toString());
        }

        return validEntries;
    }

    private EntryPayload parseEntry(String entry) {
        String[] whiteSpaceDelimitedEntries = entry.trim().split("\\s");
        String dateTime = whiteSpaceDelimitedEntries[0];
        return new EntryPayload(dateTime, whiteSpaceDelimitedEntries[1], UUID.fromString(whiteSpaceDelimitedEntries[2]));
    }

    private boolean checkEntryTimeWithinRange(OffsetDateTime start, OffsetDateTime end, OffsetDateTime entryTime) {
        return (entryTime.isAfter(start) || entryTime.isEqual(start)) &&
                (entryTime.isBefore(end) || entryTime.isEqual(end));
    }

    private boolean checkEntryTimeBeyondEndTime(OffsetDateTime endTime, OffsetDateTime entryTime) {
        return entryTime.isAfter(endTime);
    }

    private Optional<OffsetDateTime> getEntryDateTime(String entry) {
        Optional<OffsetDateTime> result = Optional.empty();
        try {
            String[] whiteSpaceDelimitedEntries = entry.trim().split("\\s");
            result = Optional.of(OffsetDateTime.parse(whiteSpaceDelimitedEntries[0]));
        } catch (DateTimeParseException d) {
            log.error("DateTimeParseException {} while parsing file entry {}", d.getCause(), entry);
        } catch (Exception e) {
            log.error("Exception {} while parsing file entry {}", e.getCause(), entry);
        }
        return result;
    }
}
