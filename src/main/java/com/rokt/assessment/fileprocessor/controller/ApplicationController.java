package com.rokt.assessment.fileprocessor.controller;


import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.models.EntryPayload;
import com.rokt.assessment.fileprocessor.process.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/")
public class ApplicationController {

    private final FileProcessor fileProcessor;

    public ApplicationController(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<EntryPayload> getEntries(@RequestBody EntryFilterRequest requestBody) {
        log.debug("{}", requestBody);
        return fileProcessor.getEntriesWithinTimeRange(requestBody);
    }

    @GetMapping("/")
    public String home() {
        return "Hello Users!!";
    }
}
