package com.sarah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandVoiceMapper {
    private final Map<String, List<String>> commands;

    public ComandVoiceMapper() {
        this.commands = carregarComandos();
    }


    private Map<String, List<String>> carregarComandos() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/comandos.json");
            return mapper.readValue(file, Map.class);
        } catch (Exception e) {
            System.err.println("Erro ao carregar comandos de voz: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public String identificationAction(String speech) {
        speech = speech.toLowerCase();
        for (Map.Entry<String, List<String>> entry : commands.entrySet()) {
            for (String phrase : entry.getValue()) {
                if (speech.contains(phrase.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return "comando_desconhecido";
    }
}
