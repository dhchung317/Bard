package com.hyunki.bard;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class SongPlayer {
    Context context;
    TextToSpeech tts;
    HashMap<String, String> params = new HashMap<String, String>();


    public SongPlayer(Context context, TextToSpeech tts) {
        this.context = context;
        params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "0.1");
        this.tts = tts;
    }


    public void playSong(final Song song) {

        Handler handler1 = new Handler();
        int totalSec = 0;
        for (final Note n : song.songNotes) {
            Log.d(TAG, "playSong: "+ n.rawNote);
            final MediaPlayer note = MediaPlayer.create(context, n.rawNote);


            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    note.seekTo(600);
                    vocalize(n);
                    note.start();

                    note.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            note.release();
                        }
                    });


                }

            }, totalSec);
            totalSec += n.duration;
        }
    }

    public void vocalize(final Note n) {
        tts.setPitch(calculatePitch(n));
        tts.setSpeechRate((float) .4);
        tts.speak(n.syllable, TextToSpeech.QUEUE_FLUSH, params);
    }

    public int calculateSongLength(final Song song) {
        int duration = 0;
        for (Note n : song.songNotes) {
            duration += n.duration;
        }
        return duration;
    }

    public float calculatePitch(Note n) {
        switch (n.note) {
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
