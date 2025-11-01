package com.sarah.service.SO;

import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class BrowserService {

    public void openBrowser(String url) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("🔍 Sistema operacional detectado: " + os);

            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                // Windows - método mais confiável
                System.out.println("🪟 Tentando abrir no Windows...");
                processBuilder = new ProcessBuilder("cmd", "/c", "start", "", url.replace("&", "^&"));
            } else if (os.contains("mac")) {
                // macOS
                System.out.println("🍎 Tentando abrir no macOS...");
                processBuilder = new ProcessBuilder("open", url);
            } else {
                // Linux/Unix
                System.out.println("🐧 Tentando abrir no Linux...");
                processBuilder = new ProcessBuilder("xdg-open", url);
            }

            // Executa o processo
            Process process = processBuilder.start();

            // Aguarda um pouco para ver se funciona
            Thread.sleep(2000);

            // Verifica se o processo ainda está rodando
            if (process.isAlive()) {
                System.out.println("✅ Processo do navegador iniciado com sucesso!");
            } else {
                int exitCode = process.exitValue();
                if (exitCode == 0) {
                    System.out.println("✅ Navegador aberto com sucesso! URL: " + url);
                } else {
                    System.out.println("⚠️ Navegador pode ter sido aberto (código: " + exitCode + ")");
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Erro de IO ao abrir navegador: " + e.getMessage());
            tryAlternativeMethods(url);
        } catch (InterruptedException e) {
            System.err.println("❌ Thread interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            tryAlternativeMethods(url);
        }
    }

    private void tryAlternativeMethods(String url) {
        System.out.println("🔄 Tentando métodos alternativos...");

        try {
            // Método alternativo para Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Tenta com Runtime.exec diretamente
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                System.out.println("✅ Navegador aberto via url.dll! URL: " + url);
                return;
            }

            // Tenta com browsers específicos
            String[] browsers = {
                    "chrome", "google-chrome", "chromium",
                    "firefox", "microsoft-edge", "opera",
                    "iexplore", "safari"
            };

            for (String browser : browsers) {
                try {
                    Runtime.getRuntime().exec(new String[]{browser, url});
                    System.out.println("✅ Navegador aberto via " + browser + "! URL: " + url);
                    return;
                } catch (IOException e) {
                    // Continua para o próximo browser
                }
            }

            System.err.println("❌ Todos os métodos falharam. Verifique se há um navegador instalado.");

        } catch (Exception e) {
            System.err.println("❌ Métodos alternativos também falharam: " + e.getMessage());
        }
    }

    // Método para testar o ambiente
    public void testEnvironment() {
        System.out.println("=== 🧪 TESTE DE AMBIENTE ===");
        System.out.println("OS: " + System.getProperty("os.name"));
        System.out.println("Arch: " + System.getProperty("os.arch"));
        System.out.println("Desktop suportado: " + java.awt.Desktop.isDesktopSupported());

        if (java.awt.Desktop.isDesktopSupported()) {
            System.out.println("Browse action: " + java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE));
        }

        // Testa comandos básicos
        testCommand("cmd", "/c", "echo", "Teste Windows");
        testCommand("echo", "Teste Unix");

        System.out.println("=== FIM DO TESTE ===");
    }

    private void testCommand(String... command) {
        try {
            Process process = new ProcessBuilder(command).start();
            int exitCode = process.waitFor();
            System.out.println("Comando " + String.join(" ", command) + " - Código: " + exitCode);
        } catch (Exception e) {
            System.out.println("Comando " + String.join(" ", command) + " - FALHOU: " + e.getMessage());
        }
    }
}