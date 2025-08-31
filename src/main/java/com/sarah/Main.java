package com.sarah;

import com.sarah.voice.VoiceListener;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String modelPath = "src/main/resources/vosk-model-small-pt-0.3";

            VoiceListener listener = new VoiceListener(modelPath);

            System.out.println("🎧 Sarah está ouvindo... Diga 'Sair' para encerrar.");
            listener.listenContinuously();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}