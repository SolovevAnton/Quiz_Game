package com.solovev.quiz_game;

import com.solovev.quiz_game.util.FormsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FormsManager.openMainForm();
    }

    public static void main(String[] args) {
            launch();
    }
}