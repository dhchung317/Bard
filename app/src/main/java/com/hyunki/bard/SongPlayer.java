package com.hyunki.bard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongPlayer {
    private Context context;
    private TextToSpeech tts;
    private HashMap<String, String> params = new HashMap<>();
    private MediaPlayer mp;
    private List<Note> playlist;
    private int i = 0;
    private Timer timer;
    private boolean started = false;

    public SongPlayer(Context context, TextToSpeech tts) {
        this.context = context;
        params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "0.1");
        this.tts = tts;
        tts.setSpeechRate((float) .3);
        timer = new Timer();
    }

    public void playSong(final Song song) {
        playlist = song.getSongNotes();
        mp = MediaPlayer.create(context, playlist.get(0).getRawNote());
        if(mp.isPlaying()){
            Toast.makeText(context, "media player is playing", Toast.LENGTH_SHORT).show();
        }else {
            if (playlist.size() >= 1 && i < playlist.size()) {
                playAll(playlist.get(0));
            }
        }
    }


    public void vocalize(final Note n) {
        tts.setPitch(calculatePitch(n));
        tts.speak(n.getSyllable(), TextToSpeech.QUEUE_FLUSH, params);
    }

    public void playAll(Note currentNote) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    mp.reset();
                    if (!started){
                        mp = MediaPlayer.create(context, playlist.get(i).getRawNote());
                        mp.seekTo(600);
                        mp.start();
                        vocalize(playlist.get(i));
                        playAll(playlist.get(i));
                        started = true;
                    }else if (i < playlist.size() - 1) {
                        mp = MediaPlayer.create(context, playlist.get(++i).getRawNote());
                        mp.seekTo(600);
                        mp.start();
                        vocalize(playlist.get(i));
                        if(i != playlist.size() - 1) {
                            playAll(playlist.get(i));
                        }
                    }
                });
            }
        },currentNote.getDuration());

        if (i == playlist.size() - 1) {
            mp.stop();
            started = false;
            timer.cancel();
            i = 0;
            timer = new Timer();
        }
    }

    public float calculatePitch(Note n) {
        switch (n.getNote()) {
            case "Aflat3":
                return (float) 1.7;
            case "A3":
                return (float) 1.8;
            case "Bflat3":
                return (float) 1.9;
            case "B3":
                return (float) 2;
            case "C3":
                return (float) 1;
            case "Dflat3":
                return (float) 1.1;
            case "D3":
                return (float) 1.2;
            case "Eflat3":
                return (float) 1.3;
            case "E3":
                return (float) 1.4;
            case "F3":
                return (float) 1.5;
            case "Gflat3":
                return (float) 1.6;
            case "G3":
                return (float) 1.7;
            default:
                return (float) 1;
        }
    }

    public MediaPlayer getMp() {
        return mp;
    }
}
