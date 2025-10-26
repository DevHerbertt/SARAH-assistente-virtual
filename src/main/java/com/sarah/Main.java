package com.sarah;

import com.sarah.controller.CommandRouterFileAndDirectory;
import com.sarah.controller.CommandRouterSO;
import com.sarah.controller.CommandRouterWeb;
import com.sarah.voice.VoiceListener;

public class Main {
    public static void main(String[] args) throws Exception {
        String modelPath = "src/main/resources/vosk-model-small-pt-0.3";
        VoiceListener listener = new VoiceListener(modelPath);
        CommandRouterFileAndDirectory routerFileAndDirectory = new CommandRouterFileAndDirectory();
        CommandRouterSO routerSO = new CommandRouterSO();
        CommandRouterWeb routerWeb = new CommandRouterWeb();

        try {
            System.out.println("🎧 Sarah está ouvindo... Diga 'Sair' para encerrar.");

            // Loop principal de escuta
            while (true) {
                // Escuta por um comando (esta versão é bloqueante)
                String comando = listener.listenForCommand();

                if (comando != null && !comando.trim().isEmpty()) {
                    System.out.println("📝 Processando comando: " + comando);


                    // Primeiro tenta executar comandos do SO
                    routerSO.execute(comando);

                    routerWeb.execute(comando);
                    // Depois tenta comandos de arquivo/diretório
                    routerFileAndDirectory.execute(comando);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            listener.stop();
        }
    }
}