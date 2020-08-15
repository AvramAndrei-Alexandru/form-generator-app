package com.language;

public class PreviewQuestionsLanguage {
    private int selectedLanguageIndex;

    public PreviewQuestionsLanguage(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
    }

    public String getMaximumNumberOFAnswersText(int maximumNumberOfAnswers) {
        if(selectedLanguageIndex == 0)
            return "A maximum number of " + maximumNumberOfAnswers + " answers can be selected.";
        else
            return "Un număr maxim de " + maximumNumberOfAnswers + " răspuns(uri) se poate selecta";
    }

    public String getAnswerLabelText() {
        if(selectedLanguageIndex == 0)
            return "Only one answer can be selected.";
        else
            return "Un singur răspuns se poate selecta";
    }

    public String getFreeTextQuestionTextArea() {
        if(selectedLanguageIndex == 0)
            return "Write here the answer to the question.";
        else
            return "Scrie aici răspunsul la întrebare";
    }
}
