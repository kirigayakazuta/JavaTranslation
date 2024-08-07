package com.example.translation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.example.translation.service.TranslationService;
import com.example.translation.dao.TranslationDAO;
import com.example.translation.model.TranslationUser;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class TranslationController {
    private final TranslationService translationService;
    private final TranslationDAO translationDAO;
    private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

    @Autowired
    public TranslationController(TranslationService translationService, TranslationDAO translationDAO) {
        this.translationService = translationService;
        this.translationDAO = translationDAO;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/translate")
    public String translate(@RequestParam String text, @RequestParam String sourceLang, @RequestParam String targetLang, Model model, HttpServletRequest request) {
        try {
            List<String> words = List.of(text.split("\\s+"));
            List<String> translations = translationService.translateWords(words, sourceLang, targetLang);
            model.addAttribute("translation", String.join(" ", translations));
            translationDAO.saveUser(new TranslationUser(getClientIp(request), text, String.join(" ", translations)));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error during translation", e);
            model.addAttribute("translation", "Error during translation");
        }
        return "index";
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}