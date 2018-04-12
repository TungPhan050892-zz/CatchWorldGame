package com.bat.fractal.catchwordgame7d.datalayer.model;

import android.util.Log;

import com.bat.fractal.catchwordgame7d.common.Strings;

/**
 * Created by phant on 22-03-18.
 */

public class Answer {

    private StringBuilder userAnswer = new StringBuilder();
    private String hiddenAnswer;
    private int highLightIndex = -1;

    public StringBuilder getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(StringBuilder userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getHiddenAnswer() {
        return hiddenAnswer;
    }

    public void setHiddenAnswer(String hiddenAnswer) {
        this.hiddenAnswer = hiddenAnswer;
    }

    public int getHighLightIndex() {
        return highLightIndex;
    }

    public void setHighLightIndex(int highLightIndex) {
        this.highLightIndex = highLightIndex;
    }

    public void reset() {
        userAnswer = new StringBuilder();
        hiddenAnswer = Strings.EMPTY;
        highLightIndex = -1;
    }

    public String getHiddenAnswerWithNoSpace() {
        return hiddenAnswer.trim()
                .replace(Strings.SPACE, Strings.EMPTY);
    }

    public int getHiddenAnswerSpaceCount(){
        int spaceCount = 0;
        for (char c : hiddenAnswer.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    public int hiddenAnswerNoSpaceLength() {
        return getHiddenAnswerWithNoSpace().length();
    }

    public int userAnswerLength(){
        return userAnswer.length();
    }

    public int hiddenAnswerLength(){
        return hiddenAnswer.length();
    }
}
