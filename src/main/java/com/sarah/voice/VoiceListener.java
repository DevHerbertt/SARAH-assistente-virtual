package com.sarah.voice;

import com.sarah.controller.CommandRouter;
import com.sarah.utils.TextCorretorUtil;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

public class VoiceListener {

    private static final Logger logger = Logger.getLogger(VoiceListener.class.getName());

    private final Model model;
    private final Recognizer recognizer;
    private TargetDataLine microphone;
    private final TextCorretorUtil textCorretor = new TextCorretorUtil();

    public VoiceListener(String modelPath) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        LibVosk.setLogLevel(LogLevel.WARNINGS);

        logger.info("Inicializando modelo Vosk com caminho: " + modelPath);
        model = new Model(modelPath);
        recognizer = new Recognizer(model, 16000.0f);
        logger.info("Modelo e reconhecedor Vosk inicializados com sucesso.");
    }

    // Escuta contínua para comandos principais
    public void listenContinuously() throws LineUnavailableException {
        AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            logger.severe("Microfone não suporta o formato de áudio necessário.");
            throw new LineUnavailableException("Microfone incompatível com o formato exigido.");
        }

        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format, 16384);
        microphone.start();
        logger.info("🎙️ Microfone aberto e capturando áudio. Sarah está ouvindo...");

        CommandRouter router = new CommandRouter();
        byte[] buffer = new byte[8192];

        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);

            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String resultJson = recognizer.getResult();
                String texto = extractTextFromJson(resultJson);

                if (!texto.isEmpty()) {
                    String textoCorrigido = textCorretor.correctVocabulary(texto);
                    logger.info("🗣️ Você disse: " + textoCorrigido);

                    router.execute(textoCorrigido);
                }
            }
        }
    }

    // Extrai o campo "text" do JSON de retorno do Vosk
    private String extractTextFromJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty() || jsonString.trim().equals("{}")) {
            logger.warning("JSON de reconhecimento está vazio ou nulo: " + jsonString);
            return "";
        }

        try {
            // Tenta parsear o JSON corretamente
            if (jsonString.contains("\"text\"") && jsonString.contains(":")) {
                // Extrai o texto entre as aspas após "text":
                int startIndex = jsonString.indexOf("\"text\"") + 6;
                startIndex = jsonString.indexOf(":", startIndex) + 1;
                startIndex = jsonString.indexOf("\"", startIndex) + 1;
                int endIndex = jsonString.indexOf("\"", startIndex);

                if (startIndex > 0 && endIndex > startIndex) {
                    String texto = jsonString.substring(startIndex, endIndex).trim();
                    logger.fine("Texto extraído do JSON: " + texto);
                    return texto;
                }
            }

            logger.warning("Não foi possível extrair texto do JSON: " + jsonString);
            return "";
        } catch (Exception e) {
            logger.warning("Erro ao extrair texto do JSON: " + jsonString + " - " + e.getMessage());
            return "";
        }
    }

    // Libera recursos e fecha microfone, reconhecedor e modelo
    public void stop() {
        logger.info("Encerrando recursos de áudio e modelo...");

        if (microphone != null) {
            microphone.stop();
            microphone.close();
            logger.info("Microfone fechado.");
        }

        if (recognizer != null) {
            recognizer.close();
            logger.info("Reconhecedor encerrado.");
        }

        if (model != null) {
            model.close();
            logger.info("Modelo encerrado.");
        }

        logger.info("Todos os recursos foram liberados com sucesso.");
    }
}