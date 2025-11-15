package com.sarah.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ComandVoiceMapperUtil {
    private final Map<String, List<String>> commandMap;

    // ✅ Construtor que aceita InputStream
    public ComandVoiceMapperUtil(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        commandMap = mapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {});
    }

    // ✅ Construtor que aceita String (caminho do arquivo) - mantém compatibilidade
    public ComandVoiceMapperUtil(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        commandMap = mapper.readValue(new File(filePath), new TypeReference<Map<String, List<String>>>() {});
    }

    public String identifyCommand(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            return null;
        }

        String normalizedPhrase = phrase.toLowerCase().trim();

        for (Map.Entry<String, List<String>> entry : commandMap.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (normalizedPhrase.contains(keyword.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}