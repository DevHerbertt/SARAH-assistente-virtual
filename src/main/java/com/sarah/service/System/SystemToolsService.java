package com.sarah.service.System;

import lombok.extern.log4j.Log4j2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

@Log4j2
public class SystemToolsService {
    
    // Abre o Painel de Controle
    public void openControlPanel() {
        try {
            log.info("🛠️ Abrindo Painel de Controle...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("control");
            } else {
                // Fallback para outros sistemas
                Runtime.getRuntime().exec("xdg-open settings://"); // Linux
            }
            log.info("✅ Painel de Controle aberto com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Painel de Controle: " + e.getMessage());
            fallbackOpen("Painel de Controle");
        }
    }
    
    // Abre o Gerenciador de Tarefas
    public void openTaskManager() {
        try {
            log.info("📊 Abrindo Gerenciador de Tarefas...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("taskmgr");
            } else {
                Runtime.getRuntime().exec("gnome-system-monitor"); // Linux
            }
            log.info("✅ Gerenciador de Tarefas aberto com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Gerenciador de Tarefas: " + e.getMessage());
            // Atalho de teclado como fallback
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            } catch (AWTException ex) {
                log.error("❌ Fallback também falhou: " + ex.getMessage());
            }
        }
    }
    
    // Abre as Configurações do Windows
    public void openWindowsSettings() {
        try {
            log.info("⚙️ Abrindo Configurações do Windows...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("start ms-settings:");
            } else {
                Runtime.getRuntime().exec("gnome-control-center"); // Linux
            }
            log.info("✅ Configurações do Windows abertas com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Configurações: " + e.getMessage());
            fallbackOpen("Configurações do Windows");
        }
    }
    
    // Abre o Prompt de Comando
    public void openCommandPrompt() {
        try {
            log.info("💻 Abrindo Prompt de Comando...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("cmd");
            } else {
                Runtime.getRuntime().exec("gnome-terminal"); // Linux
            }
            log.info("✅ Prompt de Comando aberto com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Prompt de Comando: " + e.getMessage());
        }
    }
    
    // Abre o Explorador de Arquivos
    public void openFileExplorer() {
        try {
            log.info("📁 Abrindo Explorador de Arquivos...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("explorer");
            } else {
                Runtime.getRuntime().exec("nautilus"); // Linux
            }
            log.info("✅ Explorador de Arquivos aberto com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Explorador: " + e.getMessage());
        }
    }
    
    // Abre a Calculadora
    public void openCalculator() {
        try {
            log.info("🧮 Abrindo Calculadora...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("calc");
            } else {
                Runtime.getRuntime().exec("gnome-calculator"); // Linux
            }
            log.info("✅ Calculadora aberta com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Calculadora: " + e.getMessage());
        }
    }
    
    // Abre o Gerenciador de Dispositivos
    public void openDeviceManager() {
        try {
            log.info("🔧 Abrindo Gerenciador de Dispositivos...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("devmgmt.msc");
            } else {
                Runtime.getRuntime().exec("lshw-gtk"); // Linux
            }
            log.info("✅ Gerenciador de Dispositivos aberto com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Gerenciador de Dispositivos: " + e.getMessage());
        }
    }
    
    // Abre Informações do Sistema (msinfo32)
    public void openSystemInfo() {
        try {
            log.info("💡 Abrindo Informações do Sistema...");
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("msinfo32");
            } else {
                Runtime.getRuntime().exec("hardinfo"); // Linux
            }
            log.info("✅ Informações do Sistema abertas com sucesso");
        } catch (IOException e) {
            log.error("❌ Erro ao abrir Informações do Sistema: " + e.getMessage());
        }
    }
    
    // Método fallback usando Robot para abrir menu Iniciar + pesquisa
    private void fallbackOpen(String toolName) {
        try {
            log.info("🔄 Tentando fallback para: " + toolName);
            Robot robot = new Robot();
            
            // Abre menu Iniciar (Windows key)
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            
            Thread.sleep(500);
            
            // Digita o nome da ferramenta
            String searchText = toolName.toLowerCase();
            if (toolName.equals("Painel de Controle")) {
                searchText = "control panel";
            } else if (toolName.equals("Configurações do Windows")) {
                searchText = "settings";
            }
            
            typeString(robot, searchText);
            Thread.sleep(1000);
            
            // Pressiona Enter para abrir
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
        } catch (Exception e) {
            log.error("❌ Fallback também falhou: " + e.getMessage());
        }
    }
    
    // Método para simular digitação
    private void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = getKeyCode(c);
            if (keyCode != 0) {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }
        }
    }
    
    // Mapeia caracteres para KeyEvent
    private int getKeyCode(char c) {
        if (c >= 'a' && c <= 'z') {
            return KeyEvent.VK_A + (c - 'a');
        } else if (c >= '0' && c <= '9') {
            return KeyEvent.VK_0 + (c - '0');
        } else if (c == ' ') {
            return KeyEvent.VK_SPACE;
        }
        return 0;
    }
}