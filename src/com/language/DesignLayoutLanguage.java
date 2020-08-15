package com.language;

import java.util.ArrayList;
import java.util.List;

public class DesignLayoutLanguage {

    private int selectedLanguageIndex;

    public DesignLayoutLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getRadioButtonQuestionsButtonText() {
        if(selectedLanguageIndex == 0)
            return "Single answer questions";
        else
            return "Întrebări cu o singură varianta de răspuns";
    }

    public String getCheckBoxQuestionsButtonText() {
        if(selectedLanguageIndex == 0)
            return "Multiple answer questions";
        else
            return "Întrebări cu răspuns multiplu";
    }

    public String getFreeTextQuestionsButtonText() {
        if(selectedLanguageIndex == 0)
            return "Text answer questions";
        else
            return "Întrebări cu răspuns de tip text";
    }

    public String getEditVisibilityButtonText() {
        if(selectedLanguageIndex == 0)
            return "Set the question visibility conditions";
        else
            return "Setează condițiile de vizibilitate a întrebărilor";
    }

    public String getFormLinksButtonText() {
        if(selectedLanguageIndex == 0)
            return "Set questions order";
        else
            return "Setează ordinea întrebărilor";
    }

    public String getSaveFormButtonText() {
        if(selectedLanguageIndex == 0)
            return "Save the form and quit the designer";
        else
            return "Salvează chestionarul și părăsește editorul";
    }

    public String getFormQuestionsComboBoxText() {
        if(selectedLanguageIndex == 0)
            return "Form questions. Select one to remove it.";
        else
            return "Întrebările chestionarului. Selectați una pentru a o șterge";
    }

    public String getDeleteQuestionButtonText() {
        if(selectedLanguageIndex == 0)
            return "Delete selected question";
        else
            return "Șterge întrebarea selectată";
    }

    public List<String> getSaveButtonConfirmationList() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0) {
            text.add("Confirmation");
            text.add("Save the form and quit the designer.");
            text.add("If you press OK you will not be able to edit this form anymore.");
        }else {
            text.add("Confirmare");
            text.add("Salvează chestionarul și părăsește editorul.");
            text.add("Dacă apăsăți OK nu o sa mai puteți edita chestionarul.");
        }
        return text;
    }

    public String getSaveAlert() {
        if(selectedLanguageIndex == 0)
            return "To save the form you must add at least one question.";
        else
            return "Pentru a salva chestionarul trebuie să aveți cel puțin o întrebare in el";
    }

    public String getDeleteQuestionAlert() {
        if(selectedLanguageIndex == 0)
            return "To delete a question you must select one first.";
        else
            return "Selectați o întrebare pentru a o șterge";
    }

    public String getFormLinksAlert() {
        if(selectedLanguageIndex == 0)
            return "To modify the order of the questions you must add questions first";
        else
            return "Pentru a modifică ordinea întrebărilor trebuie să adăugați întrebări mai întâi";
    }

    public List<String> getQuestionVisibilityConfirmationList() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0){
            text.add("Confirmation");
            text.add("Edit the questions visibility and save the form.");
            text.add("If you press OK you will not be able to add or remove questions to the form anymore.");
        }else {
            text.add("Confirmare");
            text.add("Setează condițiile de vizibilitate a întrebărilor");
            text.add("Dacă apăsăți OK nu o să mai puteți adauga întrebări sau modifica ordinea lor");
        }
        return text;
    }

    public String getQuestionVisibilityAlert() {
        if(selectedLanguageIndex == 0)
            return "To change the visibility of the questions you must add at least two questions to the form.";
        else
            return "Pentru a modifica condițiile de vizibilitate a întrebărilor trebuie să aveți cel puțin două întrebări in chestionar";
    }

    public String getScaleQuestionsButtonText() {
        if(selectedLanguageIndex == 0)
            return "Scale type question";
        else
            return "Întrebări de tip scală";
    }

    public String getDiscardProgressButtonText() {
        if(selectedLanguageIndex == 0)
            return "Discard progress and quit the designer";
        else
            return "Renunța la chestionar și părăsește editorul";
    }

    public List<String> getDiscardProgressConfirmationText() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0){
            text.add("Confirmation");
            text.add("Discard the current form progress and quit the designer");
            text.add("If you press OK the work on this form will not be saved.");
        }else {
            text.add("Confirmare");
            text.add("Renunța la chestionar și părăsește editorul");
            text.add("Dacă apăsăți OK progresul la acest chestionar nu o să se salveze");
        }
        return text;
    }

}
