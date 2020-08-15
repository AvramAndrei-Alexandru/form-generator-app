package com.form_classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxQuestions extends FormQuestions implements Serializable {

    private int numberOfAnswers;
    private int maximumNumberOfAnswersAllowed;
    private List<String> answerList;
    private int longestAnswerSize;


    public CheckBoxQuestions(int uniqueID, int numberOfAnswers, String questionText, int maximumNumberOfAnswersAllowed) {
        super(uniqueID, questionText);
        this.numberOfAnswers = numberOfAnswers;
        this.maximumNumberOfAnswersAllowed = maximumNumberOfAnswersAllowed;
        this.answerList = new ArrayList<>();
    }


    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public int getMaximumNumberOfAnswersAllowed() {
        return maximumNumberOfAnswersAllowed;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void addAnswer(String answer) {
        answerList.add(answer);
    }

    public int getLongestAnswerSize() {
        if(answerList.size() == 0)
            return 0;
        else {
            int longestAnswerSize = answerList.get(0).length();
            for(String answer : answerList) {
                if(answer.length() > longestAnswerSize)
                    longestAnswerSize = answer.length();
            }
            return longestAnswerSize;
        }
    }

}
