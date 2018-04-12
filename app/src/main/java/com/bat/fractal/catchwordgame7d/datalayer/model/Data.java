package com.bat.fractal.catchwordgame7d.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dangm on 03/20/18.
 */

public class Data {

    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("current_time")
    @Expose
    private long currentTime;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
