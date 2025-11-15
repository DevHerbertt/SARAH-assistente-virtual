package com.sarah.service.SO;

import lombok.extern.log4j.Log4j2;
import java.io.IOException;

@Log4j2
public class SpotifyService {

 /**
  * 🎵 Abre o Spotify
  */
 public void openSpotify() {
  try {
   log.info("🎵 Abrindo Spotify...");
   Runtime.getRuntime().exec("cmd /c start spotify");
  } catch (IOException e) {
   log.error("Erro ao abrir Spotify: ", e);
  }
 }

 /**
  * ⏯ Tocar / Pausar
  */
 public void playPause() {
  try {
   log.info("⏯ Play/Pause Spotify");
   Runtime.getRuntime().exec("powershell -command \"(New-Object -ComObject WScript.Shell).SendKeys('{MEDIA_PLAY_PAUSE}')\"");
  } catch (IOException e) {
   log.error("Erro ao pausar/retomar Spotify: ", e);
  }
 }

 /**
  * ⏭ Próxima faixa
  */
 public void nextTrack() {
  try {
   log.info("⏭ Próxima música");
   Runtime.getRuntime().exec("powershell -command \"(New-Object -ComObject WScript.Shell).SendKeys('{MEDIA_NEXT_TRACK}')\"");
  } catch (IOException e) {
   log.error("Erro ao avançar música: ", e);
  }
 }

 /**
  * ⏮ Faixa anterior
  */
 public void previousTrack() {
  try {
   log.info("⏮ Música anterior");
   Runtime.getRuntime().exec("powershell -command \"(New-Object -ComObject WScript.Shell).SendKeys('{MEDIA_PREV_TRACK}')\"");
  } catch (IOException e) {
   log.error("Erro ao retroceder música: ", e);
  }
 }
}