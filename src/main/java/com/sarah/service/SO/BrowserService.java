package com.sarah.service.SO;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class BrowserService {

    public void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI.create(url));
                System.out.println("🌐 Navegador aberto com sucesso!");
            } else {
                System.err.println("❌ Desktop não é suportado neste sistema.");
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao tentar abrir o navegador: " + e.getMessage());
        }
    }
}
