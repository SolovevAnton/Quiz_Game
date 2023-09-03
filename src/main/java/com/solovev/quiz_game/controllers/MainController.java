package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.util.FileChooseSingleton;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.File;
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
        FileChooseSingleton chooseSingleton = FileChooseSingleton.getInstance();
        File openFile = chooseSingleton.showOpenDialog();
        if(openFile != null){
            QuizRepository repo = new QuizRepository(openFile,true);
            FormsManager.openGameForm(repo.takeData());
            FormsManager.closeWindow(actionEvent);
        }
    }
}
