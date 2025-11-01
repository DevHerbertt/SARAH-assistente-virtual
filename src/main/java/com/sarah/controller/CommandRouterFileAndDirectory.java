package com.sarah.controller;

import com.sarah.service.fileAndDirectory.DirectoryService;
import com.sarah.service.fileAndDirectory.FileService;
import com.sarah.service.memories.MemoriesCommandService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.InputDialogUtil;
import com.sarah.utils.QuestionAndResponderUtil;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CommandRouterFileAndDirectory {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final DirectoryService directoryService;
    private final FileService fileService;
    private final MemoriesCommandService memoriesCommandService;

    // Injeta MemoriesCommandService via construtor
    public CommandRouterFileAndDirectory(MemoriesCommandService memoriesCommandService) {
        this.memoriesCommandService = memoriesCommandService;
        this.directoryService = new DirectoryService();
        this.fileService = new FileService();

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

        // Agora não será null

        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "criar_pasta" -> {
                log.debug("criar_pasta : initialize");
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_DIRETORIO);
                String directoryName = InputDialogUtil.execute("Nome da Pasta ");
                directoryService.createdDirectory(directoryName);
                log.debug("Pasta criada: " + directoryName);
            }
            case "deletar_pasta" -> {
                log.debug("deletar_pasta : initialize");
                 QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_DIRETORIO_DELETAR);
                String directoryName = InputDialogUtil.execute("Nome da Pasta ");
                directoryService.deleteDirectory(directoryName);
                log.debug("Pasta deletada: " + directoryName);
            }
            case "criar_arquivo" -> {
                log.debug("criar_arquivo : initialize");
                String directory = QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_DIRETORIO_ARQUIVO_SALVARA);
//                String fileName = QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_ARQUIVO);
//                fileService.createdFile(directory, fileName);
//                log.debug("Arquivo criado: " + fileName + " em " + directory);
            }
            case "ler_arquivo" -> {
                log.debug("ler_arquivo : initialize");
                String directory = QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.DIRECTORY_LER_ARQUIVO);
//                String file = QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.);
//                fileService.readFile(directory, file);
//                log.debug("Arquivo lido: " + file + " de " + directory);
            }
            case "detalhes_arquivo" -> {
                log.debug("detalhes_arquivo : initialize");
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_DIRETORIO);
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.NOME_ARQUIVO_DETALHES);
                String directoryName = InputDialogUtil.execute("Nome da Pasta ");
                String file = InputDialogUtil.execute("Nome do arquivo ");
                fileService.verificationOfFile(directoryName, file);
                log.debug("Detalhes do arquivo: " + file + " em " + directoryName);
            }
            case "listar_tudo" -> {
                log.debug("listar_tudo : initialize");
                QuestionAndResponderUtil.askAndListen(VoiceResponderDefault.LISTAR_ARQUIVOS);
                fileService.listAllFilesAndDirectories();
                log.debug("Listagem concluída para: ");
            }
            case "sair" -> {
                log.info("🛑 Comando de saída recebido. Encerrando Sarah...");
                System.exit(0);
            }
            default -> {
                log.info("❌ Comando não reconhecido: " + phrase);
            }
        }
    }
}