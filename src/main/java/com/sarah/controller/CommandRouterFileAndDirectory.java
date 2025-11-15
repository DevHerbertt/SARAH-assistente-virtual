package com.sarah.controller;

import com.sarah.service.fileAndDirectory.DirectoryService;
import com.sarah.service.fileAndDirectory.FileService;
import com.sarah.service.memories.MemoriesCommandService;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.InputDialogUtil;
import com.sarah.voice.VoiceResponder;
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

            return;
        }

        memoriesCommandService.updateRepetition(command);
        System.out.println("🎯 Comando identificado: " + command);

        switch (command) {
            case "criar_pasta" -> {
                log.debug("criar_pasta : initialize");
                VoiceResponder.askAndListen(VoiceResponderDefault.NOME_DIRETORIO);
                String directoryName = InputDialogUtil.execute("Nome da Pasta ");
                directoryService.createdDirectory(directoryName);
                log.debug("Pasta criada: " + directoryName);
            }
            case "deletar_pasta" -> {
                log.debug("deletar_pasta : initialize");
                VoiceResponder.askAndListen(VoiceResponderDefault.NOME_DIRETORIO_DELETAR);
                String directoryName = InputDialogUtil.execute("Nome da Pasta ");
                directoryService.deleteDirectory(directoryName);
                log.debug("Pasta deletada: " + directoryName);
            }
            case "criar_arquivo" -> {
                log.debug("criar_arquivo : initialize");
                VoiceResponder.askAndListen(VoiceResponderDefault.NOME_DIRETORIO_ARQUIVO_SALVARA);
                String directory = InputDialogUtil.execute("Diretório para salvar ");
                VoiceResponder.askAndListen(VoiceResponderDefault.NOME_ARQUIVO);
                String fileName = InputDialogUtil.execute("Nome do arquivo ");
                fileService.createdFile(directory, fileName);
                log.debug("Arquivo criado: " + fileName + " em " + directory);
            }
            case "deletar_arquivo" -> {
                log.debug("deletar_arquivo : initialize");
                // Usando InputDialog diretamente sem áudio específico
                String directory = InputDialogUtil.execute("Diretório onde está o arquivo para deletar");
                String fileName = InputDialogUtil.execute("Nome do arquivo para deletar");
                fileService.deleteFile(directory, fileName);
                log.debug("Arquivo deletado: " + fileName + " de " + directory);
            }
            case "ler_arquivo" -> {
                log.debug("ler_arquivo : initialize");
                VoiceResponder.askAndListen(VoiceResponderDefault.DIRECTORY_LER_ARQUIVO);
                String directory = InputDialogUtil.execute("Diretório do arquivo ");
                VoiceResponder.askAndListen(VoiceResponderDefault.NOME_ARQUIVO_LER);
                String fileName = InputDialogUtil.execute("Nome do arquivo ");
                fileService.readFile(directory, fileName);
                log.debug("Arquivo lido: " + fileName + " de " + directory);
            }
            case "detalhes_arquivo" -> {
                log.debug("detalhes_arquivo : initialize");
                // Usando InputDialog diretamente sem áudio específico
                String directoryName = InputDialogUtil.execute("Diretório do arquivo para ver detalhes");
                String fileName = InputDialogUtil.execute("Nome do arquivo para ver detalhes");
                fileService.verificationOfFile(directoryName, fileName);
                log.debug("Detalhes do arquivo: " + fileName + " em " + directoryName);
            }
            case "listar_tudo" -> {
                log.debug("listar_tudo : initialize");
                VoiceResponder.askAndListen(VoiceResponderDefault.LISTAR_ARQUIVOS);
                String directory = InputDialogUtil.execute("Diretório para listar ");
                fileService.listAllFilesAndDirectories();
                log.debug("Listagem concluída para: " + directory);
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