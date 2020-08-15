package com.model;

import com.database_layer.AnswerDao;
import com.database_layer.ConnectionFactory;
import com.database_layer.FormDao;
import com.form_classes.CheckBoxQuestions;
import com.form_classes.Form;
import com.form_classes.FormQuestions;
import com.form_classes.RadioButtonQuestions;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.Connection;
import java.util.*;

public class FormContainer {

    private FormContainer() {
        formList = new ArrayList<>();
    }
    private static FormContainer instance;
    private List<Form> formList;
    private Form currentWorkingForm;
    private Form selectedFormForPreview;

    public Form getSelectedFormForPreview() {
        return selectedFormForPreview;
    }

    public void setSelectedFormForPreview(Form selectedFormForPreview) {
        this.selectedFormForPreview = selectedFormForPreview;
    }

    public void addFormToList(Form form) {
        if(form.containsDefaultQuestions()) {
            addDefaultQuestions(form);
        }
        UniqueIDGenerator.getUniqueIDGenerator().resetId();
        formList.add(form);
        Collections.sort(formList);
        currentWorkingForm = null;
        addFormToDatabase(form);
    }

    public List<Form> getFormList() {
        return formList;
    }

    public Form getCurrentWorkingForm() {
        return currentWorkingForm;
    }

    public void setCurrentWorkingForm(Form currentWorkingForm) {
        this.currentWorkingForm = currentWorkingForm;
    }

    public static FormContainer getInstance() {
        if(instance == null) {
            instance = new FormContainer();
            return instance;
        } else {
            return instance;
        }
    }


    private void addFormToDatabase(Form form) {
        showDatabaseError();
        try {
                if (FormDao.verifyForm(form.getFormName()) == null) {
                    ByteArrayOutputStream byteArrayOutputStream = serializeForm(form);
                    if (byteArrayOutputStream != null) {
                        byte[] b = byteArrayOutputStream.toByteArray();
                        InputStream file = new ByteArrayInputStream(b);
                        FormDao.insert(form.getFormName(), file);
                        Map<String, Integer> tableHeaders = generateTableHeaders(form.getFormQuestions());
                        String formNAmeWithoutSpace = form.getFormName().replace(" ", "");
                        AnswerDao.createTable(formNAmeWithoutSpace, tableHeaders);
                        file.close();
                        byteArrayOutputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void showDatabaseError() {
        if(!isDatabaseConnected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error");
            alert.setContentText("The database cannot be reached. Please contact the administrator. The application will terminate now.");
            alert.showAndWait();
            System.exit(-1);
        }
    }

    public void loadFormList() {
        showDatabaseError();
        List<InputStream> allTheForms = FormDao.getAllTheForms();
        List<Form> temp = new ArrayList<>();
        if(allTheForms != null) {
            for (InputStream is : allTheForms) {
                Form readFormFromDatabase = null;
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(is));
                    readFormFromDatabase = (Form) objectInputStream.readObject();
                    objectInputStream.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (readFormFromDatabase != null) {
                    temp.add(readFormFromDatabase);
                }
            }
            Collections.sort(temp);
            formList = temp;
        }
    }

    private Map<String, Integer> generateTableHeaders(List<FormQuestions> questionList) {
        Map<String, Integer> tableHeaders = new LinkedHashMap<>();
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getClass().getSimpleName().equals("RadioButtonQuestions") || questionList.get(i).getClass().getSimpleName().equals("FreeTextQuestions")) {
                tableHeaders.put("intr" + (i + 1), questionList.get(i).getLongestAnswerSize());
            } else if (questionList.get(i).getClass().getSimpleName().equals("CheckBoxQuestions")) {
                CheckBoxQuestions checkBoxQuestions = (CheckBoxQuestions) questionList.get(i);
                for (int j = 0; j < checkBoxQuestions.getMaximumNumberOfAnswersAllowed(); j++) {
                    tableHeaders.put("intr" + (i + 1) + "." + (j + 1), questionList.get(i).getLongestAnswerSize());
                }
            }
        }
        return tableHeaders;
    }

    private ByteArrayOutputStream serializeForm(Form form)
    {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(form);
            objectOutputStream.close();
            return byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean isDatabaseConnected() {
        Connection connection = ConnectionFactory.getConnection();
        if(connection != null) {
            ConnectionFactory.close(connection);
            return true;
        }
        return false;
    }

    public void deleteFormFromDatabase(Form form) {
        showDatabaseError();
        String formNAmeWithoutSpace = form.getFormName().replace(" ", "");
        FormDao.deleteFormAnswerTable(formNAmeWithoutSpace);
        FormDao.deleteFormFromTable(form.getFormName());

    }

    private void addDefaultQuestions(Form form) {
        List<FormQuestions> questionsToAdd = new DefaultQuestionGenerator().generateDefaultQuestions();
        for(FormQuestions currentQuestion : questionsToAdd) {
            form.addQuestionToForm(currentQuestion);
        }
    }
}
