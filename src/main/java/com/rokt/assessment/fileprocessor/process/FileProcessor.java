package com.rokt.assessment.fileprocessor.process;

import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.models.EntryPayload;
import com.rokt.assessment.fileprocessor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Spring service class that performs business logic.
 */
@Slf4j
@Service
public class FileProcessor {

    private FileUtil fileUtil;

    public FileProcessor(FileUtil util) {
        fileUtil = util;
        log.info("Starting Service component class");
    }

    public List<EntryPayload> getEntriesWithinTimeRange(EntryFilterRequest request) {

        List<EntryPayload> validEntries = new ArrayList<>();
        try {
            File file = fileUtil.getFileForParsing(request.getFilename());
            if (file == null) {
                log.error("File: " + request.getFilename() + " not found in app server");
                return validEntries;
            }
            OffsetDateTime start = OffsetDateTime.parse(request.getFrom());
            OffsetDateTime end = OffsetDateTime.parse(request.getTo());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] whiteSpaceDelimitedEntries = line.trim().split("\\s");
                String dateTime = whiteSpaceDelimitedEntries[0];
                OffsetDateTime entryTime = OffsetDateTime.parse(dateTime);

                if (checkEntryTimeWithinRange(start, end, entryTime)) {
                    EntryPayload payloadEntry = new EntryPayload(dateTime, whiteSpaceDelimitedEntries[1],
                            UUID.fromString(whiteSpaceDelimitedEntries[2]));
                    validEntries.add(payloadEntry);
                }

                log.debug("Total entries parsed and filtered = {}", validEntries.size());
                if (checkEntryTimeBeyondEndTime(entryTime, end)) {
                    return validEntries;
                }
            }

        } catch (IOException | DateTimeParseException e) {
            log.error("Exception while parsing file entries: {}", e.toString());
        }

        return validEntries;
    }

    private boolean checkEntryTimeBeyondEndTime(OffsetDateTime entryTime, OffsetDateTime endTime) {
        return entryTime.isAfter(endTime);
    }

    private boolean checkEntryTimeWithinRange(OffsetDateTime start, OffsetDateTime end, OffsetDateTime entryTime) {
        return (entryTime.isAfter(start) || entryTime.isEqual(start)) &&
                (entryTime.isBefore(end) || entryTime.isEqual(end));
    }

}
