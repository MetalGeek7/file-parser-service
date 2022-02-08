package com.rokt.assessment.fileprocessor.models;

import lombok.Data;

@Data
public class EntryFilterRequest {
    final String filename;
    final String from;
    final String to;

    public EntryFilterRequest(String fileName, String fromDate, String toDate) {
        this.filename = fileName;
        this.from = fromDate;
        this.to = toDate;
    }

    @Override
    public String toString() {
        return "{\"filename\":\"" + filename + "\"" +
                ", \"from\":\"" + from + "\"" +
                ", \"to\":\"" + to + "\"" +
                "}";
    }
}
