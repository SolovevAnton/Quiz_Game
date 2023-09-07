package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Class to make sure the last selected dir will always be the same;
 * Its singleton class
 * Moreover it saves and opens Files with Quiz
 */
public class QuizFileChooserSaverAndLoader {
    private static QuizFileChooserSaverAndLoader instance;
    private static final FileChooser chooser = new FileChooser();
    private QuizFileChooserSaverAndLoader(){
        File root = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(root);
        chooser
                .getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter("JSON", "*.json"),
                        new FileChooser.ExtensionFilter("CSV", "*.csv")
                );
    }
    public static synchronized QuizFileChooserSaverAndLoader getInstance(){
        if(instance == null){
            instance = new QuizFileChooserSaverAndLoader();
        }
        return instance;
    }

    /**
     * Saves quiz based on chosen filePath
     * @param quiz to save
     * @return true if quiz was saved, false otherwise
     * @throws IOException is IO exception occurs
     */
    public boolean saveQuiz(Quiz quiz) throws IOException {
        File chosenFile = chooser.showSaveDialog(null);
        if(chosenFile != null){
            setDirectory(chosenFile);
            QuizRepository repo = new QuizRepository(quiz);
            repo.save(chosenFile,true);
        }
        return chosenFile != null;
    }
    /**
     * Opens quiz based on chosen filePath
     * @return opened quiz or null if file was not chosen
     * @throws IOException is IO exception occurs
     */
    public Quiz openQuiz() throws IOException {
        Quiz result = null;
        File chosenFile = chooser.showOpenDialog(null);
        if(chosenFile != null){
            setDirectory(chosenFile);
            result = new QuizRepository(chosenFile,true).takeData();
        }
        return result;
    }
    private void setDirectory(File chosenFile){
        chooser.setInitialDirectory(chosenFile.getParentFile());
            }


}
