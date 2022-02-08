package com.rokt.assessment.fileprocessor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EntryPayload {

    String eventTime;
    String emailId;
    UUID sessionId;
}
