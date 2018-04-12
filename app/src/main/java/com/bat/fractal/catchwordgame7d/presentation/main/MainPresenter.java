package com.bat.fractal.catchwordgame7d.presentation.main;

import android.content.Context;

import com.bat.fractal.catchwordgame7d.common.Constant;
import com.bat.fractal.catchwordgame7d.common.Strings;
import com.bat.fractal.catchwordgame7d.datalayer.model.Answer;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.repository.CatchWordGameDataRepository;
import com.bat.fractal.catchwordgame7d.presentation.base.BasePresenter;
import com.bat.fractal.catchwordgame7d.presentation.widget.adapter.AnswerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by dangm on 03/19/18.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private final String TAG = MainPresenter.class.getSimpleName();
    private Answer firstAnswer;//answer for line 1
    private Answer secondAnswer;//answer for line 2
    private int level = 1;
    private int live;
    private AnswerAdapter firstAdapter;//adapter for first answer recycler view
    private AnswerAdapter secondAdapter;//adapter for second answer recycler view
    private boolean suggested;

    int getLevel() {
        return level;
    }

    void increaseLevel() {
        this.level++;
    }

    void setLive(int live) {
        this.live = live;
    }

    void increaseLive() {
        this.live++;
    }

    AnswerAdapter getFirstAdapter() {
        return firstAdapter;
    }

    AnswerAdapter getSecondAdapter() {
        return secondAdapter;
    }

    boolean isSuggested() {
        return suggested;
    }

    void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }

    @Inject
    public MainPresenter(CatchWordGameDataRepository repository) {

    }

    private String createSpaceString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(Strings.SPACE);
        }
        return result.toString();
    }

    String getFirstLineFrom(String input) {
        final String[] inputArray = input.trim().split(Strings.SPACE_REGEX);
        if (inputArray.length > 2) {
            final int index = getSplitWordIndexFrom(inputArray);
            //create first line center string
            final StringBuilder firstLineString = new StringBuilder();
            for (int i = 0; i < index - 1; i++) {
                firstLineString.append(inputArray[i])
                        .append(Strings.SPACE);
            }
            return firstLineString.toString().trim();
        } else {
            return inputArray[0];//return second item in array
        }
    }

    String getSecondLineFrom(String input) {
        final String[] inputArray = input.trim().split(Strings.SPACE_REGEX);
        if (inputArray.length > 2) {
            final int index = getSplitWordIndexFrom(inputArray);
            //create second line center
            final StringBuilder secondLineString = new StringBuilder();
            for (int i = index - 1; i < inputArray.length; i++) {
                secondLineString.append(inputArray[i])
                        .append(Strings.SPACE);
            }
            return secondLineString.toString().trim();
        } else {
            return inputArray[1];//return second item in array
        }
    }

    /**
     * Author: Tung Phan
     * Method Explaination: get index of the word which make input length > recyclerview column number
     */
    private int getSplitWordIndexFrom(String[] inputArray) {
        int index = 1;
        final StringBuilder tempString = new StringBuilder(inputArray[0]);
        while (tempString.length() < Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3) {
            tempString.append(Strings.SPACE)
                    .append(inputArray[index]);
            index++;
        }
        return index;
    }

    /**
     * Author: Tung Phan
     * Method Explaination: convert input String to RV Choice String by add the input String
     * with random alphabet character
     */
    String convertToRVchoiceString(String input) {
        if (input == null || input.isEmpty()) {
            return createSpaceString(Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3);
        }
        final String randomResult = generateRandomResult(input.replace(Strings.SPACE, Strings.EMPTY));
        return generateRVChoiceStringFromInput(mixString(randomResult));
    }

    private String generateRVChoiceStringFromInput(String randomResult) {
        switch (randomResult.length()) {
            case Constant.CHOICE_NUMBER_LENGTH_1:
                return randomResult;
            case Constant.CHOICE_NUMBER_LENGTH_2:
                return createRVChoiceHaflLine(randomResult);
            case Constant.CHOICE_NUMBER_LENGTH_3:
                return randomResult;
            default:
                return Strings.EMPTY;
        }
    }

    private String createRVChoiceHaflLine(String randomResult) {
        final StringBuilder result = new StringBuilder();
        result.append(randomResult.substring(0,
                Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3))
                .append(Strings.SPACE)
                .append(Strings.SPACE)
                .append(randomResult.substring(randomResult.length()
                                - Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3 / 2,
                        randomResult.length()))
                .append(Strings.SPACE)
                .append(Strings.SPACE);
        return result.toString();
    }

    /**
     * Author: Tung Phan
     * Method Explaination: generate random string for rvchoice from input String
     */
    private String generateRandomResult(String input) {
        final List<Character> alphabetInput = createAlphabetInput(input);
        final int outputLength = caculateOutputLengthBaseOnInput(input);
        StringBuilder randomResult = new StringBuilder();
        while (alphabetInput.size() < outputLength) {
            Character c = randomFromAlphabet(alphabetInput);
            alphabetInput.add(c);
        }
        for (Character c : alphabetInput) {
            randomResult.append(c);
        }
        return randomResult.toString();
    }

    /**
     * Author: Tung Phan
     * Method Explaination: convert String to Alphabet Array list of Character
     */
    private List<Character> createAlphabetInput(String input) {
        char[] inputArray = input.toCharArray();
        List<Character> alphabetInput = new ArrayList<>();
        alphabetInput.add(inputArray[0]);
        for (int i = 1; i < inputArray.length; i++) {
            boolean hasCharacter = false;
            for (Character c : alphabetInput) {
                if (c == inputArray[i]) {
                    hasCharacter = true;
                }
            }
            if (!hasCharacter) {
                alphabetInput.add(inputArray[i]);
            }
        }
        return alphabetInput;
    }

    private int caculateOutputLengthBaseOnInput(String input) {
        if (input.length() <= Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3 - 2) {
            return Constant.CHOICE_NUMBER_LENGTH_1;
        } else if (input.length() <= Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3 + 2) {
            return Constant.CHOICE_NUMBER_LENGTH_2;
        } else {
            return Constant.CHOICE_NUMBER_LENGTH_3;
        }
    }

    private Character randomFromAlphabet(List<Character> alphabetInput) {
        Map<Integer, Character> alphabet = createAlphabet();
        //remove character in alphabet input
        for (Iterator<Map.Entry<Integer, Character>> it = alphabet.entrySet().iterator();
             it.hasNext(); ) {
            Map.Entry<Integer, Character> entry = it.next();
            for (Character c : alphabetInput) {
                if (c == entry.getValue()) {
                    it.remove();
                }
            }
        }
        //random 1 character from the rest of item in alphabet
        Character randomValue = null;
        while (randomValue == null) {
            Random r = new Random();
            Integer randomIndex = r.nextInt(alphabet.size());
            randomValue = alphabet.get(randomIndex);
        }
        return randomValue;
    }

    private Map<Integer, Character> createAlphabet() {
        int index = 0;
        Map<Integer, Character> alphabet = new HashMap<>();
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            alphabet.put(index, ch);
            index++;
        }
        return alphabet;
    }

    private String mixString(String input) {
        StringBuilder resultBuilder = new StringBuilder(input);
        Random r = new Random();
        Integer randomIndex;
        for (int i = 0; i < resultBuilder.length(); i++) {
            randomIndex = r.nextInt(input.length());
            resultBuilder.append(resultBuilder.charAt(randomIndex));
            resultBuilder.deleteCharAt(randomIndex);
        }
        return resultBuilder.toString();
    }

    Question randomQuestionFromQuestions(List<Question> questions) {
        Random r = new Random();
        Integer randomIndex = r.nextInt(questions.size());
        Question question = questions.get(randomIndex);
        questions.remove(question);
        return question;
    }

    void processSuggest() {
        // this is just suggest the next word only
        final String lineOne = firstAnswer.getHiddenAnswerWithNoSpace();
        final String lineTwo = secondAnswer.getHiddenAnswerWithNoSpace();
        if (canSuggest()) {
            final String hiddenAnswerNoSpace = lineOne + lineTwo;
            final int userAnswerLength = userAnswerLength();
            final Character suggestChar = hiddenAnswerNoSpace.charAt(userAnswerLength);
            if (userEnteringFirstAnswer()) {
                firstAnswer.setHighLightIndex(userAnswerLength);
                if (userAnswerLength < hiddenAnswerNoSpace.length() - 1) {
                    highLightSuggestChar(suggestChar,
                            hiddenAnswerNoSpace.charAt(userAnswerLength + 1),
                            firstAdapter,
                            firstAnswer);
                } else {
                    highLightSuggestChar(suggestChar,
                            Strings.CHAR_SPACE,
                            firstAdapter,
                            firstAnswer);
                }
            } else {
                final int firstLineLength = firstAnswer.userAnswerLength();
                secondAnswer.setHighLightIndex(userAnswerLength - firstLineLength);
                if (userAnswerLength < hiddenAnswerNoSpace.length() - 1) {

                    highLightSuggestChar(suggestChar,
                            hiddenAnswerNoSpace.charAt(userAnswerLength + 1 - firstLineLength),
                            secondAdapter,
                            secondAnswer);
                } else {
                    highLightSuggestChar(suggestChar,
                            Strings.CHAR_SPACE,
                            secondAdapter,
                            secondAnswer);
                }
            }
            if (userAnswerLength == hiddenAnswerNoSpace.length() - 1) {
                getView().enableButtonAnswer();
            }
        }
    }

    boolean canSuggest() {
        final int hiddenAnswerLength = firstAnswer.hiddenAnswerNoSpaceLength()
                + secondAnswer.hiddenAnswerNoSpaceLength();
        final int userAnswerLength = firstAnswer.userAnswerLength()
                + secondAnswer.userAnswerLength();
        return userAnswerLength < hiddenAnswerLength;
    }

    boolean userEnteringFirstAnswer() {
        return firstAnswer.userAnswerLength() < firstAnswer.hiddenAnswerLength();
    }

    int userAnswerLength() {
        return firstAnswer.userAnswerLength() + secondAnswer.userAnswerLength();
    }

    void highLightSuggestChar(Character suggestChar,
                              Character nextChar,
                              AnswerAdapter answerAdapter,
                              Answer answer) {
        if (suggestChar == Strings.CHAR_SPACE) {
            answer.getUserAnswer().append(nextChar);
        } else {
            answer.getUserAnswer().append(suggestChar);
        }
        getView().refreshRVanswer(answerAdapter, answer);
    }

    void processComplete() {
        final String hiddenAnswer = firstAnswer.getHiddenAnswer()
                .replaceAll(Strings.SPACE_REGEX, Strings.EMPTY)
                + secondAnswer.getHiddenAnswer().replaceAll(Strings.SPACE_REGEX, Strings.EMPTY);
        final String answer = firstAnswer.getUserAnswer().toString()
                + secondAnswer.getUserAnswer().toString();
        if (hiddenAnswer.equalsIgnoreCase(answer.toString())) {
            if (level < numberOfWonQuestion) {
                getView().showCorrect();
            } else {
                getView().showCongratulation();
            }
        } else {
            if (live == tryTime - 1) {
                getView().showOutOfTime();
            } else {
                getView().showIncorrect();
            }
        }
    }

    void clearRVanswers() {
        firstAnswer.setUserAnswer(new StringBuilder(Strings.EMPTY));
        firstAnswer.setHighLightIndex(-1);
        secondAnswer.setUserAnswer(new StringBuilder(Strings.EMPTY));
        secondAnswer.setHighLightIndex(-1);
        getView().clearRVanswers(firstAnswer, secondAnswer);
    }

    boolean outOfTimeToPlay() {
        return live == tryTime - 1;
    }

    boolean startGame() {
        return level == 1;
    }

    void initFirstAnswer(String input) {
        firstAnswer = new Answer();
        firstAnswer.setHiddenAnswer(input);
    }

    void initSecondAnswer(String input) {
        secondAnswer = new Answer();
        secondAnswer.setHiddenAnswer(input);
    }

    Answer getFirstAnswer() {
        return firstAnswer;
    }

    Answer getSecondAnswer() {
        return secondAnswer;
    }

    void processChoice(String string) {
        final int hiddenAnswerLength1 = firstAnswer.getHiddenAnswer().replaceAll(Strings.SPACE_REGEX,
                Strings.EMPTY).length();
        final int hiddenAnswerLength2 = secondAnswer.getHiddenAnswer().replaceAll(Strings.SPACE_REGEX,
                Strings.EMPTY).length();
        final int answerLength = firstAnswer.userAnswerLength()
                + secondAnswer.userAnswerLength();
        if (answerLength < hiddenAnswerLength1 + hiddenAnswerLength2) {
            if (answerLength < hiddenAnswerLength1) {
                //update rview line 1
                firstAnswer.getUserAnswer().append(string);
                getView().refreshRVanswer(firstAdapter, firstAnswer);
            } else {
                //update rview line 2
                secondAnswer.getUserAnswer().append(string);
                getView().refreshRVanswer(secondAdapter, secondAnswer);
            }
        }
        if (answerLength + 1 == hiddenAnswerLength1 + hiddenAnswerLength2) {
            getView().relayoutWhenAnswerFilled();
        }
    }

    void initFirstAdapter(Context context) {
        firstAdapter = new AnswerAdapter(context, firstAnswer);
    }

    void initSecondAdapter(Context context) {
        secondAdapter = new AnswerAdapter(context, secondAnswer);
    }

    private boolean userEnteringSecondAnswer() {
        return secondAnswer.userAnswerLength() > 0;
    }

    void processClear() {
        if (userEnteringSecondAnswer()) {
            final int length = secondAnswer.userAnswerLength();
            secondAnswer.getUserAnswer().deleteCharAt(length - 1);
        } else {
            final int length = firstAnswer.userAnswerLength();
            firstAnswer.getUserAnswer().deleteCharAt(length - 1);
        }
        getView().clearRVanswers(firstAnswer, secondAnswer);
    }

}
