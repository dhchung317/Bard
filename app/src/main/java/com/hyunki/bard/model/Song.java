package com.hyunki.bard.model;

import android.database.Observable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Song implements Parcelable {
    private List<Note> songNotes = new ArrayList<>();
    private String songTitle;

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public Song(){}

    public Song(String songTitle) {
        this.songTitle = songTitle;
    }


    protected Song(Parcel in) {
        songNotes = in.createTypedArrayList(Note.CREATOR);
        songTitle = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public void addNote(Note note){
        songNotes.add(note);
    }
    public void deleteNote(){
        songNotes.remove(songNotes.size() - 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(songNotes);
        dest.writeString(songTitle);
    }


    public List<Note> getSongNotes() {
        return songNotes;
    }

    public String getSongTitle() {
        return songTitle;
    }


}
