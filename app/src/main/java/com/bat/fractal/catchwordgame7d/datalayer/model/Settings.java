package com.bat.fractal.catchwordgame7d.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dangm on 03/20/18.
 */

public class Settings {

    @SerializedName("number_of_won_question")
    @Expose
    private String numberOfWonQuestion;
    @SerializedName("try_times")
    @Expose
    private String tryTimes;
    @SerializedName("time_per_question")
    @Expose
    private String timePerQuestion;

    public String getNumberOfWonQuestion() {
        return numberOfWonQuestion;
    }

    public void setNumberOfWonQuestion(String numberOfWonQuestion) {
        this.numberOfWonQuestion = numberOfWonQuestion;
    }

    public String getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(String tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(String timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

}
