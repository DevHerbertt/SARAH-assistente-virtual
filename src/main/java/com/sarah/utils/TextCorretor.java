package com.sarah.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TextCorretor {

    // Dicionário de palavras com erros de codificação e suas versões corretas
    private static final Map<String, String> corretorString = new HashMap<>();

    // Inicializa o dicionário com as substituições
    static {
        corretorString.put("educaÃ§Ã£o", "educação");
        corretorString.put("super-herÃ³is", "super-heróis");
        corretorString.put("prÃ³ximo", "próximo");
        corretorString.put("informaÃ§Ã£o", "informação");
        corretorString.put("pÃ¡gina", "página");
        corretorString.put("estaÃ§Ã£o", "estação");
        corretorString.put("mÃºsica", "música");
        corretorString.put("atenÃ§Ã£o", "atenção");
        corretorString.put("soluÃ§Ã£o", "solução");
        corretorString.put("ciÃªncia", "ciência");
        corretorString.put("alÃ´", "alô");
        corretorString.put("olÃ¡´", "Olá");
        corretorString.put("nÃ£o", "não");
        // Adicione mais palavras conforme necessário
    }

    // Método para corrigir o texto baseado no dicionário de substituições
    public String correctVocabulary(String text) {
        // Itera sobre as palavras do dicionário e realiza as substituições
        for (Map.Entry<String, String> entry : corretorString.entrySet()) {
            // Substitui cada palavra errada pela versão correta
            text = text.replaceAll("\\b" + Pattern.quote(entry.getKey()) + "\\b", entry.getValue());
        }
        return text;
    }


}
