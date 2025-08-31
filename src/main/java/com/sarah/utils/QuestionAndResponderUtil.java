package com.sarah.utils;

import com.sarah.voice.VoiceResponderDefault;
import lombok.Setter;


public class QuestionAndResponderUtil {
    public static String askAndListen(VoiceResponderDefault prompt) {
        // Apenas toca o áudio - o input será pela janela gráfica
        AudioPlayerUtil.play(prompt.getAudioPath());
        return null; // CommandRouter usará janela gráfica
    }
}