package com.gui.form_link_layer;

import com.form_classes.FormQuestions;
import com.language.FormLinksLanguage;
import com.language.LanguageContainer;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class FormLinkController {

    private class QuestionContainer {

        TextField questionTextTextField;
        Button moveUpButton;
        Button moveDownButton;

        public QuestionContainer(TextField questionTextTextField, Button moveUpButton, Button moveDownButton) {
            this.questionTextTextField = questionTextTextField;
            this.moveUpButton = moveUpButton;
            this.moveDownButton = moveDownButton;
        }
    }

    @FXML private Button quitFormLinksButton;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane anchorPane;

    public FormLinkController() {
        languagePack = LanguageContainer.getInstance().getFormLinksLanguagePack();
        CommunicationChannel.getInstance().setFormLinkController(this);
    }

    private AnchorPane anchorPane2;
    private List<QuestionContainer> questionList;
    private final int incrementForY = 30;
    private FormLinksLanguage languagePack;

    @FXML public void initialize() {
        quitFormLinksButton.setText(languagePack.getQuitButtonText());
        setButtonsActionListeners();
        anchorPane2 = new AnchorPane();
        questionList = new ArrayList<>();
        generateQuestionsLayout();
    }

    private void setButtonsActionListeners() {
       quitFormLinksButton.setOnMouseClicked(event -> {
           quitFormLinksActionListener();
       });
    }


    private void quitFormLinksActionListener() {
        saveQuestionOrder();
        CommunicationChannel.getInstance().notifyDesignLayoutToUpdateFormQuestionsComboBox();
        CommunicationChannel.getInstance().setFormLinkController(null);
        Stage stage = (Stage) quitFormLinksButton.getScene().getWindow();
        stage.close();
    }


    private void generateQuestionsLayout() {
        double yPosition = 0.0;
        for(FormQuestions question : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            TextField questionText = new TextField(question.getQuestionText());
            questionText.setEditable(false);
            questionText.setPrefWidth(400.0);
            AnchorPane.setLeftAnchor(questionText, 0.0);
            AnchorPane.setTopAnchor(questionText, yPosition);
            Button moveUpButton = new Button(languagePack.getQuestionsUpButtonText());
            AnchorPane.setLeftAnchor(moveUpButton, 420.0);
            AnchorPane.setTopAnchor(moveUpButton, yPosition);
            moveUpButton.setOnMouseClicked(event -> {
                moveUpButtonActionListener(moveUpButton.getLayoutY());
            });
            Button moveDownButton = new Button(languagePack.getQuestionDownButtonText());
            AnchorPane.setLeftAnchor(moveDownButton, 600.0);
            AnchorPane.setTopAnchor(moveDownButton, yPosition);
            moveDownButton.setOnMouseClicked(event -> {
                moveDownActionListener(moveDownButton.getLayoutY());
            });
            yPosition += incrementForY;
            anchorPane2.getChildren().addAll(questionText, moveDownButton, moveUpButton);
            scrollPane.setContent(anchorPane2);
            questionList.add(new QuestionContainer(questionText, moveUpButton, moveDownButton));
        }
    }

    private void moveUpButtonActionListener(double positionY) {
        boolean moved = false;
        if(positionY != 0) {
            for(QuestionContainer questionToMoveUp : questionList) {
                if(questionToMoveUp.questionTextTextField.getLayoutY() == positionY) {
                    for(QuestionContainer questionToMoveDown : questionList) {
                        if(questionToMoveDown.questionTextTextField.getLayoutY() == positionY - incrementForY) {
                            String temp = questionToMoveDown.questionTextTextField.getText();
                            questionToMoveDown.questionTextTextField.setText(questionToMoveUp.questionTextTextField.getText());
                            questionToMoveUp.questionTextTextField.setText(temp);
                            moved = true;
                            break;
                        }
                    }
                    if(moved) {
                        break;
                    }
                }
            }
        }
    }

    private void moveDownActionListener(double positionY) {
        boolean moved = false;
        if(positionY != questionList.size() * incrementForY - incrementForY) {
            for(QuestionContainer questionToMoveUp : questionList) {
                if(questionToMoveUp.questionTextTextField.getLayoutY() == positionY) {
                    for(QuestionContainer questionToMoveDown : questionList) {
                        if(questionToMoveDown.questionTextTextField.getLayoutY() == positionY + incrementForY) {
                            String temp = questionToMoveUp.questionTextTextField.getText();
                            questionToMoveUp.questionTextTextField.setText(questionToMoveDown.questionTextTextField.getText());
                            questionToMoveDown.questionTextTextField.setText(temp);
                            moved = true;
                            break;
                        }
                    }
                    if(moved) {
                        break;
                    }
                }
            }
        }
    }

    private void saveQuestionOrder() {
        List<FormQuestions> orderedFormQuestions = new ArrayList<>();
        List<FormQuestions> unorderedFormQuestions = FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions();
        for(QuestionContainer orderedQuestion : questionList) {
           for(FormQuestions unorderedQuestion : unorderedFormQuestions) {
               if(unorderedQuestion.getQuestionText().equals(orderedQuestion.questionTextTextField.getText())) {
                   orderedFormQuestions.add(unorderedQuestion);
                   unorderedFormQuestions.remove(unorderedQuestion);
                   break;
               }
           }
        }
        FormContainer.getInstance().getCurrentWorkingForm().setFormQuestions(orderedFormQuestions);
    }

    public void forceClose() {
        CommunicationChannel.getInstance().setFormLinkController(null);
        Stage stage = (Stage) quitFormLinksButton.getScene().getWindow();
        stage.close();
    }

}
