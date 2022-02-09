package com.rokt.assessment.fileprocessor.controller;


import com.rokt.assessment.fileprocessor.models.EntryFilterRequest;
import com.rokt.assessment.fileprocessor.models.EntryPayload;
import com.rokt.assessment.fileprocessor.service.FileProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/")
public class ApplicationController {

    private final FileProcessingService fileProcessingService;

    public ApplicationController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<EntryPayload> getEntries(@RequestBody EntryFilterRequest requestBody) {
        log.debug("{}", requestBody);
        return fileProcessingService.getEntriesWithinTimeRange(requestBody);
    }

    @GetMapping("/")
    public String home() {
        return "Hello Users!!";
    }
}
