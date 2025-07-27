package com.sarah.utils;

import java.util.HashMap;
import java.util.Map;

public class TextCorretorUtil {

    // Dicionário de palavras com erros de codificação e suas versões corretas
    private static final Map<String, String> corretorString = new HashMap<>();

    // Inicializa o dicionário com as substituições
    static {
        corretorString.put("coraÃ§Ã£o", "coração");
        corretorString.put("aÃ§Ã£o", "ação");
        corretorString.put("divulgaÃ§Ã£o", "divulgação");
        corretorString.put("associaÃ§Ã£o", "associação");
        corretorString.put("maÃ§Ã£", "maçã");
        corretorString.put("informaÃ§Ã£o", "informação");
        corretorString.put("avaliaÃ§Ã£o", "avaliação");
        corretorString.put("exceÃ§Ã£o", "exceção");
        corretorString.put("percepÃ§Ã£o", "percepção");
        corretorString.put("correÃ§Ã£o", "correção");
        corretorString.put("situaÃ§Ã£o", "situação");
        corretorString.put("soluÃ§Ã£o", "solução");
        corretorString.put("descriÃ§Ã£o", "descrição");
        corretorString.put("cabeÃ§alho", "cabeçalho");
        corretorString.put("balanÃ§a", "balança");
        corretorString.put("confecÃ§Ã£o", "confecção");
        corretorString.put("fÃ¡cil", "fácil");
        corretorString.put("precisÃ£o", "precisão");
        corretorString.put("sacrifÃ­cio", "sacrifício");
        corretorString.put("aceleraÃ§Ã£o", "aceleração");
        corretorString.put("decisÃ£o", "decisão");
        corretorString.put("pÃ¡ssaro", "pássaro");
        corretorString.put("intenÃ§Ã£o", "intenção");
        corretorString.put("instruÃ§Ã£o", "instrução");
        corretorString.put("bÃªnÃ§Ã£o", "bênção");
        corretorString.put("caÃ§ador", "caçador");
        corretorString.put("exclusÃ£o", "exclusão");
        corretorString.put("citaÃ§Ã£o", "citação");
        corretorString.put("ficÃ§Ã£o", "ficção");
        corretorString.put("gravaÃ§Ã£o", "gravação");
        corretorString.put("forÃ§a", "força");
        corretorString.put("restriÃ§Ã£o", "restrição");
        corretorString.put("restauraÃ§Ã£o", "restauração");
        corretorString.put("decoraÃ§Ã£o", "decoração");
        corretorString.put("aÃ§úcar", "açúcar");
        corretorString.put("projeÃ§Ã£o", "projeção");
        corretorString.put("grÃ¡fico", "gráfico");
        corretorString.put("preocupaÃ§Ã£o", "preocupação");
        corretorString.put("oraÃ§Ã£o", "oração");
        corretorString.put("fundaÃ§Ã£o", "fundação");
        corretorString.put("dÃºvida", "dúvida");
        corretorString.put("combinaÃ§Ã£o", "combinação");
        corretorString.put("estratÃ©gia", "estratégia");
        corretorString.put("atenÃ§Ã£o", "atenção");
        corretorString.put("justiÃ§a", "justiça");
        corretorString.put("confusÃ£o", "confusão");
        corretorString.put("assistÃªncia", "assistência");
        corretorString.put("compresÃ£o", "compressão");
        corretorString.put("indicaÃ§Ã£o", "indicação");
        corretorString.put("tensÃ£o", "tensão");
        corretorString.put("cÃ©lula", "célula");
        corretorString.put("inscriÃ§Ã£o", "inscrição");
        corretorString.put("interaÃ§Ã£o", "interação");
        corretorString.put("qualificaÃ§Ã£o", "qualificação");
        corretorString.put("caminhÃ£o", "caminhão");
        corretorString.put("atuaÃ§Ã£o", "atuação");
        corretorString.put("criaÃ§Ã£o", "criação");
        corretorString.put("interrupÃ§Ã£o", "interrupção");
        corretorString.put("dedicaÃ§Ã£o", "dedicação");
        corretorString.put("duraÃ§Ã£o", "duração");
        corretorString.put("explicaÃ§Ã£o", "explicação");
        corretorString.put("substituiÃ§Ã£o", "substituição");
    }

    public String correctVocabulary(String text) {
        for (Map.Entry<String, String> entry : corretorString.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());

        }
        return text;
    }


}
