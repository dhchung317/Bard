package com.hyunki.bard.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String syllable;
    private String note;
    private int rawNote;
    private int duration;

    public String getSyllable() {
        return syllable;
    }

    public String getNote() {
        return note;
    }

    public int getRawNote() {
        return rawNote;
    }

    public int getDuration() {
        return duration;
    }

    public Note(int rawNote, String syllable, int duration, String note) {
        this.syllable = syllable;
        this.rawNote = rawNote;
        this.duration = duration;
        this.note = note;
    }

    protected Note(Parcel in) {
        syllable = in.readString();
        note = in.readString();
        rawNote = in.readInt();
        duration = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(note);
        dest.writeString(syllable);
        dest.writeInt(rawNote);
        dest.writeInt(duration);
    }
}
