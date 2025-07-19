package com.sarah;


import com.sarah.voice.VoiceListener;

public class Main {
        public static void main(String[] args) throws Exception {
            System.setProperty("file.encoding", "UTF-8");
            VoiceListener listener = new VoiceListener("C:\\Users\\Microsoft\\SARAH-assistente-virtual\\src\\main\\resources\\vosk-model-small-pt-0.3");
            listener.listen(); // ele escuta até você parar manualmente
        }

}
