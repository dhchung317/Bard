package com.hyunki.bard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_PARENT = "Songs";
    private static final String TABLE_CHILD = "Notes";
    private static final String DATABASE_NAME = "songs.db";
    private static final int SCHEMA_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_PARENT +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "song_name TEXT UNIQUE);");
        db.execSQL(
                "CREATE TABLE " + TABLE_CHILD +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "raw_note INTEGER," +
                        "note_syllable TEXT," +
                        "note_duration Integer," +
                        "note_name TEXT," +
                        "song_name TEXT," +
                        "FOREIGN KEY(song_name) REFERENCES TABLE_PARENT(song_name));");
    }

    public void addSong(Song song) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_PARENT + " WHERE song_name = '" + song.getSongTitle() +
                        "';", null);

        if (cursor.getCount() == 0) {
            getWritableDatabase().execSQL("INSERT INTO " + TABLE_PARENT +
                    "(song_name) VALUES('" + song.getSongTitle() + "')");

            for (Note note : song.getSongNotes()) {
                getWritableDatabase().execSQL("INSERT INTO " + TABLE_CHILD +
                        "(raw_note, note_syllable, note_duration, note_name, song_name) " +
                        "VALUES('" + note.getRawNote() + "', '" + note.getSyllable() + "', '"
                        + note.getDuration() + "','" + note.getNote()
                        + "', '" + song.getSongTitle() + "');");
            }
        }
        cursor.close();
    }

    public Song getSong(String songName) {
        Song song = new Song("");
        Note note = null;
        Cursor checker = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_PARENT + " WHERE song_name " + "= '" + songName + "';", null);
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_CHILD + " WHERE song_name " + "= '" + songName + "';", null);
        if (checker.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    note = new Note(
                            cursor.getInt(cursor.getColumnIndex("raw_note")),
                            cursor.getString(cursor.getColumnIndex("note_syllable")),
                            cursor.getInt(cursor.getColumnIndex("note_duration")),
                            cursor.getString(cursor.getColumnIndex("note_name")));
                    song.addNote(note);
                } while (cursor.moveToNext());
            }
            song.setSongTitle(songName);
        }
        checker.close();
        cursor.close();
        return song;
    }

    public MutableLiveData<List<Song>> getAllSongs() {
        List<String> titles = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_PARENT + ";", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    titles.add(cursor.getString(cursor.getColumnIndex("song_name")));
                } while (cursor.moveToNext());
            }
        }
        MutableLiveData<List<Song>> liveData = new MutableLiveData<>();
        List<Song> returnList = new ArrayList<>();
        for (String s : titles) {
            Cursor childCursor = getReadableDatabase().rawQuery(
                    "SELECT * FROM " + TABLE_CHILD + " WHERE song_name " + "= '" + s + "';", null);
            Song song = new Song(s);
            Note note = null;
            if (childCursor != null) {
                if (childCursor.moveToFirst()) {
                    do {
                        note = new Note(
                                childCursor.getInt(childCursor.getColumnIndex("raw_note")),
                                childCursor.getString(childCursor.getColumnIndex("note_syllable")),
                                childCursor.getInt(childCursor.getColumnIndex("note_duration")),
                                childCursor.getString(childCursor.getColumnIndex("note_name"))
                        );
                        song.addNote(note);
                    } while (childCursor.moveToNext());
                }
            }
            returnList.add(song);
        }
        liveData.setValue(returnList);
        return liveData;
    }

    public void deleteSong(String songTitle) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_PARENT + " WHERE song_name " + "= '" + songTitle + "';",
                null);
        Cursor childCursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_CHILD + " WHERE song_name " + "= '" + songTitle + "';",
                null);
        if(cursor != null) {
            getWritableDatabase().execSQL(
                    "DELETE FROM " + TABLE_PARENT + " WHERE song_name " + "= '" + songTitle + "';");
        }
        do  {
            getWritableDatabase().execSQL(
                    "DELETE FROM " + TABLE_CHILD + " WHERE song_name " + "= '" + songTitle + "';");
        }while(childCursor.moveToNext());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
