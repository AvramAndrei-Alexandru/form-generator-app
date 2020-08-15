package com.gui.preview_gui_layer;

import com.form_classes.FormQuestions;
import com.form_classes.FreeTextQuestions;
import com.language.LanguageContainer;
import com.language.PreviewQuestionsLanguage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class FreeTextQuestionsPreviewController extends AnchorPane {

    private FreeTextQuestions question;
    @FXML private TextArea answerTextArea;
    private PreviewQuestionsLanguage languagePack;

    public FreeTextQuestionsPreviewController(FormQuestions formQuestions) {
        languagePack = LanguageContainer.getInstance().getPreviewQuestionLanguage();
        question = (FreeTextQuestions) formQuestions;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FreeTextQuestionsPreviewFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextArea questionTextField;

    @FXML public void initialize() {
        answerTextArea.setPromptText(languagePack.getFreeTextQuestionTextArea());
        questionTextField.setText(question.getQuestionText());
        questionTextField.setEditable(false);
    }
}
