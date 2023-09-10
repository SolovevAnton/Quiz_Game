package com.solovev.quiz_game;

import com.solovev.quiz_game.util.FormsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //todo s: 1. cvs? 2.all exceptions in alerts 3.add functionality with tick 3..rescaling?
        FormsManager.openMainForm();
    }

    public static void main(String[] args) {
        launch();
    }
}