package com.language;

import java.util.ArrayList;
import java.util.List;

public class PreviewLanguage {
    private int selectedLanguageIndex;

    public PreviewLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getPreviousQuestionButtonText() {
        if(selectedLanguageIndex == 0)
            return "Previous question";
        else
            return "Întrebarea precedentă";
    }

    public String getNextQuestionButtonText() {
        if(selectedLanguageIndex == 0)
            return "Next question";
        else
            return "întrebarea următoare";
    }

    public String getQuitButtonText() {
        if(selectedLanguageIndex == 0)
            return "Quit form preview";
        else
            return "Părăsește vizualizarea";
    }

    public String getLastQuestionAlert() {
        if(selectedLanguageIndex == 0)
            return "You are at the last question.";
        else
            return "Sunteți la ultima întrebare";
    }

    public String getFirstQuestionAlert() {
        if(selectedLanguageIndex == 0)
            return "You are at the first question.";
        else
            return "Sunteți la prima întrebare";
    }

    public List<String> getConditionsList() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0) {
            text.add("The question will be visible if the following answers are selected: ");
            text.add("No conditions are required to display this question");
        }else {
            text.add("Întrebarea o să fie vizibilă dacă următoarele condiții se îndeplinesc:");
            text.add("Întrebarea nu are nici o condiție de vizibilitate");
        }

        return text;
    }
}
