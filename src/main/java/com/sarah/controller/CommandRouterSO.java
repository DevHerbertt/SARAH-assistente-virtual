package com.sarah.controller;

import com.sarah.service.SO.BrowserService;
import com.sarah.service.System.SystemInfoService;
import com.sarah.service.System.SystemToolsService;
import com.sarah.service.fileAndDirectory.DirectoryService;
import com.sarah.service.fileAndDirectory.FileService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.QuestionAndResponderUtil;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
public class CommandRouterSO {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final BrowserService browserService = new BrowserService();
    private final SystemInfoService systemInfoService = new SystemInfoService();
    private final SystemToolsService systemToolsService = new SystemToolsService();


    public CommandRouterSO() {
        try {
            // Usar caminho relativo
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
            case "Gerenciador_Tarefas" -> {
                log.debug("Gerenciador_Tarefas : initialize");
                systemToolsService.openTaskManager();
                log.debug("Gerenciador_Tarefas: End" + LocalDateTime.now());
            }
            case "Configuracoes_Windows" -> {
                log.debug("Configuracoes_Windows : initialize");
                systemToolsService.openWindowsSettings();
                log.debug("Configuracoes_Windows: End" + LocalDateTime.now());
            }
            case "Prompt_Comando" -> {
                log.debug("Prompt_Comando : initialize");
                systemToolsService.openCommandPrompt();
                log.debug("Prompt_Comando: End" + LocalDateTime.now());
            }
            case "Explorador_Arquivos" -> {
                log.debug("Explorador_Arquivos : initialize");
                systemToolsService.openFileExplorer();
                log.debug("Explorador_Arquivos: End" + LocalDateTime.now());
            }
            case "Calculadora" -> {
                log.debug("Calculadora : initialize");
                systemToolsService.openCalculator();
                log.debug("Calculadora: End" + LocalDateTime.now());
            }
            case "Gerenciador_Dispositivos" -> {
                log.debug("Gerenciador_Dispositivos : initialize");
                systemToolsService.openDeviceManager();
                log.debug("Gerenciador_Dispositivos: End" + LocalDateTime.now());
            }
            case "Informacoes_Sistema" -> {
                log.debug("Informacoes_Sistema : initialize");
                systemToolsService.openSystemInfo();
                log.debug("Informacoes_Sistema: End" + LocalDateTime.now());
            }

            case "Info_CPU" -> {
                log.debug("Info_CPU : initialize");
                String cpuInfo = systemInfoService.getCpuInfo();
                System.out.println(cpuInfo);
                // Você pode adicionar síntese de voz aqui
                log.debug("Info_CPU: End" + LocalDateTime.now());
            }
            case "Info_Memoria" -> {
                log.debug("Info_Memoria : initialize");
                String memoryInfo = systemInfoService.getMemoryInfo();
                System.out.println(memoryInfo);
                log.debug("Info_Memoria: End" + LocalDateTime.now());
            }
            case "Info_Disco" -> {
                log.debug("Info_Disco : initialize");
                String diskInfo = systemInfoService.getDiskInfo();
                System.out.println(diskInfo);
                log.debug("Info_Disco: End" + LocalDateTime.now());
            }
            case "Info_Sistema" -> {
                log.debug("Info_Sistema : initialize");
                String systemInfo = systemInfoService.getSystemInfo();
                System.out.println(systemInfo);
                log.debug("Info_Sistema: End" + LocalDateTime.now());
            }
            case "Info_Rede" -> {
                log.debug("Info_Rede : initialize");
                String networkInfo = systemInfoService.getNetworkInfo();
                System.out.println(networkInfo);
                log.debug("Info_Rede: End" + LocalDateTime.now());
            }
            case "Info_PlacaVideo" -> {
                log.debug("Info_PlacaVideo : initialize");
                String graphicsInfo = systemInfoService.getGraphicsInfo();
                System.out.println(graphicsInfo);
                log.debug("Info_PlacaVideo: End" + LocalDateTime.now());
            }
            case "Info_Completa" -> {
                log.debug("Info_Completa : initialize");
                String completeInfo = systemInfoService.getCompleteSystemInfo();
                System.out.println(completeInfo);
                log.debug("Info_Completa: End" + LocalDateTime.now());
            }
            case "sair" -> {
                log.info("🛑 Comando de saída recebido. Encerrando Sarah...");
                System.exit(0);
            }
            default -> {
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.ERRO);
                log.info("❌ Comando não reconhecido: " + phrase);
            }
        }
    }
}