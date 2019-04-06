package com.hyunki.bard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class SongPlayer {
    private Context context;
    private TextToSpeech tts;
    private HashMap<String, String> params = new HashMap<String, String>();

    public SongPlayer(Context context, TextToSpeech tts) {
        this.context = context;
        params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "0.1");
        this.tts = tts;
    }

    public void playSong(final Song song) {
        Handler handler1 = new Handler();
        int totalSec = 0;

        for (final Note n : song.getSongNotes()) {
            Log.d(TAG, "playSong: "+ n.getRawNote());
            final MediaPlayer note = MediaPlayer.create(context, n.getRawNote());
            handler1.postDelayed(() -> {
                note.seekTo(600);
                vocalize(n);
                note.start();
                note.setOnCompletionListener(mp -> note.release());
            }, totalSec);
            totalSec += n.getDuration();
        }
    }

    public void vocalize(final Note n) {
        tts.setPitch(calculatePitch(n));
        tts.setSpeechRate((float) .4);
        tts.speak(n.getSyllable(), TextToSpeech.QUEUE_FLUSH, params);
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
}
