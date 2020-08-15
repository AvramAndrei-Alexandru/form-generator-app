package com.gui.preview_gui_layer;

import com.form_classes.Form;
import com.form_classes.FormQuestions;
import com.form_classes.FreeTextQuestions;
import com.form_classes.ScaleQuestions;
import com.language.LanguageContainer;
import com.language.PreviewLanguage;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class PreviewMainLayoutController {

    private Form form;
    private int currentIndex = 0;
    private int maxIndex = 0;
    private PreviewLanguage languagePack;

    public PreviewMainLayoutController() {
        languagePack = LanguageContainer.getInstance().getPreviewLanguagePack();
        CommunicationChannel.getInstance().setPreviewMainLayoutController(this);
    }

    @FXML private BorderPane borderPane;
    @FXML private Button previousQuestionBtn;
    @FXML private Button nextQuestionBtn;
    @FXML private Button quitFormPreviewBtn;
    @FXML private TextArea conditionTextArea;
    @FXML
    public void initialize() {
        setGUILanguage();
        conditionTextArea.setEditable(false);
        form = FormContainer.getInstance().getSelectedFormForPreview();
        maxIndex = form.getFormQuestions().size() - 1;
        setButtonsActionListeners();
        showFirstQuestion();

    }

    /**
     * Sets all the button action listeners.
     */
    private void setButtonsActionListeners() {
        quitFormPreviewBtn.setOnMouseClicked(event -> {
            quitButtonActionListener();
        });
        nextQuestionBtn.setOnMouseClicked(event -> {
            nextQuestionButtonActionListener();
        });
        previousQuestionBtn.setOnMouseClicked(event -> {
            previousQuestionButtonActionListener();
        });
    }

    /**
     * This method is called when the quit button is pressed.
     */
    private void quitButtonActionListener() {
        CommunicationChannel.getInstance().setPreviewMainLayoutController(null);
        Stage stage = (Stage) quitFormPreviewBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to show the first question of the form when the layout is first launched.
     */
    private void showFirstQuestion() {
        changeCenterQuestionLayout();
    }

    /**
     * This method is used when the next question button is pressed.
     */
    private void nextQuestionButtonActionListener() {
        if(currentIndex < maxIndex) {
            currentIndex++;
            changeCenterQuestionLayout();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LanguageContainer.getInstance().getWarning());
            alert.setHeaderText(languagePack.getLastQuestionAlert());
            alert.showAndWait();
        }

    }

    /**
     * This method is used when the previous question button is pressed.
     */
    private void previousQuestionButtonActionListener() {
        if(currentIndex != 0) {
            currentIndex--;
            changeCenterQuestionLayout();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LanguageContainer.getInstance().getWarning());
            alert.setHeaderText(languagePack.getFirstQuestionAlert());
            alert.showAndWait();
        }
    }

    private void changeCenterQuestionLayout() {
        updateConditionTextArea();
        if(form.getFormQuestions().get(currentIndex).getClass().getSimpleName().equals("RadioButtonQuestions")) {
            RadioButtonQuestionsPreviewController radioButtonQuestionsPreviewController = new RadioButtonQuestionsPreviewController(form.getFormQuestions().get(currentIndex));
            borderPane.setCenter(radioButtonQuestionsPreviewController);
        }
        else if(form.getFormQuestions().get(currentIndex).getClass().getSimpleName().equals("CheckBoxQuestions")) {
            CheckBoxQuestionsPreviewController checkBoxQuestionsPreviewController = new CheckBoxQuestionsPreviewController(form.getFormQuestions().get(currentIndex));
            borderPane.setCenter(checkBoxQuestionsPreviewController);
        }
        else if(form.getFormQuestions().get(currentIndex).getClass().getSimpleName().equals("FreeTextQuestions")) {
            FreeTextQuestionsPreviewController freeTextQuestionsPreviewController = new FreeTextQuestionsPreviewController(form.getFormQuestions().get(currentIndex));
            borderPane.setCenter(freeTextQuestionsPreviewController);
        }
        else if(form.getFormQuestions().get(currentIndex).getClass().getSimpleName().equals("ScaleQuestions")) {
            ScaleQuestionPreviewController scaleQuestionPreviewController = new ScaleQuestionPreviewController(form.getFormQuestions().get(currentIndex));
            borderPane.setCenter(scaleQuestionPreviewController);
        }
    }

    private void updateConditionTextArea() {
        List<String> text = languagePack.getConditionsList();
        conditionTextArea.setText(text.get(0));
        FormQuestions currentQuestion = form.getFormQuestions().get(currentIndex);
        Map<Integer, String> conditionMap = currentQuestion.getConditionsMap();
        if(conditionMap.size() == 0) {
            conditionTextArea.setText(text.get(1));
        }else {
            for(Integer id : conditionMap.keySet()) {
                for(FormQuestions questions : form.getFormQuestions()) {
                    if(questions.getUniqueID() == id) {
                        conditionTextArea.setText(conditionTextArea.getText() + "\n" + questions.getQuestionText() + " ====> " + conditionMap.get(id));
                        break;
                    }
                }
            }
        }
    }

    private void setGUILanguage() {
        previousQuestionBtn.setText(languagePack.getPreviousQuestionButtonText());
        nextQuestionBtn.setText(languagePack.getNextQuestionButtonText());
        quitFormPreviewBtn.setText(languagePack.getQuitButtonText());
    }

    public void forceClose() {
        CommunicationChannel.getInstance().setPreviewMainLayoutController(null);
        Stage stage = (Stage) quitFormPreviewBtn.getScene().getWindow();
        stage.close();
    }

}
