package com.gui.questions_gui_layer;

import com.form_classes.CheckBoxQuestions;
import com.form_classes.FormQuestions;
import com.language.LanguageContainer;
import com.language.RadioButtonCheckBoxQuestionsLanguage;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import com.model.UniqueIDGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckBoxQuestionsController extends AnchorPane {

    private AnchorPane anchorPane2 = new AnchorPane();
    private ArrayList<AnswerContainer> answerList;
    private RadioButtonCheckBoxQuestionsLanguage languagePack;

    public CheckBoxQuestionsController() {
        languagePack = LanguageContainer.getInstance().getRadioButtonCheckBoxQuestionsLanguage();
        answerList = new ArrayList<>();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckBoxQuestionsFXML.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AnswerContainer {

        public AnswerContainer(CheckBox checkBox, TextArea textArea, Button button) {
            this.checkBox = checkBox;
            this.textArea = textArea;
            this.button = button;
        }

        CheckBox checkBox;
        TextArea textArea;
        Button button;
    }

    @FXML private Button addAnswerBtn;
    @FXML private AnchorPane anchorPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Button saveButton;
    @FXML private TextArea questionText;
    @FXML private ComboBox<Integer> maximumNumberOfAnswersComboBox;


    @FXML
    private void initialize() {
        setGUILanguage();
        scrollPane.setMinWidth(1150);
        scrollPane.setMinHeight(500);
        setButtonsActionListeners();
        setComboBoxActionListeners();
    }

    /**
     * Sets all the button action listeners.
     */
    private void setButtonsActionListeners() {
        addAnswerBtn.setOnMouseClicked(event -> {
            addAnswerButtonActionListener();
        });
        saveButton.setOnMouseClicked(event -> {
            saveButtonActionListener();
        });
    }

    /**
     * This method is called when the add answer button is pressed.
     */
    private void addAnswerButtonActionListener() {
        if(answerList.size() == 0) {
            addElementsToAnswerContainer(70.0, 30.0, 67.0, 50.0, 67.0);
        }
        else {
            AnswerContainer lastQuestion = answerList.get(answerList.size() - 1);
            double checkBoxX = lastQuestion.checkBox.getLayoutX();
            double checkBoxY = lastQuestion.checkBox.getLayoutY();
            addElementsToAnswerContainer(checkBoxY + 70, checkBoxX, checkBoxY + 70, checkBoxX + 20,checkBoxY + 70
            );
        }
    }

    private void setComboBoxActionListeners() {
        maximumNumberOfAnswersComboBox.setOnShowing(event -> {
            maximumNumberOfAnswersComboBoxActionListener();
        });
    }

    /**
     * This method is used to create an answer container and to add it to the answer list.
     */
    private void addElementsToAnswerContainer(double checkBoxX, double checkBoxY, double textFieldX, double textFieldY, double deleteButtonX) {
        CheckBox checkBox = new CheckBox("");
        AnchorPane.setTopAnchor(checkBox, checkBoxX);
        AnchorPane.setLeftAnchor(checkBox, checkBoxY);
        TextArea textArea = new TextArea();
        textArea.setPromptText(languagePack.getAnswerTextFieldPromptText());
        textArea.setMinWidth(850);
        textArea.setPrefHeight(60);
        textArea.setWrapText(true);
        AnchorPane.setTopAnchor(textArea, textFieldX);
        AnchorPane.setLeftAnchor(textArea, textFieldY);
        Button button = new Button(languagePack.getDeleteAnswerButtonText());
        button.setOnMouseClicked(event1 -> {
            deleteButtonActionListener(button.getLayoutY());
        });
        AnchorPane.setTopAnchor(button, deleteButtonX);
        AnchorPane.setLeftAnchor(button, 900.0);
        anchorPane2.getChildren().addAll(checkBox, textArea, button);
        scrollPane.setContent(null);
        scrollPane.setContent(anchorPane2);
        AnswerContainer answerContainer = new AnswerContainer(checkBox, textArea, button);
        answerList.add(answerContainer);
    }

    /**
     * This method is called when the delete answer button is pressed.
     */
    private void deleteButtonActionListener(double positionY) {
        if(answerList.get(answerList.size() - 1).button.getLayoutY() == positionY) {
            anchorPane2.getChildren().removeAll(answerList.get(answerList.size() - 1).checkBox, answerList.get(answerList.size() - 1).textArea
                    , answerList.get(answerList.size() - 1).button);
            answerList.remove(answerList.get(answerList.size() - 1));
        }
        else {
            for(int i = 0; i < answerList.size(); i++) {
                if(answerList.get(i).button.getLayoutY() == positionY) {
                    for(int j = i; j < answerList.size() - 1; j++) {
                        answerList.get(j).textArea.setText(answerList.get(j + 1).textArea.getText());
                        answerList.get(j).checkBox.setSelected(answerList.get(j + 1).checkBox.isSelected());
                    }
                    anchorPane2.getChildren().removeAll(answerList.get(answerList.size() - 1).checkBox, answerList.get(answerList.size() - 1).textArea,
                            answerList.get(answerList.size() - 1).button);
                    answerList.remove(answerList.size() - 1);
                    break;
                }
            }
        }
    }

    /**
     * This method is called when the save button is pressed.
     */
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
        else if(maximumNumberOfAnswersComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlertMessage(text.get(6));
            isOkToSave = false;
        }
        else if(answerList.size() == 0) {
            showAlertMessage(text.get(2));
            isOkToSave = false;
        }else {
            for(AnswerContainer currentAnswer : answerList) {
                if(currentAnswer.textArea.getText().equals("")) {
                    isOkToSave = false;
                    showAlertMessage(text.get(4));
                    break;
                }
            }
            if(isOkToSave) {
                Set<String> detectDuplicatesSet = new HashSet<>();
                for(AnswerContainer currentAnswer : answerList) {
                    detectDuplicatesSet.add(currentAnswer.textArea.getText());
                }
                if(detectDuplicatesSet.size() != answerList.size()) {
                    isOkToSave = false;
                    showAlertMessage(text.get(5));
                }
            }
        }
        if(isOkToSave) {
            UniqueIDGenerator uniqueIDGenerator = UniqueIDGenerator.getUniqueIDGenerator();
            CheckBoxQuestions question = new CheckBoxQuestions(uniqueIDGenerator.generateUniqueID(), answerList.size(), questionText.getText(), maximumNumberOfAnswersComboBox.getSelectionModel().getSelectedItem());
            for(AnswerContainer currentAnswer : answerList) {
                question.addAnswer(currentAnswer.textArea.getText());
            }
            FormContainer.getInstance().getCurrentWorkingForm().addQuestionToForm(question);
            CommunicationChannel.getInstance().notifyDesignLayoutToClear();
        }
    }



    private void maximumNumberOfAnswersComboBoxActionListener() {
        if(answerList.size() == 0) {
            maximumNumberOfAnswersComboBox.setItems(null);
        }else {
            int maxNumberOfAnswers = answerList.size();
            ObservableList<Integer> comboBoxList = FXCollections.observableArrayList();
            for(int i = 1; i <= maxNumberOfAnswers; i++) {
                comboBoxList.add(i);
            }
            maximumNumberOfAnswersComboBox.setItems(comboBoxList);
        }
    }

    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageContainer.getInstance().getWarning());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private boolean isQuestionDistinct() {
        for(FormQuestions question : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            if(question.getQuestionText().equals(questionText.getText())) {
                return false;
            }
        }
        return true;
    }

    private void setGUILanguage() {
        addAnswerBtn.setText(languagePack.getAddAnswerButtonText());
        saveButton.setText(languagePack.getSaveItemButtonText());
        questionText.setPromptText(languagePack.getQuestionTextPrompt());
        maximumNumberOfAnswersComboBox.setPromptText(languagePack.getMaximumNumberOfAnswersText());
    }

}
