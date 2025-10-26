package com.sarah.service.System;

import lombok.extern.log4j.Log4j2;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Enumeration;

@Log4j2
public class SystemInfoService {
    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;
    private final OperatingSystem operatingSystem;
    
    public SystemInfoService() {
        this.systemInfo = new SystemInfo();
        this.hardware = systemInfo.getHardware();
        this.operatingSystem = systemInfo.getOperatingSystem();
    }
    
    // Informações da CPU
    public String getCpuInfo() {
        CentralProcessor processor = hardware.getProcessor();
        StringBuilder info = new StringBuilder();
        
        info.append("💻 **INFORMAÇÕES DA CPU**\n");
        info.append("Processador: ").append(processor.getProcessorIdentifier().getName()).append("\n");
        info.append("Núcleos Físicos: ").append(processor.getPhysicalProcessorCount()).append("\n");
        info.append("Núcleos Lógicos: ").append(processor.getLogicalProcessorCount()).append("\n");
        info.append("Frequência: ").append(formatGhz(processor.getMaxFreq())).append(" GHz\n");
        info.append("Arquitetura: ").append(processor.getProcessorIdentifier().getMicroarchitecture()).append("\n");
        
        return info.toString();
    }
    
    // Informações da Memória RAM
    public String getMemoryInfo() {
        GlobalMemory memory = hardware.getMemory();
        DecimalFormat df = new DecimalFormat("#,##0.0");
        
        long totalGB = memory.getTotal() / (1024 * 1024 * 1024);
        long availableGB = memory.getAvailable() / (1024 * 1024 * 1024);
        long usedGB = totalGB - availableGB;
        double usagePercent = ((double) usedGB / totalGB) * 100;
        
        StringBuilder info = new StringBuilder();
        info.append("🧠 **INFORMAÇÕES DA MEMÓVIA RAM**\n");
        info.append("Memória Total: ").append(totalGB).append(" GB\n");
        info.append("Memória Usada: ").append(usedGB).append(" GB\n");
        info.append("Memória Disponível: ").append(availableGB).append(" GB\n");
        info.append("Uso: ").append(df.format(usagePercent)).append("%\n");
        
        return info.toString();
    }
    
    // Informações de Disco
    public String getDiskInfo() {
        StringBuilder info = new StringBuilder();
        info.append("💾 **INFORMAÇÕES DE ARMAZENAMENTO**\n");
        
        for (HWDiskStore disk : hardware.getDiskStores()) {
            if (disk.getSize() > 0) {
                long sizeGB = disk.getSize() / (1024 * 1024 * 1024);
                info.append("Disco: ").append(disk.getModel()).append("\n");
                info.append("Tamanho: ").append(sizeGB).append(" GB\n");
                info.append("---\n");
            }
        }
        
        return info.toString();
    }
    
    // Informações do Sistema Operacional
    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("🖥️ **INFORMAÇÕES DO SISTEMA**\n");
        info.append("Sistema: ").append(operatingSystem.getFamily()).append("\n");
        info.append("Versão: ").append(operatingSystem.getVersionInfo().getVersion()).append("\n");
        info.append("Build: ").append(operatingSystem.getVersionInfo().getBuildNumber()).append("\n");
        info.append("Fabricante: ").append(operatingSystem.getManufacturer()).append("\n");
        info.append("Tempo de Atividade: ").append(formatUptime(hardware.getComputerSystem().getHardwareUUID())).append("\n");
        
        return info.toString();
    }
    
    // Informações de Rede
    public String getNetworkInfo() {
        StringBuilder info = new StringBuilder();
        info.append("🌐 **INFORMAÇÕES DE REDE**\n");
        
        try {
            // IP Local
            String localIp = getLocalIpAddress();
            info.append("IP Local: ").append(localIp).append("\n");
            
            // Hostname
            String hostname = InetAddress.getLocalHost().getHostName();
            info.append("Hostname: ").append(hostname).append("\n");
            
            // Interfaces de rede
            info.append("Interfaces de Rede:\n");
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && !ni.isLoopback()) {
                    info.append("- ").append(ni.getDisplayName()).append("\n");
                }
            }
            
        } catch (Exception e) {
            log.error("Erro ao obter informações de rede: " + e.getMessage());
            info.append("Erro ao obter informações de rede\n");
        }
        
        return info.toString();
    }
    
    // Informações da Placa de Vídeo
    public String getGraphicsInfo() {
        StringBuilder info = new StringBuilder();
        info.append("🎮 **INFORMAÇÕES DA PLACA DE VÍDEO**\n");
        
        for (GraphicsCard card : hardware.getGraphicsCards()) {
            if (card.getDeviceId().toLowerCase().contains("nvidia") || 
                card.getDeviceId().toLowerCase().contains("amd") ||
                card.getDeviceId().toLowerCase().contains("intel")) {
                
                long vramMB = card.getVRam() / (1024 * 1024);
                info.append("Placa: ").append(card.getName()).append("\n");
                info.append("VRAM: ").append(vramMB).append(" MB\n");
                info.append("Fabricante: ").append(card.getDeviceId()).append("\n");
                info.append("---\n");
            }
        }
        
        if (info.toString().equals("🎮 **INFORMAÇÕES DA PLACA DE VÍDEO**\n")) {
            info.append("Nenhuma placa de vídeo dedicada detectada\n");
        }
        
        return info.toString();
    }
    
    // Informações completas do sistema
    public String getCompleteSystemInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("🔍 **INFORMAÇÕES COMPLETAS DO COMPUTADOR**\n\n");
        info.append(getSystemInfo()).append("\n");
        info.append(getCpuInfo()).append("\n");
        info.append(getMemoryInfo()).append("\n");
        info.append(getDiskInfo()).append("\n");
        info.append(getGraphicsInfo()).append("\n");
        info.append(getNetworkInfo());
        
        return info.toString();
    }
    
    // Métodos auxiliares
    private String formatGhz(long hz) {
        double ghz = hz / 1_000_000_000.0;
        return String.format("%.2f", ghz);
    }
    
    private String formatUptime(String uuid) {
        // Simples implementação - em produção você usaria oshi para tempo de atividade real
        return "Sistema ativo";
    }
    
    private String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && !ni.isLoopback()) {
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().contains(".")) {
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Erro ao obter IP: " + e.getMessage());
        }
        return "Não disponível";
    }
}