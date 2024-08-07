package com.example.translation.service;

import com.example.translation.model.TranslationRequest;
import com.example.translation.model.TranslationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TranslationService {

    @Value("${yandex.api.key}")
    private String apiKey;

    @Value("${yandex.folder.id}")
    private String folderId;

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public TranslationService(RestTemplate restTemplate, ExecutorService executorService) {
        this.restTemplate = restTemplate;
        this.executorService = executorService;
    }

    public TranslationResponse translate(String text, String sourceLang, String targetLang) {
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + apiKey);

        TranslationRequest request = new TranslationRequest();
        request.setFolderId(folderId);
        request.setTargetLanguageCode(targetLang);
        request.setSourceLanguageCode(sourceLang);
        request.setTexts(List.of(text));

        HttpEntity<TranslationRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<TranslationResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, TranslationResponse.class);

        return response.getBody();
    }

    public List<String> translateWords(List<String> words, String sourceLang, String targetLang) throws InterruptedException, ExecutionException {
        List<Callable<String>> tasks = new ArrayList<>();
        for (String word : words) {
            tasks.add(() -> {
                TranslationResponse response = translate(word, sourceLang, targetLang);
                if (response != null && !response.getTranslations().isEmpty()) {
                    return response.getTranslations().get(0).getText();
                } else {
                    return "No translation available";
                }
            });
        }

        List<Future<String>> results = executorService.invokeAll(tasks);

        List<String> translations = new ArrayList<>();
        for (Future<String> result : results) {
            translations.add(result.get());
        }

        return translations;
    }
}
