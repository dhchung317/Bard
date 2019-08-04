package com.hyunki.bard.model;

public class ClickableNote {
    private String note;
    private int rawNote;
    private String imgSrc;

    public ClickableNote(String note, int rawNote, String imgSrc) {
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

    public String getImgSrc() {
        return imgSrc;
    }
}
