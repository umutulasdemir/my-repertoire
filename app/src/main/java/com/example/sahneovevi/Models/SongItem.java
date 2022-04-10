package com.example.sahneovevi.Models;

import java.util.ArrayList;

public class SongItem {
    String name, tone, chords, attribute1, attribute2, lowercase;

    public SongItem(String name, String tone, String chords, String attribute1, String attribute2, String lowercase) {
        this.name = name;
        this.tone = tone;
        this.chords = chords;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.lowercase = lowercase;
    }

    public String getLowercase() {
        return lowercase;
    }

    public void setLowercase(String lowercase) {
        this.lowercase = lowercase;
    }

    public SongItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getChords() {
        return chords;
    }

    public void setChords(String chords) {
        this.chords = chords;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }
}