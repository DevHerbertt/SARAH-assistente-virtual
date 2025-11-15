package com.sarah.voice;

import com.sarah.utils.AudioPlayerUtil;
import lombok.experimental.UtilityClass;


@UtilityClass
public class VoiceResponder {
    public static String askAndListen(VoiceResponderDefault prompt) {
        // Apenas toca o áudio - o input será pela janela gráfica
        AudioPlayerUtil.play(prompt.getAudioPath());
        return null; // CommandRouter usará janela gráfica
    }
}