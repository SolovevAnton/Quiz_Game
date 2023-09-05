package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Quiz;

import java.io.IOException;

/**
 * Class to open forms with
 */
public class FormsManager extends WindowManager {
    public static void openMainForm() throws IOException {
        openWindow("/com/solovev/quiz_game/UI/main.fxml","Start Game",null);
    }
    public static void openGameForm(Quiz quiz) throws IOException {
        openWindow("/com/solovev/quiz_game/UI/gameForm.fxml","Quiz Game",quiz);
    }
    public static void openLoadForm() throws IOException {
        openWindow("/com/solovev/quiz_game/UI/loadingForm.fxml","Quiz Creator",null);
    }
    public static Boolean openDialogForm(Quiz quiz) throws IOException {
        return openWindowAndWaitWithRetrieveData("/com/solovev/quiz_game/UI/dialogForm.fxml","Quiz successfully created",quiz);
    }

}
