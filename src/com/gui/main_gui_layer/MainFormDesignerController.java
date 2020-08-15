package com.gui.main_gui_layer;

import com.form_classes.Form;
import com.language.MainFormDesignerLanguage;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import com.language.LanguageContainer;
import com.start.MainFXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;


public class MainFormDesignerController {

    private TextField formName = null;
    private Button launchLayout = null;
    private List<Form> formList;
    private CheckBox generateDefaultQuestionsCheckBox = null;
    private MainFormDesignerLanguage languagePack;

    @FXML
    private Button deleteSelectedFormBtn;
    @FXML
    private Button previewSelectedFormBtn;
    @FXML
    private ComboBox savedFormsComboBox;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button createNewFormBtn;
    @FXML private ComboBox<String> selectLanguageComboBox;
    @FXML private Label selectedLanguagesLabel;
    @FXML private Button quitButton;


    public MainFormDesignerController() {
       languagePack = LanguageContainer.getInstance().getMainFormDesignerLanguagePack();
    }

    @FXML
    private void initialize() {
        setGUILanguage();
        initializeLanguageComboBox();
        setButtonsActionListeners();
        setComboBoxActionListener();
        FormContainer.getInstance().loadFormList();
    }

    /**
     * Sets all the button action listeners.
     */
    private void setButtonsActionListeners() {

        previewSelectedFormBtn.setOnMouseClicked(event -> {
            previewSelectedFormButtonActionListener();
        });
        deleteSelectedFormBtn.setOnMouseClicked(event -> {
            deleteSelectedFormButtonActionListener();
        });
        createNewFormBtn.setOnMouseClicked(event -> {
            createNewFormButtonActionListener();
        });
        quitButton.setOnMouseClicked(event -> {
            quitButtonActionListener();
        });
    }

    /**
     * Sets the combobox action listener
     */
    private void setComboBoxActionListener() {
        savedFormsComboBox.setOnShowing(event -> {
            updateFormComboBox();
        });
        selectLanguageComboBox.setOnAction(event -> {
            selectLanguageComboBoxActionListener();
        });
    }

    /**
     * This method is called when the create new form button is pressed.
     */
    private void createNewFormButtonActionListener() {
        formName = new TextField();
        formName.setPrefWidth(200);
        AnchorPane.setTopAnchor(formName, createNewFormBtn.getLayoutY());
        AnchorPane.setLeftAnchor(formName, createNewFormBtn.getLayoutX() + createNewFormBtn.getWidth() + 50);
        launchLayout = new Button();
        launchLayoutButtonActionListener(launchLayout);
        AnchorPane.setTopAnchor(launchLayout, createNewFormBtn.getLayoutY());
        AnchorPane.setLeftAnchor(launchLayout, createNewFormBtn.getLayoutX() + createNewFormBtn.getWidth() + 50 + 200);
        generateDefaultQuestionsCheckBox = new CheckBox();
        AnchorPane.setTopAnchor(generateDefaultQuestionsCheckBox, createNewFormBtn.getLayoutY());
        AnchorPane.setLeftAnchor(generateDefaultQuestionsCheckBox, createNewFormBtn.getLayoutX() + createNewFormBtn.getWidth() + 50 + 200 + 300);
        generateDefaultQuestionsCheckBox.setSelected(true);
        anchorPane.getChildren().addAll(formName, launchLayout, generateDefaultQuestionsCheckBox);
        setGUILanguage();
    }

    /**
     * This method is called when the delete button is pressed.
     */
    private void deleteSelectedFormButtonActionListener() {
        if (savedFormsComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlertMessage(LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getDeleteFormAlertText());
        } else {
            List<String> text = LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getDeleteConfirmationText();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(text.get(0));
            alert.setHeaderText(text.get(1));
            alert.setContentText(text.get(2));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Form form : formList) {
                    if (form.getFormName().equals(savedFormsComboBox.getSelectionModel().getSelectedItem().toString())) {
                        formList.remove(form);
                        updateFormComboBox();
                        savedFormsComboBox.setPromptText(LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getSavedFormComboBoxPromptText());
                        savedFormsComboBox.setValue(null);
                        savedFormsComboBox.show();
                        savedFormsComboBox.hide();
                        if (savedFormsComboBox.getItems().size() == 1) {
                            savedFormsComboBox.setItems(null);
                            savedFormsComboBox.show();
                            savedFormsComboBox.hide();
                        }
                        FormContainer.getInstance().deleteFormFromDatabase(form);
                        break;
                    }
                }
            }
        }

    }

    /**
     * This method is called when the preview selected form button is pressed.
     */
    private void previewSelectedFormButtonActionListener() {
        if(CommunicationChannel.getInstance().getPreviewMainLayoutController() == null) {
            if (savedFormsComboBox.getSelectionModel().getSelectedItem() != null) {
                Form foundForm = findFormByName(savedFormsComboBox.getSelectionModel().getSelectedItem().toString());
                if (foundForm != null) {
                    FormContainer.getInstance().setSelectedFormForPreview(foundForm);
                    MainFXMLLoader mainFXMLLoader = new MainFXMLLoader();
                    mainFXMLLoader.loadPreviewLayout();
                }
            } else {
                showAlertMessage(LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getPreviewAlertText());
            }
        }else {
            System.out.println("Nu se poate deschide fereastra noua");
        }
    }

    /**
     * Used to find a form with a given name inside the form list.
     */
    private Form findFormByName(String formName) {
        List<Form> allForms = FormContainer.getInstance().getFormList();
        for (Form form : allForms) {
            if (form.getFormName().equals(formName))
                return form;
        }
        return null;
    }


    /**
     * Used to update the items inside the form combobox.
     */
    public void updateFormComboBox() {
        formList = FormContainer.getInstance().getFormList();
        if (formList != null && formList.size() != 0) {
            ObservableList<String> contents = FXCollections.observableArrayList();
            for (Form form : formList) {
                contents.add(form.getFormName());
            }
            if (savedFormsComboBox != null) {
                savedFormsComboBox.setItems(contents);
            }
        }
    }

    /**
     * This method is called when the launch layout designer button is pressed.
     */
    private void launchLayoutButtonActionListener(Button button) {
        button.setOnAction(event -> {
            if(FormContainer.getInstance().getCurrentWorkingForm() == null) {
                if (formName != null && !formName.getText().equals("")) {
                    if (findFormByName(formName.getText()) != null) {
                        showAlertMessage(LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getAnotherFormNameExistsAlertText(formName.getText()));
                    } else {
                        Form newForm = new Form(formName.getText());
                        newForm.setContainsDefaultQuestions(generateDefaultQuestionsCheckBox.isSelected());
                        FormContainer.getInstance().setCurrentWorkingForm(newForm);
                        MainFXMLLoader mainFXMLLoader = new MainFXMLLoader();
                        mainFXMLLoader.loadDesignLayoutFXML();
                        generateDefaultQuestionsCheckBox.setVisible(false);
                        launchLayout.setVisible(false);
                        formName.setVisible(false);
                        generateDefaultQuestionsCheckBox = null;
                        launchLayout = null;
                        formName = null;
                    }
                }
            }
            else {
                showAlertMessage(languagePack.getAnotherFormIsOpenAlertText());
            }
        });
    }


    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageContainer.getInstance().getWarning());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void selectLanguageComboBoxActionListener() {
        LanguageContainer.getInstance().setSelectedLanguageIndex(selectLanguageComboBox.getSelectionModel().getSelectedIndex());
        setGUILanguage();
    }

    private void initializeLanguageComboBox() {
        List<String> languages = LanguageContainer.getInstance().getLanguageList();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(languages);
        selectLanguageComboBox.setItems(list);
        selectLanguageComboBox.setValue(list.get(LanguageContainer.getInstance().getSelectedLanguageIndex()));
    }

    private void setGUILanguage() {
        languagePack = LanguageContainer.getInstance().getMainFormDesignerLanguagePack();
        selectedLanguagesLabel.setText(LanguageContainer.getInstance().getSelectedLanguageLabelText());
        deleteSelectedFormBtn.setText(languagePack.getDeleteButtonText());
        previewSelectedFormBtn.setText(languagePack.getPreviewFormButtonText());
        createNewFormBtn.setText(languagePack.getCreateNewFormButtonText());
        savedFormsComboBox.setPromptText(languagePack.getSavedFormComboBoxPromptText());
        quitButton.setText(languagePack.getQuitButtonText());
        if(launchLayout != null)
            launchLayout.setText(languagePack.getSaveFormButtonText());
        if(generateDefaultQuestionsCheckBox != null)
            generateDefaultQuestionsCheckBox.setText(languagePack.getDefaultQuestionsLabelText());
        if(formName != null)
            formName.setPromptText(languagePack.getFormNameTextFieldPromptText());
    }

    private void quitButtonActionListener() {
        List<String> text = LanguageContainer.getInstance().getMainFormDesignerLanguagePack().getQuitConfirmationText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(text.get(0));
        alert.setHeaderText(text.get(1));
        alert.setContentText(text.get(2));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CommunicationChannel.getInstance().notifyWindowsToClose();
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }
}
