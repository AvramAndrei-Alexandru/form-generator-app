package com.start;

import com.database_layer.ConnectionFactory;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;

public class MainFXMLLoader extends Application {

    private Dimension screenSize;
    private static final double SCREEN_RATIO = 0.7;

    public MainFXMLLoader() {
        screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void start(Stage primaryStage) {
        loadMainFormDesignerFXML();
    }

    public void loadMainFormDesignerFXML() {
        loadFXML("/com/gui/main_gui_layer/MainFormDesignerFXML.fxml", screenSize.getWidth() * SCREEN_RATIO, screenSize.getHeight() * SCREEN_RATIO, "Form Designer", true);
    }

    public void loadDesignLayoutFXML() {
        loadFXML("/com/gui/design_gui_layer/DesignLayoutFXML.fxml", screenSize.getWidth() * SCREEN_RATIO, screenSize.getHeight() * SCREEN_RATIO, "Layout designer", true);
    }

    public void loadPreviewLayout() {
        loadFXML("/com/gui/preview_gui_layer/PreviewMainLayoutFXML.fxml", screenSize.getWidth() * SCREEN_RATIO, screenSize.getHeight() * SCREEN_RATIO, "Preview form", true);
    }

    public void loadFormLinksLayout() {
        loadFXML("/com/gui/form_link_layer/FormLinkFXML.fxml", screenSize.getWidth() * SCREEN_RATIO, screenSize.getHeight() * SCREEN_RATIO, "Questions order", true);
    }

    public void loadQuestionVisibilityLayout() {
        loadFXML("/com/gui/question_visibility_layer/QuestionVisibilityFXML.fxml", screenSize.getWidth() * SCREEN_RATIO, screenSize.getHeight() * SCREEN_RATIO, "Question Visibility", true);
    }

    private void loadFXML(String fxmlFile, double width, double height, String title, boolean undecorated) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            if(undecorated) {
                stage.setOnCloseRequest(Event::consume);
            }
            stage.centerOnScreen();
            stage.setTitle(title);
            stage.setScene(new Scene(parent, width , height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
