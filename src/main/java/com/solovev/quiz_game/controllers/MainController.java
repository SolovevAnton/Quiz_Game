package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.QuizFileChooserSaverAndLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    public void buttonLoadFromInternet(ActionEvent actionEvent) {
        FormsManager.openLoadForm();
        FormsManager.closeWindow(actionEvent);
    }

    @FXML
    public void buttonLoadFromFile(ActionEvent actionEvent) {
        Quiz openQuiz = QuizFileChooserSaverAndLoader.getInstance().openQuiz();
        if (openQuiz != null) {
            FormsManager.openGameForm(openQuiz);
            FormsManager.closeWindow(actionEvent);
        }
    }
}
