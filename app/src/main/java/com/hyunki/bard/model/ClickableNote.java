package com.hyunki.bard.model;

public class ClickableNote {
    private String note;
    private int rawNote;
    private int imgSrc;

    public ClickableNote(String note, int rawNote, int imgSrc) {
        this.note = note;
        this.rawNote = rawNote;
        this.imgSrc = imgSrc;
    }

    public String getNote() {
        return note;
    }

    public int getRawNote() {
        return rawNote;
    }

    public int getImgSrc() {
        return imgSrc;
    }
}
