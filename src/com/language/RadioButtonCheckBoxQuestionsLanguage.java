package com.language;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonCheckBoxQuestionsLanguage {
    private int selectedLanguageIndex;

    public RadioButtonCheckBoxQuestionsLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getAddAnswerButtonText() {
        if(selectedLanguageIndex == 0)
            return "Add answer";
        else
            return "Adaugă răspuns";
    }

    public String getQuestionTextPrompt() {
        if(selectedLanguageIndex == 0)
            return "Question text";
        else
            return "Textul întrebării";
    }

    public String getSaveItemButtonText() {
        if(selectedLanguageIndex == 0)
            return "Save question";
        else
            return "Salvează întrebarea";
    }

    public String getDeleteAnswerButtonText() {
        if(selectedLanguageIndex == 0)
            return "Delete answer";
        else
            return "Șterge răspunsul";
    }

    public String getAnswerTextFieldPromptText() {
        if(selectedLanguageIndex == 0)
            return "Enter the current answer";
        else
            return "Introduceți răspunsul";
    }

    public List<String> getSaveAlertList() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0) {
            text.add("The question can not be saved. The question text is not set.");
            text.add("The question can not be saved. The form contains another question with the same text.");
            text.add("The question can not be saved. No answers were added.");
            text.add("The question can not be saved. Some answers ratings are not set.");
            text.add("The question can not be saved. Some answers are empty.");
            text.add("The question can not be saved. Some answers have the same text.");
            text.add("The question can not be saved. Maximum number of answers is not selected.");
        }else {
            text.add("Întrebarea nu se poate salva. Textul întrebării nu este setat.");
            text.add("Întrebarea nu se poate salva. Chestionarul conține deja o întrebare cu același text.");
            text.add("Întrebarea nu se poate salva. Întrebarea nu are răspunsuri.");
            text.add("Întrebarea nu se poate salva. Unele răspunsuri nu au scorul salvat.");
            text.add("Întrebarea nu se poate salva. Unele răspunsuri sunt goale.");
            text.add("Întrebarea nu se poate salva. Unele răspunsuri sunt identice.");
            text.add("Întrebarea nu se poate salva. Numărul maxim de răspunsuri nu este setat");
        }

        return text;
    }

    public String getMaximumNumberOfAnswersText() {
        if(selectedLanguageIndex == 0)
            return "Maximum number of answers ";
        else
            return "Numărul maxim de răspunsuri";
    }

    public String getTextAreaPrompt() {
        if(selectedLanguageIndex == 0)
            return "Here the user will type the answer.";
        else
            return "Aici se va scrie răspunsul";
    }
}
