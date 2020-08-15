package com.language;

import java.util.ArrayList;
import java.util.List;

public class ScaleQuestionsLanguage {

    private int selectedLanguageIndex;

    public ScaleQuestionsLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
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
}
