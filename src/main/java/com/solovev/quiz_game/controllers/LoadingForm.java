package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import com.solovev.quiz_game.repositories.AvailableCategoriesRepository;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.URLCreator;
import com.solovev.quiz_game.util.validators.QuizValidator;
import com.solovev.quiz_game.util.validators.RequestValidator;
import com.solovev.quiz_game.util.validators.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;

public class LoadingForm {
    private final String defaultQuestionsNumber = "10";
    private final int maxQuestionsNumber = 50;
    public ComboBox<QuestionType> comboBoxType;
    public ComboBox<Difficulty> comboBoxDifficulty;
    public ComboBox<Category> comboBoxCategory;
    public TextField textFieldNumberOfQuestions;

    public void initialize() throws IOException {

        comboBoxType.getItems().setAll(QuestionType.values());
        comboBoxDifficulty.getItems().setAll(Difficulty.values());
        Collection<Category> availableCategories = new AvailableCategoriesRepository().takeData();
        comboBoxCategory.getItems().setAll(availableCategories);
        //sort sorted in alphabetical order
        comboBoxCategory.getItems().sort(Comparator.comparing(Category::getName));

        //factory to show only names of categories
        Callback<ListView<Category>, ListCell<Category>> nameFactoryCategory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? comboBoxCategory.getPromptText() : category.getName());
            }
        };

        //factory to show prompt text if value is empty for buttonCell
        Callback<ListView, ListCell> nameFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Object object, boolean empty) {
                super.updateItem(object, empty);
                setText(empty ? comboBoxCategory.getPromptText() : object.toString());
            }
        };

        comboBoxCategory.setCellFactory(nameFactoryCategory);

        comboBoxCategory.setButtonCell(nameFactoryCategory.call(null));
        comboBoxType.setButtonCell(nameFactory.call(null));
        comboBoxDifficulty.setButtonCell(nameFactory.call(null));

        reset();
    }

    /**
     * Method resets form to default values
     */
    public void reset() {
        comboBoxType.setValue(null);
        comboBoxCategory.setValue(null);
        comboBoxDifficulty.setValue(null);
        textFieldNumberOfQuestions.setText(defaultQuestionsNumber);
    }

    public void buttonGenerateQuiz(ActionEvent actionEvent) {
        try {
            Request request = new Request(textFieldNumberOfQuestions.getText(), comboBoxCategory.getValue(), comboBoxDifficulty.getValue(), comboBoxType.getValue());
            RequestValidator requestValidator = new RequestValidator(maxQuestionsNumber, request);
            String errorMessage = null;

            if (requestValidator.isValid()) {
                URLCreator urlCreator = new URLCreator(request);
                QuizRepository createdQuiz = new QuizRepository(urlCreator.getURL());
                Validator quizValidator = new QuizValidator(createdQuiz.takeData());
                if (quizValidator.isValid()) {
                    //shows dialog if everything is ok
                    boolean closeWindow = showDialog(createdQuiz);
                    if (closeWindow) {
                        FormsManager.closeWindow(actionEvent);
                    }
                } else {
                    errorMessage = quizValidator.getErrorMessage();
                }
            } else {
                errorMessage = requestValidator.getErrorMessage();
            }
            if (errorMessage != null) {
                FormsManager.showAlertWithoutHeaderText("Quiz not created", errorMessage, Alert.AlertType.WARNING);
            }
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("Exception Occurred!", e.toString(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows dialog with possible options of the quiz
     *
     * @param quizRepo repo to take quiz from
     * @return if the main window should be closed, if true it will be
     */
    private boolean showDialog(QuizRepository quizRepo) throws IOException {
        return FormsManager.openDialogForm(quizRepo.takeData());

    }

    public void buttonClear(ActionEvent actionEvent) {
        reset();
    }

    public String getDefaultQuestionsNumber() {
        return defaultQuestionsNumber;
    }

    public int getMaxQuestionsNumber() {
        return maxQuestionsNumber;
    }

    @FXML
    public void openLink(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://opentdb.com"));
    }

    @FXML
    public void buttonToMainForm(ActionEvent actionEvent) {
        FormsManager.openMainForm();
        FormsManager.closeWindow(actionEvent);
    }
}
