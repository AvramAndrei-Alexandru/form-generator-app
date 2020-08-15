package com.language;

import java.util.ArrayList;
import java.util.List;

public class MainFormDesignerLanguage {
    private int selectedLanguageIndex;

    public MainFormDesignerLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getCreateNewFormButtonText() {
        if(selectedLanguageIndex == 0)
            return "Create new form";
        else return "Creează un chestionar nou";
    }
    public String getPreviewFormButtonText() {
        if(selectedLanguageIndex == 0)
            return "View selected form";
        else
            return "Vizualizează chestionarul selectat";
    }
    public String getDeleteButtonText() {
        if(selectedLanguageIndex == 0)
            return "Delete selected form";
        else
            return "Șterge chestionarul selectat";
    }
    public String getSavedFormComboBoxPromptText() {
        if(selectedLanguageIndex == 0)
            return "Saved forms";
        else
            return "Chestionare salvate";
    }
    public String getFormNameTextFieldPromptText() {
        if(selectedLanguageIndex == 0)
            return "Enter the form name";
        else
            return "Introduceți numele chestionarului";
    }
    public String getSaveFormButtonText() {
        if(selectedLanguageIndex == 0)
            return "Save the form name and launch the layout designer";
        else
            return "Salvează numele chestionarului și deschide editorul";
    }
    public String getDefaultQuestionsLabelText() {
        if(selectedLanguageIndex == 0)
            return "Generate default questions";
        else
            return "Generează întrebările standard";
    }
    public String getPreviewAlertText() {
        if(selectedLanguageIndex == 0)
            return "To view a form you must select one first";
        else
            return "Selectați un chestionar pentru a îl vizualiza";
    }
    public String getAnotherFormNameExistsAlertText(String formName) {
        if(selectedLanguageIndex == 0)
            return "Another form with the name " + formName + " exists. You must use another name";
        else
            return "Un alt chestionar cu numele" + formName + " există deja. Alegeți alt nume";
    }
    public String getDeleteFormAlertText() {
        if(selectedLanguageIndex == 0)
            return "To delete a form you must select one first";
        else
            return "Selectați un chestionar pentru a îl șterge";
    }
    public List<String> getDeleteConfirmationText() {
        List<String> text = new ArrayList<>();
        if(selectedLanguageIndex == 0) {
            text.add("Confirmation");
            text.add("Delete selected form");
            text.add("If you press OK you will delete the selected form. Are you sure you want to do this?");
        }
        else {
            text.add("Confirmare");
            text.add("Șterge chestionarul selectat");
            text.add("Dacă apăsăți pe OK chestionarul selectat se va șterge. Doriți să faceți acest lucru?");
        }
        return text;
    }

    public String getAnotherFormIsOpenAlertText() {
        if(selectedLanguageIndex == 0)
            return "Cannot open form designer because another form is being worked on";
        else
            return "Nu se poate deschide editorul deoarece se lucrează deja la un chestionar";
    }

    public String getQuitButtonText() {
        if(selectedLanguageIndex == 0)
            return "Quit application";
        else
            return "Părăsește aplicația";
    }

    public List<String> getQuitConfirmationText() {
        List<String> text = new ArrayList<>();
        if(selectedLanguageIndex == 0) {
            text.add("Confirmation");
            text.add("Quit application");
            text.add("If you press OK you will exit the application.");
        }
        else {
            text.add("Confirmare");
            text.add("Părăsește aplicația");
            text.add("Dacă apăsăți pe OK aplicația se va închide");
        }
        return text;
    }
}
