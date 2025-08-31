package com.sarah.utils;

import java.io.File;
import java.net.URL;

public class AudioDebugTest {
    public static void main(String[] args) {
        System.out.println("🔍 Testando localização dos arquivos de áudio...");

        String[] audioFiles = {
                "audio/Erro.wav",
                "audio/Nome_Diretorio.wav",
                "audio/Nome_Diretorio_Deletar.wav"
        };

        for (String file : audioFiles) {
            URL audioUrl = AudioDebugTest.class.getClassLoader().getResource(file);
            if (audioUrl == null) {
                System.err.println("❌ NÃO ENCONTRADO: " + file);
            } else {
                System.out.println("✅ ENCONTRADO: " + file);
                System.out.println("   📍 Localização: " + audioUrl.getPath());

                // Testa se pode ser lido como arquivo
                File audioFile = new File(audioUrl.getFile());
                if (audioFile.exists()) {
                    System.out.println("   📊 Tamanho: " + audioFile.length() + " bytes");
                } else {
                    System.err.println("   ❌ Arquivo não existe no sistema de arquivos");
                }
            }
            System.out.println("---");
        }

        // Teste de reprodução
        System.out.println("🔊 Testando reprodução...");
        AudioPlayerUtil.play("audio/Erro.wav");
    }
}

