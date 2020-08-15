package com.model;

import com.gui.design_gui_layer.DesignLayoutController;
import com.gui.form_link_layer.FormLinkController;
import com.gui.preview_gui_layer.PreviewMainLayoutController;
import com.gui.question_visibility_layer.QuestionVisibilityController;

public class CommunicationChannel {

    private static CommunicationChannel instance;
    private DesignLayoutController designLayoutController;
    private FormLinkController formLinkController;
    private PreviewMainLayoutController previewMainLayoutController;
    private QuestionVisibilityController questionVisibilityController;

    public void setFormLinkController(FormLinkController formLinkController) {
        this.formLinkController = formLinkController;
    }

    public void setPreviewMainLayoutController(PreviewMainLayoutController previewMainLayoutController) {
        this.previewMainLayoutController = previewMainLayoutController;
    }

    public void setQuestionVisibilityController(QuestionVisibilityController questionVisibilityController) {
        this.questionVisibilityController = questionVisibilityController;
    }

    private CommunicationChannel(){
        designLayoutController = null;
        formLinkController = null;
        previewMainLayoutController = null;
        questionVisibilityController = null;
    }

    public void notifyDesignLayoutToClear() {
        if(designLayoutController != null)
            this.designLayoutController.clearCenterLayout();
    }

    public void notifyDesignLayoutToUpdateFormQuestionsComboBox() {
        if(designLayoutController != null)
            this.designLayoutController.updateFormQuestionsComboBox();
    }

    public void setDesignLayoutController(DesignLayoutController designLayoutController) {
        this.designLayoutController = designLayoutController;
    }

    public static CommunicationChannel getInstance() {
        if(instance == null) {
            instance = new CommunicationChannel();
            return instance;
        }else {
            return instance;
        }
    }

    public void notifyWindowsToClose() {
        if(designLayoutController != null)
            designLayoutController.forceClose();
        if(previewMainLayoutController != null)
            previewMainLayoutController.forceClose();
        if(formLinkController != null)
            formLinkController.forceClose();
        if(questionVisibilityController != null)
            questionVisibilityController.forceClose();
    }

    public DesignLayoutController getDesignLayoutController() {
        return designLayoutController;
    }

    public FormLinkController getFormLinkController() {
        return formLinkController;
    }

    public PreviewMainLayoutController getPreviewMainLayoutController() {
        return previewMainLayoutController;
    }

    public QuestionVisibilityController getQuestionVisibilityController() {
        return questionVisibilityController;
    }
}
