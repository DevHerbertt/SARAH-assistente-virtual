package com.sarah.controller;

import com.sarah.service.SO.BrowserService;
import com.sarah.service.System.SystemInfoService;
import com.sarah.service.System.SystemToolsService;
import com.sarah.service.memories.MemoriesCommandService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.voice.VoiceResponder;
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
    private final MemoriesCommandService memoriesCommandService;

    public CommandRouterSO(BrowserService browserService, SystemInfoService systemInfoService, SystemToolsService systemToolsService,MemoriesCommandService memoriesCommandService) {
        this.browserService = browserService;
        this.systemInfoService = systemInfoService;
        this.systemToolsService = systemToolsService;
        this.memoriesCommandService = memoriesCommandService;

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
            VoiceResponder.askAndListen(VoiceResponderDefault.ERRO);
            return;
        }

        memoriesCommandService.updateRepetition(command);
        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "Painel_Controle" -> {
                log.debug("Painel_Controle: initialize");
                systemToolsService.openControlPanel();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Painel_Controle: End " + LocalDateTime.now());
            }
            case "Gerenciador_Tarefas" -> {
                log.debug("Gerenciador_Tarefas: initialize");
                systemToolsService.openTaskManager();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Gerenciador_Tarefas: End " + LocalDateTime.now());
            }
            case "Configuracoes_Windows" -> {
                log.debug("Configuracoes_Windows: initialize");
                systemToolsService.openWindowsSettings();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Configuracoes_Windows: End " + LocalDateTime.now());
            }
            case "Prompt_Comando" -> {
                log.debug("Prompt_Comando: initialize");
                systemToolsService.openCommandPrompt();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Prompt_Comando: End " + LocalDateTime.now());
            }
            case "Explorador_Arquivos" -> {
                log.debug("Explorador_Arquivos: initialize");
                systemToolsService.openFileExplorer();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Explorador_Arquivos: End " + LocalDateTime.now());
            }
            case "Calculadora" -> {
                log.debug("Calculadora: initialize");
                systemToolsService.openCalculator();
                VoiceResponder.askAndListen(VoiceResponderDefault.EXECUTANDO);
                log.debug("Calculadora: End " + LocalDateTime.now());
            }
            case "Gerenciador_Dispositivos" -> {
                log.debug("Gerenciador_Dispositivos: initialize");
                systemToolsService.openDeviceManager();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Gerenciador_Dispositivos: End " + LocalDateTime.now());
            }
            case "Informacoes_Sistema" -> {
                log.debug("Informacoes_Sistema: initialize");
                systemToolsService.openSystemInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Informacoes_Sistema: End " + LocalDateTime.now());
            }
            case "Info_CPU" -> {
                log.debug("Info_CPU: initialize");
                systemInfoService.getCpuInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_CPU: End " + LocalDateTime.now());
            }
            case "Info_Memoria" -> {
                log.debug("Info_Memoria: initialize");
                systemInfoService.getMemoryInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_Memoria: End " + LocalDateTime.now());
            }
            case "Info_Disco" -> {
                log.debug("Info_Disco: initialize");
                systemInfoService.getDiskInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_Disco: End " + LocalDateTime.now());
            }
            case "Info_Sistema" -> {
                log.debug("Info_Sistema: initialize");
                systemInfoService.getSystemInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_Sistema: End " + LocalDateTime.now());
            }
            case "Info_Rede" -> {
                log.debug("Info_Rede: initialize");
                systemInfoService.getNetworkInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_Rede: End " + LocalDateTime.now());
            }
            case "Info_PlacaVideo" -> {
                log.debug("Info_PlacaVideo: initialize");
                systemInfoService.getGraphicsInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_PlacaVideo: End " + LocalDateTime.now());
            }
            case "Info_Completa" -> {
                log.debug("Info_Completa: initialize");
                systemInfoService.getCompleteSystemInfo();
                VoiceResponder.askAndListen(VoiceResponderDefault.PEDIDO_ENTENDIDO);
                log.debug("Info_Completa: End " + LocalDateTime.now());
            }
            case "sair" -> {
                log.info("🛑 Comando de saída recebido. Encerrando Sarah...");
                VoiceResponder.askAndListen(VoiceResponderDefault.FINALIZANDO);
                System.exit(0);
            }
            default -> {
                log.warn("Comando não implementado: " + command);

            }
        }
    }
}