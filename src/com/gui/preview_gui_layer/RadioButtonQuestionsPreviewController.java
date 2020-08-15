package com.gui.preview_gui_layer;

import com.form_classes.FormQuestions;
import com.form_classes.RadioButtonQuestions;
import com.language.LanguageContainer;
import com.language.PreviewQuestionsLanguage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RadioButtonQuestionsPreviewController extends AnchorPane {

    private RadioButtonQuestions question;
    private AnchorPane anchorPane2 = new AnchorPane();
    private List<String> answerList;
    private PreviewQuestionsLanguage languagePack;

    @FXML private TextArea questionTextField;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane anchorPane;
    @FXML private Label answerLabel;


    public RadioButtonQuestionsPreviewController(FormQuestions formQuestion) {
        languagePack = LanguageContainer.getInstance().getPreviewQuestionLanguage();
        this.question = (RadioButtonQuestions) formQuestion;
        answerList = question.getAnswerList();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RadioButtonQuestionsPreviewFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        answerLabel.setText(languagePack.getAnswerLabelText());
        loadAnswers();
    }

    /**
     * This method is used to load all the answers the question contains.
     */
    private void loadAnswers() {
        ToggleGroup toggleGroup = new ToggleGroup();
        questionTextField.setText(question.getQuestionText());
        questionTextField.setEditable(false);
        double yValue = questionTextField.getLayoutY();
        for(String answer : answerList) {
            RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(toggleGroup);
            TextArea textArea = new TextArea(answer);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.setPrefHeight(60);
            textArea.setMinWidth(700);
            AnchorPane.setLeftAnchor(textArea, 50.0);
            AnchorPane.setTopAnchor(textArea, yValue);
            AnchorPane.setLeftAnchor(radioButton, 30.0);
            AnchorPane.setTopAnchor(radioButton, yValue);
            anchorPane2.getChildren().addAll(radioButton, textArea);
            scrollPane.setContent(null);
            scrollPane.setContent(anchorPane2);
            yValue += 70;
        }

    }
}
