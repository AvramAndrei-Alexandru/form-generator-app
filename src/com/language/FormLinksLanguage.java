package com.language;

public class FormLinksLanguage {

    private int selectedLanguageIndex;

    public FormLinksLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getQuestionsUpButtonText() {
        if(selectedLanguageIndex == 0)
            return "Move question up";
        else
            return "Mută întrebarea în sus";
    }

    public String getQuestionDownButtonText() {
        if(selectedLanguageIndex == 0)
            return "Move question down";
        else
            return "Mută întrebarea în jos";
    }

    public String getQuitButtonText() {
        if(selectedLanguageIndex == 0)
            return "Save questions order";
        else
            return "Salvează ordinea întrebărilor";
    }
}
