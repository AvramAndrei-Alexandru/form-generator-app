package com.language;

import com.database_layer.LanguageDAO;

import java.util.ArrayList;
import java.util.List;

public class LanguageContainer {

    private static LanguageContainer instance;
    private List<String> languageList;
    private int selectedLanguageIndex;

    private LanguageContainer() {
        selectedLanguageIndex = LanguageDAO.getLanguageIndexFromDataBase();
        languageList = new ArrayList<>();
        languageList.add("English");
        languageList.add("Română");
    }


    public String getSelectedLanguageLabelText() {
        if(selectedLanguageIndex == 0)
            return "Selected language";
        else
            return "Limba selectată";
    }

    public String getWarning() {
        if(selectedLanguageIndex == 0)
            return "Warning";
        else
            return "Avertizare";
    }

    public static LanguageContainer getInstance() {
        if(instance == null) {
            instance = new LanguageContainer();
            return instance;
        } else {
            return instance;
        }
    }

    public List<String> getLanguageList() {
        return languageList;
    }

    public int getSelectedLanguageIndex() {
        return selectedLanguageIndex;
    }

    public void setSelectedLanguageIndex(int selectedLanguageIndex) {
        this.selectedLanguageIndex = selectedLanguageIndex;
        LanguageDAO.insertSelectedLanguageIndexInDatabase(selectedLanguageIndex);
    }


    public MainFormDesignerLanguage getMainFormDesignerLanguagePack() {
        return new MainFormDesignerLanguage(selectedLanguageIndex);
    }

    public DesignLayoutLanguage getDesignLayoutLanguagePack() {
        return new DesignLayoutLanguage(selectedLanguageIndex);
    }

    public RadioButtonCheckBoxQuestionsLanguage getRadioButtonCheckBoxQuestionsLanguage() {
        return new RadioButtonCheckBoxQuestionsLanguage(selectedLanguageIndex);
    }

    public FormLinksLanguage getFormLinksLanguagePack() {
        return new FormLinksLanguage(selectedLanguageIndex);
    }

    public QuestionVisibilityLanguage getQuestionVisibilityLanguagePack() {
        return new QuestionVisibilityLanguage(selectedLanguageIndex);
    }

    public PreviewLanguage getPreviewLanguagePack() {
        return new PreviewLanguage(selectedLanguageIndex);
    }

    public PreviewQuestionsLanguage getPreviewQuestionLanguage() {
        return new PreviewQuestionsLanguage(selectedLanguageIndex);
    }

    public ScaleQuestionsLanguage getScaleQuestionsLanguagePack() {
        return new ScaleQuestionsLanguage(selectedLanguageIndex);
    }
}
