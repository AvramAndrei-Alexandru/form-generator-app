package com.gui.design_gui_layer;

import com.form_classes.Form;
import com.form_classes.FormQuestions;
import com.form_classes.ScaleQuestions;
import com.gui.questions_gui_layer.CheckBoxQuestionsController;
import com.gui.questions_gui_layer.FreeTextQuestionsController;
import com.gui.questions_gui_layer.RadioButtonQuestionsController;
import com.gui.questions_gui_layer.ScaleQuestionsController;
import com.language.DesignLayoutLanguage;
import com.language.LanguageContainer;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import com.start.MainFXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class DesignLayoutController {

    @FXML
    private Button saveFormBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button radioButtonQuestionsBtn;
    @FXML
    private ComboBox<String> formQuestionsComboBox;
    @FXML
    private Button deleteSelectedQuestionButton;
    @FXML
    private Button checkBoxQuestionsButton;
    @FXML
    private Button freeTextQuestionsButton;
    @FXML private Button formLinksButton;
    @FXML private Button editQuestionVisibilityButton;
    @FXML private Button scaleQuestionsButton;
    @FXML private Button discardProgressButton;
    private DesignLayoutLanguage languagePack;

    public DesignLayoutController() {
        CommunicationChannel.getInstance().setDesignLayoutController(this);
        languagePack = LanguageContainer.getInstance().getDesignLayoutLanguagePack();
    }

    @FXML
    public void initialize() {
        setGUILanguage();
        setButtonsActionListeners();
    }

    /**
     * Sets all the button listeners.
     */
    private void setButtonsActionListeners() {
        radioButtonQuestionsBtn.setOnMouseClicked(event -> {
            radioButtonQuestionsButtonActionListener();
        });
        saveFormBtn.setOnMouseClicked(event -> {
            saveFormButtonActionListener();
        });
        deleteSelectedQuestionButton.setOnMouseClicked(event -> {
            deleteQuestionButtonActionListener();
        });
        checkBoxQuestionsButton.setOnMouseClicked(event -> {
            checkBoxQuestionsButtonActionListener();
        });
        freeTextQuestionsButton.setOnMouseClicked(event -> {
            freeTextQuestionsButtonActionListener();
        });
        formLinksButton.setOnMouseClicked(event -> {
            formLinksButtonActionListener();
        });
        editQuestionVisibilityButton.setOnMouseClicked(event -> {
            editQuestionVisibilityButtonActionListener();
        });
        scaleQuestionsButton.setOnMouseClicked(event -> {
            scaleQuestionsButtonActionListener();
        });
        discardProgressButton.setOnMouseClicked(event -> {
            discardProgressButtonActionListener();
        });
    }

    /**
     * This method is called when the button for radio button questions is pressed.
     * It sets the center of the borer pane to the radio button questions layout (FXML loaded).
     */
    private void radioButtonQuestionsButtonActionListener() {
        RadioButtonQuestionsController radioButtonQuestionsController = new RadioButtonQuestionsController();
        borderPane.setCenter(radioButtonQuestionsController);
    }

    /**
     * This method is called when the save form button is pressed. If the form is valid and can be saved, the form will be added to the list
     * found inside the form container class.
     */
    private void saveFormButtonActionListener() {
        Form currentForm = FormContainer.getInstance().getCurrentWorkingForm();
        if (currentForm != null && currentForm.getFormQuestions().size() != 0) {
            List<String> text = LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getDeleteConfirmationText();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(text.get(0));
            alert.setHeaderText(text.get(1));
            alert.setContentText(text.get(2));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                FormContainer.getInstance().addFormToList(currentForm);
                Stage stage = (Stage) saveFormBtn.getScene().getWindow();
                stage.close();
            }
        }else {
            showAlertMessage(languagePack.getSaveAlert());
        }
    }

    /**
     * This method is used to clear the center of the border pane.
     */
    public void clearCenterLayout() {
        borderPane.setCenter(null);
        updateFormQuestionsComboBox();
    }

    public void updateFormQuestionsComboBox() {
        if (FormContainer.getInstance().getCurrentWorkingForm() != null && FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions() != null &&
                FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions().size() != 0) {

            ObservableList<String> questionList = FXCollections.observableArrayList();
            for (FormQuestions question : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
                questionList.add(question.getQuestionText());
            }
            formQuestionsComboBox.setItems(questionList);
        } else {
            formQuestionsComboBox.setItems(null);
            formQuestionsComboBox.show();
            formQuestionsComboBox.hide();
        }
    }

    private void deleteQuestionButtonActionListener() {
        Form currentWorkingForm = FormContainer.getInstance().getCurrentWorkingForm();
        if (formQuestionsComboBox.getSelectionModel().getSelectedItem() != null) {
            String questionToDelete = formQuestionsComboBox.getSelectionModel().getSelectedItem();
            for (FormQuestions question : currentWorkingForm.getFormQuestions()) {
                if(question.getQuestionText().equals(questionToDelete)) {
                    FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions().remove(question);
                    break;
                }
            }
        }else {
            showAlertMessage(languagePack.getDeleteQuestionAlert());
        }
        updateFormQuestionsComboBox();
    }

    private void checkBoxQuestionsButtonActionListener() {
        CheckBoxQuestionsController checkBoxQuestionsController = new CheckBoxQuestionsController();
        borderPane.setCenter(checkBoxQuestionsController);
    }

    private void freeTextQuestionsButtonActionListener() {
        FreeTextQuestionsController freeTextQuestionsController = new FreeTextQuestionsController();
        borderPane.setCenter(freeTextQuestionsController);
    }

    private void formLinksButtonActionListener() {
        if(CommunicationChannel.getInstance().getFormLinkController() == null) {
            if(FormContainer.getInstance().getCurrentWorkingForm() == null ||
                    FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions() == null ||
                    FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions().size() == 0) {
                showAlertMessage(languagePack.getFormLinksAlert());
            }else {
                new MainFXMLLoader().loadFormLinksLayout();
            }
        }else {
            System.out.println("Nu se poate deschide o fereastra noua");
        }

    }

    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageContainer.getInstance().getWarning());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void editQuestionVisibilityButtonActionListener() {
        if(CommunicationChannel.getInstance().getQuestionVisibilityController() == null && CommunicationChannel.getInstance().getFormLinkController() == null) {
            Form currentForm = FormContainer.getInstance().getCurrentWorkingForm();
            if (currentForm != null && currentForm.getFormQuestions().size() >= 2) {
                List<String> text = languagePack.getQuestionVisibilityConfirmationList();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(text.get(0));
                alert.setHeaderText(text.get(1));
                alert.setContentText(text.get(2));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    new MainFXMLLoader().loadQuestionVisibilityLayout();
                    CommunicationChannel.getInstance().setDesignLayoutController(null);
                    Stage stage = (Stage) saveFormBtn.getScene().getWindow();
                    stage.close();
                }
            }else {
                showAlertMessage(languagePack.getQuestionVisibilityAlert());
            }
        }else {
            System.out.println("Nu se poate deschide o fereastra noua");
        }
    }

    private void setGUILanguage() {
        radioButtonQuestionsBtn.setText(languagePack.getRadioButtonQuestionsButtonText());
        checkBoxQuestionsButton.setText(languagePack.getCheckBoxQuestionsButtonText());
        freeTextQuestionsButton.setText(languagePack.getFreeTextQuestionsButtonText());
        editQuestionVisibilityButton.setText(languagePack.getEditVisibilityButtonText());
        formLinksButton.setText(languagePack.getFormLinksButtonText());
        saveFormBtn.setText(languagePack.getSaveFormButtonText());
        deleteSelectedQuestionButton.setText(languagePack.getDeleteQuestionButtonText());
        formQuestionsComboBox.setPromptText(languagePack.getFormQuestionsComboBoxText());
        scaleQuestionsButton.setText(languagePack.getScaleQuestionsButtonText());
        discardProgressButton.setText(languagePack.getDiscardProgressButtonText());
    }

    private void scaleQuestionsButtonActionListener() {
        ScaleQuestionsController scaleQuestionsController = new ScaleQuestionsController();
        borderPane.setCenter(scaleQuestionsController);
    }

    private void discardProgressButtonActionListener() {
        List<String> text = languagePack.getDiscardProgressConfirmationText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(text.get(0));
        alert.setHeaderText(text.get(1));
        alert.setContentText(text.get(2));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            FormContainer.getInstance().setCurrentWorkingForm(null);
            CommunicationChannel.getInstance().setDesignLayoutController(null);
            Stage stage = (Stage) saveFormBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void forceClose() {
        CommunicationChannel.getInstance().setDesignLayoutController(null);
        Stage stage = (Stage) saveFormBtn.getScene().getWindow();
        stage.close();
    }
}
