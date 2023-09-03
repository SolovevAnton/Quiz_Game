package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Quiz;

import java.io.IOException;

/**
 * Class to open forms with
 */
public class FormsManager extends WindowManager {
    public static void openGameForm(Quiz quiz) throws IOException {
        WindowManager.openWindow("/com/solovev/quiz_game/UI/gameForm.fxml","Quiz Game",quiz);
    }
    public static void openLoadForm() throws IOException {
        WindowManager.openWindow("/com/solovev/quiz_game/UI/loadingForm.fxml","Quiz Creator",null);
    }
}
