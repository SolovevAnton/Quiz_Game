package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.repositories.AvailableCategoriesRepository;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.Type;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;

public class LoadingForm {
    public ComboBox<Type> comboBoxType;
    public ComboBox<Difficulty> comboBoxDifficulty;
    public ComboBox<Category> comboBoxCategory;
    public TextField textFieldNumberOfQuestions;
    private Collection<Category> availableCategories;
    public void initialize() throws IOException {
        comboBoxType.getItems().setAll(Type.values());
        comboBoxDifficulty.getItems().setAll(Difficulty.values());
        this.availableCategories = new AvailableCategoriesRepository().takeData();
        comboBoxCategory.getItems().setAll(availableCategories);
        //sort sorted in alphabetical order
        comboBoxCategory.getItems().sort(Comparator.comparing(Category::getName));

        //factory to show only names of categories
        Callback<ListView<Category>, ListCell<Category>> nameFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? comboBoxCategory.getPromptText() : category.getName());
            }
        };
        comboBoxCategory.setCellFactory(nameFactory);
        comboBoxCategory.setButtonCell(nameFactory.call(null));

        reset();
    }

    /**
     * Method resets form to default values
     */
    public void reset(){
        comboBoxType.setValue(null);
        comboBoxCategory.setValue(null);
        comboBoxDifficulty.setValue(null);
        textFieldNumberOfQuestions.setText("10");
    }
    public void buttonGenerateQuiz(ActionEvent actionEvent) {
    }

    public void buttonClear(ActionEvent actionEvent) {
        reset();
    }
}
