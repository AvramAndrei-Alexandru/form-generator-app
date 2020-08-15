package com.form_classes;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormQuestions implements Serializable {

    private int uniqueID;
    private String questionText;
    private Map<Integer, String> conditionsMap;

    public FormQuestions(int uniqueID, String questionText) {
        this.uniqueID = uniqueID;
        this.questionText = questionText;
        conditionsMap = new LinkedHashMap<>();
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Map<Integer, String> getConditionsMap() {
        return conditionsMap;
    }

    public void setConditionsMap(Map<Integer, String> conditionsMap) {
        this.conditionsMap = conditionsMap;
    }

    public  int getLongestAnswerSize() {
        return 0;
    }
}
