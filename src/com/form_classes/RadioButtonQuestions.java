package com.form_classes;

import java.io.Serializable;
import java.util.*;

public class RadioButtonQuestions extends FormQuestions implements Serializable {

    private int numberOfAnswers;
    private List<String> answerList;


    public RadioButtonQuestions(int uniqueID, int numberOfAnswers, String questionText) {
        super(uniqueID, questionText);
        this.numberOfAnswers = numberOfAnswers;
        this.answerList = new ArrayList<>();
    }

    public void addAnswerToList(String answer) {
        answerList.add(answer);
    }


    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public List<String> getAnswerList() {
        return answerList;
    }


    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    @Override
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
