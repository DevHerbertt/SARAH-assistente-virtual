package com.sarah.controller;

import com.sarah.service.SO.BrowserService;
import com.sarah.service.System.SystemInfoService;
import com.sarah.service.System.SystemToolsService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.QuestionAndResponderUtil;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
public class CommandRouterSO {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final BrowserService browserService;
    private final SystemInfoService systemInfoService;
    private final SystemToolsService systemToolsService;

    // ✅ INJETA BrowserService
    public CommandRouterSO(BrowserService browserService, SystemInfoService systemInfoService, SystemToolsService systemToolsService) {
        this.browserService = browserService;
        this.systemInfoService = systemInfoService;
        this.systemToolsService = systemToolsService;

        try {
            String jsonPath = "src/main/resources/comandos.json";
            comandVoiceMapper = new ComandVoiceMapperUtil(jsonPath);
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

        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "Painel_Controle" -> {
                log.debug("Painel_Controle : initialize");
                systemToolsService.openControlPanel();
                log.debug("Painel_Controle: End" + LocalDateTime.now());
            }
            // ... outros casos
        }
    }
}