package com.solovev.quiz_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String mainForm = "/com/solovev/quiz_game/UI/main.fxml";
        String loaderForm = "/com/solovev/quiz_game/UI/loadingForm.fxml";
        String gameForm = "/com/solovev/quiz_game/UI/gameForm.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(mainForm));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}