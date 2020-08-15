package com.gui.questions_gui_layer;

import com.form_classes.FormQuestions;
import com.form_classes.FreeTextQuestions;
import com.form_classes.ScaleQuestions;
import com.language.LanguageContainer;
import com.language.ScaleQuestionsLanguage;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import com.model.UniqueIDGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class ScaleQuestionsController extends AnchorPane {

    private ScaleQuestionsLanguage languagePack;

    @FXML private Button saveButton;
    @FXML private TextArea questionText;

    @FXML public void initialize() {
        setGUILanguage();
        setButtonsActionListeners();
    }

    public ScaleQuestionsController() {
        languagePack = LanguageContainer.getInstance().getScaleQuestionsLanguagePack();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScaleQuestionsFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonsActionListeners() {
        saveButton.setOnMouseClicked(event -> {
            saveButtonActionListener();
        });
    }


    private void setGUILanguage() {
        saveButton.setText(languagePack.getSaveItemButtonText());
        questionText.setPromptText(languagePack.getQuestionTextPrompt());
    }

    private void saveButtonActionListener() {
        List<String> text = languagePack.getSaveAlertList();
        boolean isOkToSave = true;
        if(questionText.getText().equals("")) {
            showAlertMessage(text.get(0));
            isOkToSave = false;
        }
        else if(!isQuestionDistinct()) {
            showAlertMessage(text.get(1));
            isOkToSave = false;
        }
        if(isOkToSave) {
            UniqueIDGenerator uniqueIDGenerator = UniqueIDGenerator.getUniqueIDGenerator();
            ScaleQuestions scaleQuestions = new ScaleQuestions(uniqueIDGenerator.generateUniqueID(), questionText.getText());
            FormContainer.getInstance().getCurrentWorkingForm().addQuestionToForm(scaleQuestions);
            CommunicationChannel.getInstance().notifyDesignLayoutToClear();
        }
    }

    private boolean isQuestionDistinct() {
        for(FormQuestions question : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            if(question.getQuestionText().equals(questionText.getText())) {
                return false;
            }
        }
        return true;
    }

    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageContainer.getInstance().getWarning());
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
