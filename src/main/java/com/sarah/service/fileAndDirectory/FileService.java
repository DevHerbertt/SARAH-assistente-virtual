package com.sarah.service.fileAndDirectory;

import lombok.extern.log4j.Log4j2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class FileService {
    private final String PATH = ("C:\\Users\\Microsoft\\Documents\\ProjetosJava\\FileFacilite\\src\\directoryToProject\\");



    public boolean createdFile(String directory, String file) {
        Path wayOfDirectory = Paths.get(PATH, directory.toLowerCase());
        Path wayOfFile = Paths.get(PATH, directory, file.toLowerCase());
        if (Files.exists(wayOfDirectory) && Files.isDirectory(wayOfDirectory)) {
            if (Files.notExists(wayOfFile)) {
                try {
                    Files.createFile(wayOfFile);
                    /* Add audio here */
                    log.info("Arquivo criado com sucesso ({})",wayOfFile);
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            /* Add audio here */
            log.error("Diretório não existe ({})",wayOfDirectory);
        }
        /* Add audio here */
        log.error("Erro ao criar arquivo ({}}",wayOfFile);
        return false;
    }

    public boolean deleteFile(String directory, String file) {
        Path wayOfDirectory = Paths.get(PATH, directory.toLowerCase());
        Path wayOfDeleteFile = Paths.get(PATH, directory, file.toLowerCase());
        if (Files.exists(wayOfDirectory)) {

            if (Files.isRegularFile(wayOfDeleteFile)) {
                try {
                    Files.deleteIfExists(wayOfDeleteFile);
                    /* Add audio here */
                    log.info("Arquivo excluído com sucesso ({})",wayOfDeleteFile );
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                /* Add audio here */
                log.error("Erro ao excluir arquivo, arquivo nao existe ({})",wayOfDeleteFile );
                return false;
            }
        }
        /* Add audio here */
        log.error("Erro ao excluir arquivo, diretorio nao existe ({})", wayOfDirectory);
        return false;
    }

    public boolean readFile(String diretorio, String file){
        Path directory = Paths.get(PATH, diretorio.toLowerCase());
        Path fileToRead = Paths.get(PATH, diretorio, file.toLowerCase());
        if (Files.exists(directory) && Files.isDirectory(directory)){
            if (Files.exists(fileToRead) && Files.isReadable(fileToRead)){
                try {

                    System.out.println("---------------------------------------");
                    System.out.println( Files.readString(fileToRead));
                    System.out.println("---------------------------------------------");
                    System.out.println("Escrita do arquivo acima ");
                } catch (IOException e) {
                    System.out.println("Ocorreu um erro");
                    throw new RuntimeException(e);
                }
            }else {
                /* Add audio here */
                System.out.println("O arquivo não existe ou não pode ser lido");
                return false;
            }
        }else {
            /* Add audio here */
            System.out.println("O diretório não existe ");
            return false;
        }
        return false;
    }
    public void listAllFilesAndDirectories() {
        Path path = Paths.get(PATH);
        if (Files.exists(path) && Files.isDirectory(path)) {
            try {
                List<Path> paths = Files.walk(path)
                        .collect(Collectors.toList());

                /* Add audio here */
                log.info("Lista de diretorios e arquivos : ");
                for (Path p : paths) {
                    System.out.println(p.toAbsolutePath());
                }

            } catch (IOException e) {
                /* Add audio here */
                log.error("Erro ao listar arquivos: {}", e.getMessage());
            }
        } else {
            /* Add audio here */
            log.error("O diretório não existe ou não é um diretório válido.");
        }
    }

    public boolean verificationOfFile(String diretorio, String file) {
        Path directory = Paths.get(PATH, diretorio.toLowerCase());
        Path fileToRead = Paths.get(PATH, diretorio, file.toLowerCase());
        BasicFileAttributes attributes;
        SimpleDateFormat simpleDateFormat;



        if (Files.exists(directory) && Files.isDirectory(directory)) {
            if (Files.exists(fileToRead) && Files.isReadable(fileToRead)) {

                try {
                    attributes = Files.readAttributes(fileToRead, BasicFileAttributes.class);
                    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                } catch (IOException e) {
                    log.error( "Erro ao acessar arquivo: {} ", e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                /* Add audio here */
                log.info("---------------------------");
                log.info("Nome do arquivo: " + file);
                log.info(Files.isWritable(fileToRead) ? "Arquivo permite escrita" : "Arquivo não permite escrita");
                log.info(Files.isReadable(fileToRead) ? "Arquivo permite leitura" : "Arquivo não permite leitura");
                log.info("Data de criação do arquivo: " + simpleDateFormat.format(attributes.creationTime().toMillis()));
                log.info("Data do último acesso ao arquivo: " + simpleDateFormat.format(attributes.lastAccessTime().toMillis()));
                log.info("Tamanho do arquivo: " + attributes.size() + " Bytes");
                log.info("---------------------------");

            } else {
                /* Add audio here */
                log.warn("O arquivo não existe");
                return false;
            }
        } else {
            /* Add audio here */
            log.warn("O diretório não existe");
            return false;
        }
        return false;
    }
}
