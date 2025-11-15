package com.sarah.voice;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


public enum VoiceResponderDefault {
    ERRO("audio/Erro.wav"),
    DIRECTORY_LER_ARQUIVO("audio/Ler_Arquivo.wav"),
    LISTAR_ARQUIVOS("audio/Listar_Arquivos.wav"),
    NOME_ARQUIVO_DETALHES("audio/Nome_Arquivo_Detalhes.wav"),
    NOME_DIRETORIO("audio/Nome_Diretorio.wav"),
    NOME_DIRETORIO_ARQUIVO_SALVARA("audio/Nome_Diretorio_Arquivo_Salvara.wav"),
    NOME_DIRETORIO_DELETAR("audio/Nome_Diretorio_Deletar.wav"),
    NOME_ARQUIVO("audio/Nome_Arquivo.wav"),
    NOME_ARQUIVO_LER("audio/Nome_Arquivo_Ler.wav"),
    DIRETORIO_LISTAR("audio/Diretorio_Listar.wav"),
    PEDIDO_ENTENDIDO("audio/Pedido entendido.wav"),
    FINALIZANDO("audio/FInalizando.wav"),
    EXECUTANDO("audio/executando.wav");


    private final String audioPath;

    VoiceResponderDefault(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getAudioPath() {
        return audioPath;
    }
}
