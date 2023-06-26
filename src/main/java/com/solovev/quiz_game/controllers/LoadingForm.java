package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.util.enums.Difficulty;
import com.solovev.quiz_game.util.enums.Type;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LoadingForm {
    public ComboBox<Type> comboBoxType;
    public ComboBox<Difficulty> comboBoxDifficulty;
    public ComboBox<Category> comboBoxCategory;
    public TextField textFieldNumberOfQuestions;
}
