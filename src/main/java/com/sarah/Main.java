package com.sarah;

import com.sarah.controller.CommandRouterFileAndDirectory;
import com.sarah.controller.CommandRouterSO;
import com.sarah.controller.CommandRouterWeb;
import com.sarah.controller.CommandSpotify;
import com.sarah.utils.VerificationUsers;
import com.sarah.voice.VoiceListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run(
            VoiceListener listener,
            CommandRouterFileAndDirectory routerFileAndDirectory,
            CommandRouterSO routerSO,
            CommandRouterWeb routerWeb,
            CommandSpotify commandSpotify,
            VerificationUsers verificationUsers) {

        return args -> {
            try {
                // Inicializa o VoiceListener
                listener.initialize("src/main/resources/vosk-model-small-pt-0.3");

                System.out.println("🎧 Sarah está ouvindo... Diga 'Sair' para encerrar.");

                // Loop principal de escuta
                while (true) {
                    String comando = listener.listenForCommand();

                    if (comando != null && !comando.trim().isEmpty()) {
                        System.out.println("📝 Processando comando: " + comando);

                        routerSO.execute(comando);
                        routerWeb.execute(comando);
                        routerFileAndDirectory.execute(comando);
                        commandSpotify.execute(comando);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            } finally {
                listener.stop();
            }
        };
    }
}