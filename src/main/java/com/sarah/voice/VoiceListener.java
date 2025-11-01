package com.sarah.voice;

import com.sarah.utils.TextCorretorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

@Component
public class VoiceListener {
    public String textoCorrigido;
    private static final Logger logger = Logger.getLogger(VoiceListener.class.getName());

    private Model model;
    private Recognizer recognizer;
    private TargetDataLine microphone;
    private final TextCorretorUtil textCorretor;
    private boolean listening = false;

    // Construtor para Spring - sem parâmetros
    public VoiceListener(TextCorretorUtil textCorretor) {
        this.textCorretor = textCorretor;
    }

    // Método de inicialização
    public void initialize(@Value("${voice.model.path:src/main/resources/vosk-model-small-pt-0.3}") String modelPath) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        LibVosk.setLogLevel(LogLevel.WARNINGS);

        logger.info("Inicializando modelo Vosk com caminho: " + modelPath);
        model = new Model(modelPath);
        recognizer = new Recognizer(model, 16000.0f);
        logger.info("Modelo e reconhecedor Vosk inicializados com sucesso.");
    }

    // Inicia a escuta contínua
    public void startListening() throws LineUnavailableException {
        if (model == null) {
            throw new IllegalStateException("VoiceListener não foi inicializado. Chame initialize() primeiro.");
        }

        AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            logger.severe("Microfone não suporta o formato de áudio necessário.");
            throw new LineUnavailableException("Microfone incompatível com o formato exigido.");
        }

        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format, 16384);
        microphone.start();
        listening = true;
        logger.info("🎙️ Microfone aberto e capturando áudio. Sarah está ouvindo...");
    }

    // Obtém o próximo comando reconhecido (não-bloqueante)
    public String getNextCommand() {
        if (!listening || microphone == null) {
            return null;
        }

        byte[] buffer = new byte[8192];
        int bytesRead = microphone.read(buffer, 0, buffer.length);

        if (bytesRead > 0 && recognizer.acceptWaveForm(buffer, bytesRead)) {
            String resultJson = recognizer.getResult();
            String texto = extractTextFromJson(resultJson);

            if (!texto.isEmpty()) {
                textoCorrigido = textCorretor.correctVocabulary(texto);
                logger.info("🗣️ Você disse: " + textoCorrigido);
                return textoCorrigido;
            }
        }

        return null;
    }

    // Versão simplificada para teste
    public String listenForCommand() throws LineUnavailableException, InterruptedException, IOException {
        if (model == null) {
            initialize("src/main/resources/vosk-model-small-pt-0.3");
        }

        if (!listening) {
            startListening();
        }

        byte[] buffer = new byte[8192];

        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);

            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String resultJson = recognizer.getResult();
                String texto = extractTextFromJson(resultJson);

                if (!texto.isEmpty() && !texto.equals("oi")) {
                    textoCorrigido = textCorretor.correctVocabulary(texto);
                    logger.info("🗣️ Comando reconhecido: " + textoCorrigido);
                    return textoCorrigido;
                }
            }

            // Pequena pausa para não sobrecarregar
            Thread.sleep(100);
        }
    }

    // Extrai o campo "text" do JSON de retorno do Vosk
    private String extractTextFromJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty() || jsonString.trim().equals("{}")) {
            return "";
        }

        try {
            if (jsonString.contains("\"text\"") && jsonString.contains(":")) {
                int startIndex = jsonString.indexOf("\"text\"") + 6;
                startIndex = jsonString.indexOf(":", startIndex) + 1;
                startIndex = jsonString.indexOf("\"", startIndex) + 1;
                int endIndex = jsonString.indexOf("\"", startIndex);

                if (startIndex > 0 && endIndex > startIndex) {
                    String texto = jsonString.substring(startIndex, endIndex).trim();
                    return texto;
                }
            }
            return "";
        } catch (Exception e) {
            logger.warning("Erro ao extrair texto do JSON: " + e.getMessage());
            return "";
        }
    }

    public void stop() {
        logger.info("Encerrando recursos de áudio e modelo...");
        listening = false;

        if (microphone != null) {
            microphone.stop();
            microphone.close();
            logger.info("Microfone fechado.");
        }

        if (recognizer != null) {
            recognizer.close();
        }

        if (model != null) {
            model.close();
        }

        logger.info("Todos os recursos foram liberados com sucesso.");
    }

    // Verifica se está inicializado
    public boolean isInitialized() {
        return model != null && recognizer != null;
    }
}