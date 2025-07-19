package com.sarah.voice;

import com.sarah.utils.TextCorretor;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Locale;

public class VoiceListener {

    private final Model model;
    private final Recognizer recognizer;
    private TargetDataLine microphone;
    TextCorretor textCorretor = new TextCorretor();


    public VoiceListener(String modelPath) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        // Definindo o nível de log para reduzir a poluição no console
        LibVosk.setLogLevel(LogLevel.WARNINGS);

        // Inicializa o modelo Vosk com o caminho fornecido
        model = new Model(modelPath);
        recognizer = new Recognizer(model, 16000.0f); // Taxa de amostragem de 16kHz
    }

    // Método para iniciar a escuta da voz
    public void listen() throws LineUnavailableException {
        AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);  // Formato de áudio para capturar a voz

        // Verifica se o microfone suporta o formato especificado
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Microfone não suporta este formato.");
        }

        // Obter o microfone e configurar
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format, 16384);  // Tamanho do buffer ajustado
        microphone.start();


        System.out.println("🎙️ Sarah está ouvindo... (fale algo)");

        byte[] buffer = new byte[8192];  // Tamanho do buffer para captura de áudio

        // Loop principal para processar a fala
        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);

            // Verifica se uma transcrição completa foi reconhecida
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String result = recognizer.getResult();
                String texto = extractTextFromJson(result); // Método para extrair o texto de forma mais segura

                String correctedtext = textCorretor.correctVocabulary(texto);

                if (!texto.isEmpty()) {
                    System.out.println("🗣️ Você disse (Final): " + correctedtext);
                    // Aqui pode chamar: new SarahEngine().responder(texto);
                }
            }
        }
    }

    // Método para extrair o texto de um JSON retornado pelo recognizer
    private String extractTextFromJson(String jsonString) {
        // Utilizando expressão regular para extrair o valor do campo "text"
        String texto = jsonString.replaceAll(".*\"text\"\\s*:\\s*\"(.*?)\".*", "$1").trim();
        return texto;
    }

    // Método para parar a captura e fechar o microfone e recursos
    public void stop() {
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }

        // Fechar o modelo e reconhecedor ao finalizar
        if (recognizer != null) {
            recognizer.close();
        }

        if (model != null) {
            model.close();
        }
    }

    public static void main(String[] args) {
        try {
            // Passar o caminho do modelo desejado para o construtor
            VoiceListener listener = new VoiceListener("path/to/your/model");
            listener.listen();  // Inicia a escuta

        } catch (Exception e) {
            e.printStackTrace();  // Exibe erros no console
        }
    }
}
