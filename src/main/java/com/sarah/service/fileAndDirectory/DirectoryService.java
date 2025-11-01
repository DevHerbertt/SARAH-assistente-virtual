package com.sarah.service.fileAndDirectory;



import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Log4j2
@Component
public class DirectoryService {
    private final static String path = ("C:\\Users\\Microsoft\\Documents\\ProjetosJava\\FileFacilite\\src\\directoryToProject\\");
    public  void createdDirectory( String directory){
        Path way = Paths.get(path,directory.toLowerCase());
        if (Files.notExists(way) ){
            try {
                Files.createDirectory(way);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
           log.info("criado com Sucesso");

        }else {
            log.error("Diretorio ja existe");
        }

    }
    public  boolean deleteDirectory(String directory){
        Path way = Paths.get(path, directory.toLowerCase());
        if (Files.exists(way) && Files.isDirectory(way)){
            try {
                Files.delete(way);
            } catch (IOException e) {
                System.out.println("Erro");
                throw new RuntimeException(e);
            }
            log.debug("Diretorio excluido  ( {} ) ", way.toAbsolutePath() );
            return true;

        }else {
            log.warn("Diretorio não existe");
            return false;
        }
    }
}
