package com.sarah.core;

import com.sarah.commands.DirectoryCommand;
import com.sarah.utils.ComandVoiceMapper;
import com.sarah.utils.VoiceListenerUtil;

public class CommandRouter {
    private ComandVoiceMapper comandVoiceMapper = new ComandVoiceMapper();

    public void execute() {
        String phrase = VoiceListenerUtil.Listen();
        String command = comandVoiceMapper.identificationAction(phrase);

        switch (command){
            case "criar_pasta" ->{
                interfaceUsuario.acessPath(SCANNER, "Nome do diretório :"); //pergunta da Sarah via audio
                String directory = VoiceListenerUtil.Listen();
                boolean createdDirectory = DirectoryCommand.createdDirectory(directory);

            }
            case "deletar_pasta" ->{
                String deleteDirectory = interfaceUsuario.acessPath(SCANNER, "Nome do diretório para ser deletado :"); //pergunta da Sarah via audio
                directoryService.deleteDirectory(deleteDirectory);

            }
            case "criar_arquivo" ->{
                String directoryFile = interfaceUsuario.acessPath(SCANNER, "Nome do diretório que o arquivo será salvo  :"); //pergunta da Sarah via audio
                String file = interfaceUsuario.acessPath(SCANNER, "Nome do arquivo  :"); //pergunta da Sarah via audio
                fileService.createdFile(directoryFile, file);

            }
            case "ler_arquivo" ->{
                String directoryToFile = interfaceUsuario.acessPath(SCANNER, "Nome do diretório :"); //pergunta da Sarah via audio
                String fileRead = interfaceUsuario.acessPath(SCANNER, "Nome do arquivo que será lido :"); //pergunta da Sarah via audio
                fileService.readFile(directoryToFile, fileRead);

            }
            case "detalhes_arquivo" ->{
                String directoryToFile = interfaceUsuario.acessPath(SCANNER, "Nome do diretório :"); //pergunta da Sarah via audio
                String fileRead = interfaceUsuario.acessPath(SCANNER, "Nome do arquivo que será detalhado :"); //pergunta da Sarah via audio
                fileService.readFile(directoryToFile, fileRead);

            }
            case "listar_tudo" ->{
                //Sarah : listando arquivos
                fileService.listAllFilesAndDirectories();

            }
            default->{
                System.out.println("Sua escolha não foi reconhecida"); //Sarah responde por audio aqui

            }


            }
    }

}
