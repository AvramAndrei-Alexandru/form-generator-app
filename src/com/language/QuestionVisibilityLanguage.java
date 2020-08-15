package com.language;

import java.util.ArrayList;
import java.util.List;

public class QuestionVisibilityLanguage {
    private int selectedLanguageIndex;

    public QuestionVisibilityLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public List<String> getSaveConfirmationAlert() {
        List<String> text = new ArrayList<>();

        if(selectedLanguageIndex == 0) {
            text.add("Confirmation");
            text.add("Save the form and quit the question visibility.");
            text.add("If you press OK you will not be able to edit this form anymore.");
        }else {
            text.add("Confirmare");
            text.add("Salvează chestionarul.");
            text.add("Dacă apăsați OK nu o să mai puteți edita chestionarul");
        }
        return text;
    }

    public String getTextFieldPrompt() {
        if(selectedLanguageIndex == 0)
            return "Question text:";
        else
            return "Textul întrebării";
    }

    public String getMenuButtonText() {
        if(selectedLanguageIndex == 0)
            return "Select conditions";
        else
            return "Selecteaza condițiile";
    }

    public String getSaveFormButtonText() {
        if(selectedLanguageIndex == 0)
            return "Save the form";
        else
            return "Salvează chestionarul";
    }

    public String getRadioAlert(int ID) {
        if(selectedLanguageIndex == 0)
            return "You cannot select multiple answers from single answer questions. question ID = " + ID ;
        else
            return "Nu puteți selecta mai multe răspunsuri ce aparțin unei întrebări cu o singură variantă de răspuns. ID-ul întrebării = " + ID;
    }

    public String getCheckAlert(int ID) {
        if(selectedLanguageIndex == 0)
            return "You cannot select a number of answers that is greater than the maximum number of answers allowed. question ID = " + ID;
        else
            return "Nu puteți selecta mai multe răspunsuri ca numărul maxim de răspunsuri admise. ID-ul întrebării = " + ID;
    }
}
