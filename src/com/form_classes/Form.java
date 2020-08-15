package com.form_classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Form implements Serializable , Comparable<Form>{

    private List<FormQuestions> formQuestions;
    private String formName;
    private int firstQuestionID;
    private boolean containsDefaultQuestions;

    public Form(String formName) {
        this.formName = formName;
        formQuestions = new ArrayList<>();
        containsDefaultQuestions = true;
    }

    public void addQuestionToForm(FormQuestions formQuestion) {
        formQuestions.add(formQuestion);
    }

    public List<FormQuestions> getFormQuestions() {
        return formQuestions;
    }

    public String getFormName() {
        return formName;
    }

    public int getFirstQuestionID() {
        return firstQuestionID;
    }

    public void setFormQuestions(List<FormQuestions> formQuestions) {
        this.formQuestions = formQuestions;
    }

    public void setFirstQuestionID(int firstQuestionID) {
        this.firstQuestionID = firstQuestionID;
    }

    @Override
    public int compareTo(Form o) {
        return this.formName.compareTo(o.getFormName());
    }

    public boolean containsDefaultQuestions() {
        return containsDefaultQuestions;
    }

    public void setContainsDefaultQuestions(boolean containsDefaultQuestions) {
        this.containsDefaultQuestions = containsDefaultQuestions;
    }
}
