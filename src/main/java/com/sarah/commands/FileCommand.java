package com.sarah.commands;

import main.java.br.com.filefacilete.view.InterfaceUsuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class FileCommand {
    private final String PATH = ("C:\\Users\\Microsoft\\Documents\\ProjetosJava\\FileFacilite\\src\\directoryToProject\\");
    InterfaceUsuario interfaceUsuarioUsuario = new InterfaceUsuario();


    public boolean createdFile(String directory, String file) {
        Path wayOfDirectory = Paths.get(PATH, directory.toLowerCase());
        Path wayOfFile = Paths.get(PATH, directory, file.toLowerCase());
        if (Files.exists(wayOfDirectory) && Files.isDirectory(wayOfDirectory)) {
            if (Files.notExists(wayOfFile)) {
                try {
                    Files.createFile(wayOfFile);
                    System.out.println("Arquivo criado com sucesso (" + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(wayOfFile));
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Diretório não existe");
        }
        System.out.println("Erro ao criar arquivo (" + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(wayOfFile) + ")");
        return false;
    }

    public boolean deleteFile(String directory, String file) {
        Path wayOfDirectory = Paths.get(PATH, directory.toLowerCase());
        Path wayOfDeleteFile = Paths.get(PATH, directory, file.toLowerCase());
        if (Files.exists(wayOfDirectory)) {

            if (Files.isRegularFile(wayOfDeleteFile)) {
                try {
                    Files.deleteIfExists(wayOfDeleteFile);
                    System.out.println("Arquivo excluído com sucesso (" + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(wayOfDeleteFile));
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("Erro ao excluir arquivo, arquivo nao existe (" + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(wayOfDeleteFile) + ")");
                return false;
            }
        }
        System.out.println("Erro ao excluir arquivo, diretorio nao existe (" + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(wayOfDeleteFile) + ")");
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
                System.out.println("O arquivo não existe ou não pode ser lido");
                return false;
            }
        }else {
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

                System.out.println("Lista de diretorios e arquivos : ");
                for (Path p : paths) {
                    System.out.println(p.toAbsolutePath());
                }

            } catch (IOException e) {
                System.out.println("Erro ao listar arquivos: " + e.getMessage());
            }
        } else {
            System.out.println("O diretório não existe ou não é um diretório válido.");
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
                    System.out.println("Erro ao acessar arquivo " + e.getMessage());
                    throw new RuntimeException(e);
                }

                System.out.println("---------------------------");
                System.out.println("Nome do arquivo: " + file);
                System.out.println(Files.isWritable(fileToRead) ? "Arquivo permite escrita" : "Arquivo não permite escrita");
                System.out.println(Files.isReadable(fileToRead) ? "Arquivo permite leitura" : "Arquivo não permite leitura");
                System.out.println("O arquivo pertence aos diretórios " + interfaceUsuarioUsuario.wayOfDirectoriesOrFiles(fileToRead));
                System.out.println("Data de criação do arquivo: " + simpleDateFormat.format(attributes.creationTime().toMillis()));
                System.out.println("Data do último acesso ao arquivo: " + simpleDateFormat.format(attributes.lastAccessTime().toMillis()));
                System.out.println("Tamanho do arquivo: " + attributes.size() +" Bytes");

                System.out.println("---------------------------");
            }else {
                System.out.println("O arquivo não existe ");
                return false;
            }
        }else {
            System.out.println("O diretório não existe ");
            return false;
        }
        return false;
    }
}
