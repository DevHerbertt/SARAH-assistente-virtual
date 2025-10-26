package com.sarah.controller;

import com.sarah.service.fileAndDirectory.DirectoryService;
import com.sarah.service.fileAndDirectory.FileService;
import com.sarah.users.AdminUser;
import com.sarah.users.User;
import com.sarah.users.UserManager;
import com.sarah.utils.ComandVoiceMapperUtil;
import com.sarah.utils.InputDialogUtil;
import com.sarah.utils.QuestionAndResponderUtil;
import com.sarah.voice.VoiceResponderDefault;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandRouterFileAndDirectory {
    private final ComandVoiceMapperUtil comandVoiceMapper;
    private final DirectoryService directoryService = new DirectoryService();
    private final FileService fileService = new FileService();


    public CommandRouterFileAndDirectory() {

        try {
            // Ajuste o caminho do JSON
            comandVoiceMapper = new ComandVoiceMapperUtil("C:\\Users\\Microsoft\\SARAH-assistente-virtual\\src\\main\\resources\\comandos.json");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar mapeamento de comandos", e);
        }
    }

    public void execute(String phrase) {


//        if (!(adminUser instanceof AdminUser || adminUser instanceof UserManager)) {
//            log.error("🚫 Acesso negado. Você não tem permissão para executar este comando.");
//            return;
//        }
        if (phrase == null || phrase.isBlank() || phrase.equals("{}")) {
            log.warn("Frase vazia ou inválida recebida: " + phrase);
            return;
        }

        String command = comandVoiceMapper.identifyCommand(phrase);

        if (command == null) {
            log.info("Nenhum comando reconhecido para: " + phrase);
            return;
        }


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