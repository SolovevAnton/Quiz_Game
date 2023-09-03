package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import com.solovev.quiz_game.repositories.AvailableCategoriesRepository;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.URLCreator;
import com.solovev.quiz_game.util.WindowManager;
import com.solovev.quiz_game.util.validators.QuizValidator;
import com.solovev.quiz_game.util.validators.RequestValidator;
import com.solovev.quiz_game.util.validators.Validator;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class LoadingForm {
    private final String defaultQuestionsNumber = "10";
    private final int maxQuestionsNumber = 50;
    public ComboBox<QuestionType> comboBoxType;
    public ComboBox<Difficulty> comboBoxDifficulty;
    public ComboBox<Category> comboBoxCategory;
    public TextField textFieldNumberOfQuestions;
    private Collection<Category> availableCategories;

    public void initialize() throws IOException {

        comboBoxType.getItems().setAll(QuestionType.values());
        comboBoxDifficulty.getItems().setAll(Difficulty.values());
        this.availableCategories = new AvailableCategoriesRepository().takeData();
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
        textFieldNumberOfQuestions.setText(String.valueOf(defaultQuestionsNumber));
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
                    if(closeWindow){
                        FormsManager.closeWindow(actionEvent);
                    }
                } else {
                    errorMessage = quizValidator.getErrorMessage();
                }
            } else {
                errorMessage = requestValidator.getErrorMessage();
            }
            if(errorMessage != null) {
                FormsManager.showAlertWithoutHeaderText("Quiz not created",errorMessage, Alert.AlertType.WARNING);
            }
        } catch (IOException e) {
            FormsManager.showAlertWithoutHeaderText("Exception Occurred!",e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows dialog with possible options of the quiz
     * @param quizRepo repo to take quiz from
     * @return if the main window should be closed, if true it will be
     */
    private boolean showDialog(QuizRepository quizRepo) throws IOException {
        boolean closeWindow = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quiz successfully created");
        alert.setHeaderText("Please, choose what to do with this quiz");
        alert.setContentText("Choose your option.");

        ButtonType saveQuiz = new ButtonType("Save Quiz");
        ButtonType startQuiz = new ButtonType("Start Quiz");
        ButtonType saveStartQuiz = new ButtonType("Save and Start Quiz");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveQuiz, startQuiz, saveStartQuiz,cancel);
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == saveQuiz){
            System.out.println("Saved");
        } else if (result.get() == startQuiz) {
            FormsManager.openGameForm(quizRepo.takeData());
            closeWindow = true;
        } else if (result.get() == saveStartQuiz) {
        }
        return closeWindow;
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
}
