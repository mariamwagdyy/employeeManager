package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KnownLanguages {
    @JsonProperty("LanguageName")
    private String languageName;
    @JsonProperty("ScoreOutof100")
    private int score;

    public KnownLanguages() {

    }

    public KnownLanguages(String languageName, int score) {
        this.languageName = languageName;
        this.score = score;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "{" +
                "LanguageName=" + languageName +
                ", ScoreOutof100=" + score +
                '}' + "\n";
    }
}
