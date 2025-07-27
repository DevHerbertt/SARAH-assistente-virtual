package com.sarah.utils;

import com.sarah.voice.VoiceListener;

import javax.sound.sampled.LineUnavailableException;

public class VoiceListenerUtil {
    private static VoiceListener listener;

    public static String Listen() {
        if (listener != null) {
            try {
                return listener.listen();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
