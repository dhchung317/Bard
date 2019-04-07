package com.hyunki.bard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;
import com.hyunki.bard.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class SongPlayer {
    private Context context;
    private TextToSpeech tts;
    private HashMap<String, String> params = new HashMap<String, String>();
    Timer timer;
    MediaPlayer mp;
    List<Note> playlist;
    int i = 0;

    public SongPlayer(Context context, TextToSpeech tts) {
        this.context = context;
        params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "0.1");
        this.tts = tts;
        timer = new Timer();

    }

    public void playSong(final Song song) {

        playlist = song.getSongNotes();

//        for(Note n : playlist){
//            playlist.add(n.getRawNote());
//        }

        mp = MediaPlayer.create(context, playlist.get(0).getRawNote());
        mp.setOnCompletionListener(l);
        vocalize(playlist.get(0));
        mp.seekTo(600);
        mp.start();
        if (playlist.size() > 1 && i < playlist.size()) playNext(playlist.get(0));
    }

    MediaPlayer.OnCompletionListener l = new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mp) {

//            if (mp.isPlaying())
//                mp.stop();
//            timer.cancel();

        }
    };


    public void vocalize(final Note n) {
        tts.setPitch(calculatePitch(n));
        tts.setSpeechRate((float) .4);
        tts.speak(n.getSyllable(), TextToSpeech.QUEUE_FLUSH, params);
    }

    public void playNext(Note currentNote) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        mp.reset();
                        if (i < playlist.size() - 1) {
                            mp = MediaPlayer.create(context, playlist.get(++i).getRawNote());
                            mp.seekTo(600);
                            mp.start();
                            vocalize(playlist.get(i));
                            playNext(playlist.get(i));
                        }


                    }
                });
            }

        },currentNote.getDuration());

        if (i == playlist.size() - 1){
            mp.stop();
            mp.release();
            timer.cancel();
            i =0;
        }

        timer = new Timer();

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
