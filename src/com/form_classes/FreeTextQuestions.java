package com.form_classes;

import java.io.Serializable;

public class FreeTextQuestions extends FormQuestions implements Serializable {


    public FreeTextQuestions(int uniqueID, String questionText) {
        super(uniqueID, questionText);
    }

    @Override
    public int getLongestAnswerSize() {
        return  1024;
    }
}
