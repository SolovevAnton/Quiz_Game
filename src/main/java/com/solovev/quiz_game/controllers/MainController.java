package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.QuizFileChooserSaverAndLoader;
import com.solovev.quiz_game.util.FormsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.IOException;

public class MainController {
    @FXML
    public CheckBox checkBoxCorrectAnswers;
    @FXML
    public void buttonLoadFromInternet(ActionEvent actionEvent) throws IOException {
        FormsManager.openLoadForm();
        FormsManager.closeWindow(actionEvent);
    }
    @FXML
    public void buttonLoadFromFile(ActionEvent actionEvent) throws IOException {
        QuizFileChooserSaverAndLoader chooseSingleton = QuizFileChooserSaverAndLoader.getInstance();
        Quiz openQuiz = chooseSingleton.openQuiz();
        if(openQuiz != null){
            FormsManager.openGameForm(openQuiz);
            FormsManager.closeWindow(actionEvent);
        }
    }
}
