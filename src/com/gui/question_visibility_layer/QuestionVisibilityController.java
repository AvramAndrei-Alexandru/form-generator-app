package com.gui.question_visibility_layer;

import com.form_classes.CheckBoxQuestions;
import com.form_classes.Form;
import com.form_classes.FormQuestions;
import com.form_classes.RadioButtonQuestions;
import com.language.LanguageContainer;
import com.language.QuestionVisibilityLanguage;
import com.model.CommunicationChannel;
import com.model.FormContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.*;


public class QuestionVisibilityController {

    @FXML
    private Button saveButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;

    private AnchorPane anchorPane2 = new AnchorPane();
    private List<AnswerContainer> conditionalAnswerList = new ArrayList<>();
    private QuestionVisibilityLanguage languagePack;

    public QuestionVisibilityController() {
        languagePack = LanguageContainer.getInstance().getQuestionVisibilityLanguagePack();
        CommunicationChannel.getInstance().setQuestionVisibilityController(this);
    }

    @FXML
    public void initialize() {
        saveButton.setText(languagePack.getSaveFormButtonText());
        setButtonsActionListeners();
        generateQuestionsLayout();

    }

    private void setButtonsActionListeners() {
        saveButton.setOnMouseClicked(event -> {
            saveButtonActionListener();
        });
    }

    private class AnswerContainer {
        int questionID;
        List<String> answerList = new ArrayList<>();
        boolean noCondition = true;
        TextField questionIDTextField;
    }

    private void saveButtonActionListener() {
        List<String> text = languagePack.getSaveConfirmationAlert();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(text.get(0));
        alert.setHeaderText(text.get(1));
        alert.setContentText(text.get(2));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            setConditionsMap();
            FormContainer.getInstance().addFormToList(FormContainer.getInstance().getCurrentWorkingForm());
            CommunicationChannel.getInstance().setQuestionVisibilityController(null);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    private void generateQuestionsLayout() {
        Form form = FormContainer.getInstance().getCurrentWorkingForm();
        double yValue = 30;
        TextField t1 = new TextField(languagePack.getTextFieldPrompt());
        t1.setEditable(false);
        t1.setPrefWidth(400.0);
        AnchorPane.setLeftAnchor(t1, 0.0);
        AnchorPane.setTopAnchor(t1, 0.0);
        TextField t2 = new TextField("ID:");
        t2.setEditable(false);
        t2.setPrefWidth(40.0);
        AnchorPane.setLeftAnchor(t2, 410.0);
        AnchorPane.setTopAnchor(t2, 0.0);
        anchorPane2.getChildren().addAll(t1, t2);
        for (FormQuestions currentQuestion : form.getFormQuestions()) {
            TextField questionText = new TextField(currentQuestion.getQuestionText());
            questionText.setEditable(false);
            questionText.setPrefWidth(400.0);
            AnchorPane.setLeftAnchor(questionText, 0.0);
            AnchorPane.setTopAnchor(questionText, yValue);
            TextField questionID = new TextField(currentQuestion.getUniqueID() + "");
            questionID.setEditable(false);
            questionID.setPrefWidth(40.0);
            AnchorPane.setLeftAnchor(questionID, 410.0);
            AnchorPane.setTopAnchor(questionID, yValue);
            MenuButton menuButton = new MenuButton(languagePack.getMenuButtonText());
            for (FormQuestions question : form.getFormQuestions()) {
                if (question.getUniqueID() == currentQuestion.getUniqueID()) {
                    break;
                }
                if (!question.getClass().getSimpleName().equals("FreeTextQuestions")) {
                    if(question.getClass().getSimpleName().equals("RadioButtonQuestions")) {
                        RadioButtonQuestions radioButtonQuestions = (RadioButtonQuestions) question;
                        List<String> answerList = radioButtonQuestions.getAnswerList();
                        for(String s : answerList) {
                            CheckMenuItem checkMenuItem = new CheckMenuItem("ID = " + question.getUniqueID() + " " + s);
                            checkMenuItem.setOnAction(event -> {
                                checkMenuItemActionListener(checkMenuItem, menuButton);
                            });
                            menuButton.getItems().add(checkMenuItem);
                        }
                    }
                    else if(question.getClass().getSimpleName().equals("CheckBoxQuestions")) {
                        CheckBoxQuestions checkBoxQuestions = (CheckBoxQuestions) question;
                        List<String> answerList = checkBoxQuestions.getAnswerList();
                        for(String s : answerList) {
                            CheckMenuItem checkMenuItem = new CheckMenuItem("ID = " + question.getUniqueID() + " " + s);
                            checkMenuItem.setOnAction(event -> {
                                checkMenuItemActionListener(checkMenuItem, menuButton);
                            });
                            menuButton.getItems().add(checkMenuItem);
                        }
                    }

                }
            }
            if(menuButton.getItems().size() == 0) {
                menuButton.setVisible(false);
            }
            AnswerContainer answerContainer = new AnswerContainer();
            answerContainer.questionID = currentQuestion.getUniqueID();
            answerContainer.questionIDTextField = questionID;
            conditionalAnswerList.add(answerContainer);
            menuButton.setPrefWidth(300.0);
            AnchorPane.setTopAnchor(menuButton, yValue);
            AnchorPane.setLeftAnchor(menuButton, 460.0);
            yValue += 30;
            anchorPane2.getChildren().addAll(questionText, questionID, menuButton);
        }
        scrollPane.setContent(null);
        scrollPane.setContent(anchorPane2);
    }

    private void checkMenuItemActionListener(CheckMenuItem checkMenuItem, MenuButton menuButton) {
        if (checkMenuItem.isSelected()) {
            for (AnswerContainer answerContainer : conditionalAnswerList) {
                if (answerContainer.questionIDTextField.getLayoutY() == menuButton.getLayoutY()) {
                    if(isRadioButtonQuestion(getIDFormString(checkMenuItem.getText()))) {
                        if(checkForMultipleConditionsInRadioButtonQuestions(checkMenuItem.getText(), answerContainer.answerList)) {
                            answerContainer.answerList.add(checkMenuItem.getText());
                            answerContainer.noCondition = false;
                        }else {
                            showAlertMessage(languagePack.getRadioAlert(getIDFormString(checkMenuItem.getText())));
                            checkMenuItem.setSelected(false);
                        }
                    }
                    else if(isCheckBoxQuestion(getIDFormString(checkMenuItem.getText()))) {
                        if(maxNumberOfAnswersIsNotReached(checkMenuItem.getText(), answerContainer.answerList)) {
                            answerContainer.answerList.add(checkMenuItem.getText());
                            answerContainer.noCondition = false;
                        }else {
                            showAlertMessage(languagePack.getCheckAlert(getIDFormString(checkMenuItem.getText())));
                            checkMenuItem.setSelected(false);
                        }
                    }


                    break;
                }
            }
        } else {
            for (AnswerContainer answerContainer : conditionalAnswerList) {
                if (answerContainer.questionIDTextField.getLayoutY() == menuButton.getLayoutY()) {
                    answerContainer.answerList.remove(checkMenuItem.getText());
                    if(answerContainer.answerList.size() == 0) {
                        answerContainer.noCondition = true;
                    }
                    break;
                }
            }
        }
    }

    private boolean maxNumberOfAnswersIsNotReached(String answerToAdd, List<String> listOfAnswers) {
        int answersAdded = 0;
        int maxNumberOfAnswers = 0;
        int questionID = getIDFormString(answerToAdd);
        for(FormQuestions questions : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            if(questions.getUniqueID() == questionID) {
                CheckBoxQuestions checkBoxQuestions = (CheckBoxQuestions) questions;
                maxNumberOfAnswers = checkBoxQuestions.getMaximumNumberOfAnswersAllowed();
            }
        }
        for(String currentAnswer : listOfAnswers) {
            if(getIDFormString(currentAnswer) == questionID) {
                answersAdded += 1;
            }
        }
        if(answersAdded == maxNumberOfAnswers) {
            return false;
        }
        return true;
    }

    private boolean checkForMultipleConditionsInRadioButtonQuestions(String answerToAdd, List<String> listOfAnswers) {
        for(String currentAnswer : listOfAnswers) {
            if(getIDFormString(currentAnswer) == getIDFormString(answerToAdd)) {
                if(isRadioButtonQuestion(getIDFormString(currentAnswer))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRadioButtonQuestion(int questionID) {
        for(FormQuestions questions : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            if(questions.getUniqueID() == questionID && questions.getClass().getSimpleName().equals("RadioButtonQuestions")) {
                return true;
            }
        }
        return false;
    }

    private boolean isCheckBoxQuestion(int questionID) {
        for(FormQuestions questions : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
            if(questions.getUniqueID() == questionID && questions.getClass().getSimpleName().equals("CheckBoxQuestions")) {
                return true;
            }
        }
        return false;
    }


    private int getIDFormString(String text) {
        String[] split = text.split(" ");
        try {
            return Integer.parseInt(split[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void setConditionsMap() {
        for(AnswerContainer answerContainer : conditionalAnswerList) {
            if(!answerContainer.noCondition) {
                Map<Integer, String> conditionMap = new LinkedHashMap<>();
                for(String s : answerContainer.answerList) {
                    int id = getIDFormString(s);
                    String[] split = s.split(" ");
                    String answer = "";
                    for(int i = 3; i < split.length; i++) {
                        answer += split[i];
                        if(i != split.length -1) {
                            answer += " ";
                        }
                    }
                    conditionMap.put(id, answer);
                }
                for(FormQuestions formQuestions : FormContainer.getInstance().getCurrentWorkingForm().getFormQuestions()) {
                    if(formQuestions.getUniqueID() == answerContainer.questionID) {
                        formQuestions.setConditionsMap(conditionMap);
                        break;
                    }
                }
            }
        }
    }

    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageContainer.getInstance().getWarning());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void forceClose() {
        CommunicationChannel.getInstance().setQuestionVisibilityController(null);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}
