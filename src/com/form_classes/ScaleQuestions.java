package com.form_classes;

import java.io.Serializable;

public class ScaleQuestions extends FormQuestions implements Serializable {

    public ScaleQuestions(int uniqueID, String questionText) {
        super(uniqueID, questionText);
    }

    @Override
    public int getLongestAnswerSize() {
        return 2;
    }
}
