package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.QuizFileChooserSaverAndLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class DialogForm implements ControllerData<Quiz>, ControllerRetrieveData<Boolean> {
    private boolean closeWindow;
    private Quiz quizToSave;

    public void saveButton(ActionEvent actionEvent) throws IOException {
        if (QuizFileChooserSaverAndLoader.getInstance().saveQuiz(quizToSave)) { //if quiz saved successfully does not closes window, to make another quiz
            FormsManager.closeWindow(actionEvent);
        }
    }

    public void startButton(ActionEvent actionEvent) throws IOException {
        closeWindow = true;
        FormsManager.openGameForm(quizToSave);
        FormsManager.closeWindow(actionEvent);
    }

    public void saveAndStartButton(ActionEvent actionEvent) throws IOException {
        closeWindow = QuizFileChooserSaverAndLoader.getInstance().saveQuiz(quizToSave);
        if (closeWindow) {
            startButton(actionEvent);
        }
    }

    @Override
    public void initData(Quiz quiz) {
        quizToSave = quiz;
    }

    @Override
    public Boolean retrieveData() {
        return closeWindow;
    }
}
