package com.solovev.quiz_game.util;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class to make sure the last selected dir will always be the same
 */
public class FileChooseSingleton {
    private static FileChooseSingleton instance;
    private static final FileChooser chooser = new FileChooser();
    private FileChooseSingleton(){
        File root = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(root);
        chooser
                .getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter("JSON", "*.json"),
                        new FileChooser.ExtensionFilter("CSV", "*.csv")
                );
    }
    public static synchronized FileChooseSingleton getInstance(){
        if(instance == null){
            instance = new FileChooseSingleton();
        }
        return instance;
    }
    public File showOpenDialog(){
        File chosenFile = chooser.showOpenDialog(null);
        if(chosenFile != null){
            chooser.setInitialDirectory(chosenFile.getParentFile());
        }
        return chosenFile;
    }
    public File showSaveDialog(){
        File chosenFile = chooser.showSaveDialog(null);
        if(chosenFile != null){
            chooser.setInitialDirectory(chosenFile);
        }
        return chosenFile;
    }

}
