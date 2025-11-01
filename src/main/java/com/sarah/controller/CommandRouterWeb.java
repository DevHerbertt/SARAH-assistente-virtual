package com.sarah.controller;

import com.sarah.service.SO.BrowserService;
import com.sarah.service.memories.MemoriesCommandService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.QuestionAndResponderUtil;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
public class CommandRouterWeb {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final BrowserService browserService;
    private final MemoriesCommandService memoriesCommandService;

    public CommandRouterWeb(MemoriesCommandService memoriesCommandService, BrowserService browserService) {
        this.memoriesCommandService = memoriesCommandService;
        this.browserService = browserService;

        try {
            String jsonPath = "src/main/resources/comandos.json";
            this.comandVoiceMapper = new ComandVoiceMapperUtil(jsonPath);
            System.out.println("✅ Comandos carregados de: " + jsonPath);
        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar comandos: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar mapeamento de comandos", e);
        }
    }

    public void execute(String phrase) {
        if (phrase == null || phrase.isBlank() || phrase.equals("{}")) {
            log.warn("Frase vazia ou inválida recebida: " + phrase);
            return;
        }

        System.out.println("🔍 Identificando comando para: " + phrase);
        String command = comandVoiceMapper.identifyCommand(phrase);

        if (command == null) {
            log.info("Nenhum comando reconhecido para: " + phrase);
            QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.ERRO);
            return;
        }

        memoriesCommandService.updateRepetition(command);
        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "Abrindo_Navegador" -> {
                log.debug("Abrindo_Navegador : initialize");
                browserService.openBrowser("https://www.google.com/");
                log.debug("Abrindo_Navegador: End" + LocalDateTime.now());
            }
            case "Abrir_Youtube" -> {
                log.debug("Abrir_Youtube : initialize");
                browserService.openBrowser("https://www.youtube.com/");
                log.debug("Abrir_Youtube: End" + LocalDateTime.now());
            }
            case "Abrir_Instagram" -> {
                log.debug("Abrir_Instagram : initialize");
                browserService.openBrowser("https://www.instagram.com/");
                log.debug("Abrir_Instagram: End" + LocalDateTime.now());
            }
            case "Abrir_Facebook" -> {
                log.debug("Abrir_Facebook : initialize");
                browserService.openBrowser("https://www.facebook.com/");
                log.debug("Abrir_Facebook: End" + LocalDateTime.now());
            }
            case "Abrir_Gmail" -> {
                log.debug("Abrir_Gmail : initialize");
                browserService.openBrowser("https://mail.google.com/");
                log.debug("Abrir_Gmail: End" + LocalDateTime.now());
            }
            case "Abrir_Noticias" -> {
                log.debug("Abrir_Noticias : initialize");
                browserService.openBrowser("https://news.google.com/");
                log.debug("Abrir_Noticias: End" + LocalDateTime.now());
            }
            case "Abrir_Maps" -> {
                log.debug("Abrir_Maps : initialize");
                browserService.openBrowser("https://www.google.com/maps");
                log.debug("Abrir_Maps: End" + LocalDateTime.now());
            }
            default -> {
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.ERRO);
                log.info("❌ Comando não reconhecido: " + phrase);
            }
        }
    }
}