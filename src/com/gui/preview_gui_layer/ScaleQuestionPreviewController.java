package com.gui.preview_gui_layer;

import com.form_classes.FormQuestions;
import com.form_classes.FreeTextQuestions;
import com.form_classes.ScaleQuestions;
import com.language.LanguageContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ScaleQuestionPreviewController extends AnchorPane {


    private ScaleQuestions question;

    public ScaleQuestionPreviewController(FormQuestions formQuestions) {
        question = (ScaleQuestions) formQuestions;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScaleQuestionsPreviewFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextArea questionTextField;
    @FXML private AnchorPane anchorPaneRadioButton;
    @FXML private Label answerLabel;

    @FXML public void initialize() {

        setToggleGroup();
        questionTextField.setText(question.getQuestionText());
        questionTextField.setEditable(false);
        answerLabel.setText(LanguageContainer.getInstance().getPreviewQuestionLanguage().getAnswerLabelText());
    }

    private void setToggleGroup() {
        ToggleGroup toggleGroup = new ToggleGroup();
        for(Node node : anchorPaneRadioButton.getChildren()) {
            if(node.getId() != null) {
                RadioButton radioButton = (RadioButton)node;
                radioButton.setToggleGroup(toggleGroup);
            }
        }
    }
}
