package com.sarah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandVoiceMapperUtil {
    private final Map<String, List<String>> commandMap = new HashMap<>();

    // Construtor que carrega o JSON
    public ComandVoiceMapperUtil(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> map = mapper.readValue(new File(jsonFilePath), Map.class);

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> lowerCaseList = new ArrayList<>();
            for (String phrase : entry.getValue()) {
                lowerCaseList.add(phrase.toLowerCase());
            }
            commandMap.put(entry.getKey(), lowerCaseList);
        }
    }

    public String identifyCommand(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            return null;
        }

        String lowerPhrase = phrase.toLowerCase();
        for (Map.Entry<String, List<String>> entry : commandMap.entrySet()) {
            for (String variant : entry.getValue()) {
                if (lowerPhrase.contains(variant)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}