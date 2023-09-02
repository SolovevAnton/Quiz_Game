package com.solovev.quiz_game.util.validators;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.validators.Validator;

/**
 * Validates quiz with response code
 */
public class QuizValidator implements Validator {
    private String errorMessage;
    private Quiz quiz;

    public QuizValidator(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public boolean isValid() {
        Quiz.ResponseCode code = quiz.getResponseCode();
        errorMessage = "Response code: " + code.name() + "\n" + code.getMessage();
        return code == Quiz.ResponseCode.SUCCESS;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
