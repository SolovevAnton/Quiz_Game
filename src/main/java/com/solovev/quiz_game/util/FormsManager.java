package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Quiz;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Class to open forms with
 */
public class FormsManager extends WindowManager {
    private interface RunnableWithThrows {
        void run() throws IOException;
    }

    public static void openMainForm() {
        ioExceptionHandler(() -> openWindow("/com/solovev/quiz_game/UI/main.fxml", "Start Game", null));
    }

    public static void openGameForm(Quiz quiz) {
        ioExceptionHandler(() -> openWindow("/com/solovev/quiz_game/UI/gameForm.fxml", "Quiz Game", quiz));
    }

    public static void openLoadForm() {
        ioExceptionHandler(() -> openWindow("/com/solovev/quiz_game/UI/loadingForm.fxml", "Quiz Creator", null));
    }

    public static Boolean openDialogForm(Quiz quiz){
        boolean success;
        try {
            success = openWindowAndWaitWithRetrieveData("/com/solovev/quiz_game/UI/dialogForm.fxml", "Quiz successfully created", quiz);
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
            return success;
    }

    /**
     * Method to handle exceptions, so they can be seen in app
     *
     * @param method to run , should throw IO exception
     */
    private static void ioExceptionHandler(RunnableWithThrows method) {
        try {
            method.run();
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

}
