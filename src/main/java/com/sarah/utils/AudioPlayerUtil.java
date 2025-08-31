package com.sarah.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayerUtil {

    public static void play(String audioResourcePath) {
        System.out.println("🎵 Tentando reproduzir: " + audioResourcePath);

        try {
            // Tenta carregar do classpath
            InputStream audioStream = AudioPlayerUtil.class.getClassLoader()
                    .getResourceAsStream(audioResourcePath);

            if (audioStream == null) {
                System.err.println("❌ Arquivo não encontrado no classpath: " + audioResourcePath);

                // Tenta como arquivo absoluto (para desenvolvimento)
                File audioFile = new File("src/main/resources/" + audioResourcePath);
                if (audioFile.exists()) {
                    System.out.println("✅ Encontrado como arquivo: " + audioFile.getAbsolutePath());
                    audioStream = new FileInputStream(audioFile);
                } else {
                    System.err.println("❌ Também não encontrado como arquivo");
                    return;
                }
            }

            System.out.println("✅ Arquivo carregado com sucesso");

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
            AudioFormat format = audioInputStream.getFormat();

            System.out.println("📊 Formato do áudio: " + format);

            // Converte para formato compatível se necessário
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                System.out.println("🔄 Convertendo formato de áudio...");
                AudioFormat targetFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        16,
                        format.getChannels(),
                        format.getChannels() * 2,
                        format.getSampleRate(),
                        false
                );
                audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
            }

            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());

            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("❌ Formato de áudio não suportado pelo sistema");
                return;
            }

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);

            System.out.println("▶️ Iniciando reprodução...");
            clip.start();

            // Aguarda o áudio terminar
            long duration = clip.getMicrosecondLength() / 1000;
            System.out.println("⏱️ Duração: " + duration + "ms");

            Thread.sleep(duration);

            clip.close();
            audioInputStream.close();
            audioStream.close();

            System.out.println("✅ Áudio reproduzido com sucesso!");

        } catch (UnsupportedAudioFileException e) {
            System.err.println("❌ Formato de arquivo não suportado: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("❌ Dispositivo de áudio ocupado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("❌ Erro de leitura do arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}