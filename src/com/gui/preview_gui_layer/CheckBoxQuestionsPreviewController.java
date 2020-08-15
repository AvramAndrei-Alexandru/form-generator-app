package com.gui.preview_gui_layer;

import com.form_classes.CheckBoxQuestions;
import com.form_classes.FormQuestions;
import com.language.LanguageContainer;
import com.language.PreviewQuestionsLanguage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CheckBoxQuestionsPreviewController extends AnchorPane {

    private CheckBoxQuestions question;
    private AnchorPane anchorPane2 = new AnchorPane();
    private List<String> answerList;
    private int maximumNumberOfAnswersAllowed;
    private int currentAnswersSelected;
    private PreviewQuestionsLanguage languagePack;

    @FXML private TextArea questionTextField;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane anchorPane;
    @FXML private Label numberOfAnswersLabel;

    public CheckBoxQuestionsPreviewController(FormQuestions formQuestions) {
        languagePack = LanguageContainer.getInstance().getPreviewQuestionLanguage();
        this.question = (CheckBoxQuestions) formQuestions;
        answerList = question.getAnswerList();
        maximumNumberOfAnswersAllowed = question.getMaximumNumberOfAnswersAllowed();
        currentAnswersSelected = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckBoxQuestionsPreviewFXML.fxml"));
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
        numberOfAnswersLabel.setText(languagePack.getMaximumNumberOFAnswersText(maximumNumberOfAnswersAllowed));
        loadAnswers();
    }

    /**
     * This method is used to load all the answers the question contains.
     */
    private void loadAnswers() {
        questionTextField.setText(question.getQuestionText());
        questionTextField.setEditable(false);
        double yValue = questionTextField.getLayoutY();
        for(String answer : answerList) {
            CheckBox checkBox = new CheckBox();
            TextArea textArea = new TextArea(answer);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefHeight(60);
            textArea.setMinWidth(700);
            AnchorPane.setLeftAnchor(textArea, 50.0);
            AnchorPane.setTopAnchor(textArea, yValue);
            checkBox.setOnMouseClicked(event -> {
                checkBoxActionListener(checkBox);
            });
            AnchorPane.setLeftAnchor(checkBox, 30.0);
            AnchorPane.setTopAnchor(checkBox, yValue);
            anchorPane2.getChildren().addAll(checkBox, textArea);
            scrollPane.setContent(null);
            scrollPane.setContent(anchorPane2);
            yValue += 70;
        }
    }

    private void checkBoxActionListener(CheckBox checkBox) {
        if(checkBox.isSelected()) {
            if(currentAnswersSelected == maximumNumberOfAnswersAllowed) {
                checkBox.setSelected(false);
            }else {
                currentAnswersSelected++;
            }
        }
        else {
            currentAnswersSelected--;
        }
    }
}
