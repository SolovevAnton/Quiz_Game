package com.solovev.quiz_game.util;

import com.solovev.quiz_game.App;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Class to make sure the last selected dir will always be the same;
 * Its singleton class
 * Moreover it saves and opens Files with Quiz
 */
public class QuizFileChooserSaverAndLoader {
    private static QuizFileChooserSaverAndLoader instance;
    private static final FileChooser chooser = new FileChooser();
    private static final Preferences preferences = Preferences.userNodeForPackage(App.class);

    private QuizFileChooserSaverAndLoader() {
        //first creation directory set
        File root = preferences.get("Last put", null) == null
                ? new File(System.getProperty("user.dir"))
                : new File(preferences.get("Last put", null));

        chooser.setInitialDirectory(root);
        chooser
                .getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter("JSON", "*.json")
                );
    }

    public static synchronized QuizFileChooserSaverAndLoader getInstance() {
        if (instance == null) {
            instance = new QuizFileChooserSaverAndLoader();
        }
        return instance;
    }

    /**
     * Saves quiz based on chosen filePath
     *
     * @param quiz to save
     * @return true if quiz was saved, false otherwise
     */
    public boolean saveQuiz(Quiz quiz) {
        File chosenFile = chooser.showSaveDialog(null);
        try {
            if (chosenFile != null) {
                setDirectory(chosenFile);
                QuizRepository repo = new QuizRepository(quiz);
                repo.save(chosenFile, true);
            }
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
        return chosenFile != null;
    }

    /**
     * Opens quiz based on chosen filePath
     *
     * @return opened quiz or null if file was not chosen
     */
    public Quiz openQuiz() {
        Quiz result = null;
        try {
            File chosenFile = chooser.showOpenDialog(null);
            if (chosenFile != null) {
                setDirectory(chosenFile);
                result = new QuizRepository(chosenFile, true).takeData();
            }
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("IOException Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
        return result;
    }

    private void setDirectory(File chosenFile) {
        File directoryToState = chosenFile.getParentFile();
        chooser.setInitialDirectory(directoryToState);
        preferences.put("Last put", directoryToState.toString());
    }

}
