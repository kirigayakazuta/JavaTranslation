package com.example.translation.model;

import lombok.Data;

import java.util.List;

@Data
public class TranslationRequest {
    private String folderId;
    private String targetLanguageCode;
    private String sourceLanguageCode;
    private List<String> texts;
}

