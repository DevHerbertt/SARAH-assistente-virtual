package com.sarah.controller;

import com.sarah.service.SO.BrowserService;
import com.sarah.service.SO.SpotifyService;
import com.sarah.service.memories.MemoriesCommandService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.voice.VoiceResponder;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class CommandSpotify {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final BrowserService browserService;
    private final MemoriesCommandService memoriesCommandService;
    private final SpotifyService spotifyService = new SpotifyService();

    public CommandSpotify(MemoriesCommandService memoriesCommandService, BrowserService browserService) {
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
            return;
        }

        memoriesCommandService.updateRepetition(command);
        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "abrir_spotify" -> {
                log.debug("abrir_spotify : initialize");
                spotifyService.openSpotify();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
            }
            case "tocar_musica" -> {
                log.debug("tocar_musica : initialize");
                spotifyService.playPause();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
            }
            case "proxima_musica" -> {
                log.debug("proxima_musica : initialize");
                spotifyService.nextTrack();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
            }
            case "musica_anterior" -> {
                log.debug("musica_anterior : initialize");
                spotifyService.previousTrack();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
            }
            default -> {
                log.info("Comando não reconhecido: " + phrase);
            }
        }
    }
}
