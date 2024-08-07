package com.example.translation.model;

import lombok.Data;

@Data
public class TranslationUser {
    private String ipAddress;
    private String sourceText;
    private String targetText;

    public TranslationUser(String ipAddress, String sourceText, String targetText) {
        this.ipAddress = ipAddress;
        this.sourceText = sourceText;
        this.targetText = targetText;
    }
}

