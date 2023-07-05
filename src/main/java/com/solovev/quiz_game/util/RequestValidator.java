package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.repositories.AvailableQuestionsRepository;

import java.io.IOException;
import java.util.Map;

/**
 * Class used to validate the request before forming url to quiz API. Mainly focuses on the questions insufficiencies
 * NOTE: The boolean/multiple question insufficiencies is not checked! Only topic and difficulty ones;
 */
public class RequestValidator implements Validator {

    //all possible error massages
    public static final String WRONG_FORMAT_MESSAGE = "Number of questions must be an integer";
    public static final String OVERALL_NUMBER = "Question number can be from 1 to %d";
    public static final String DIFFICULTY_MESSAGE = """
            Could not return results. The Database doesn't have enough questions for your query.
            You requested %d questions in category %s and %s difficulty.
            But there are TOTAL of %d questions in category:
            %s""";
    private String errorMessage;
    private final int overallMaxNumberOfQuestions;
    private final Request request;
    private int numberOfQuestions;
    private Map<Difficulty, Integer> countQuestionsByDifficulty;
    private int allAvailableQuestions;


    public RequestValidator(int overallMaxNumberOfQuestions, Request request) {
        this.overallMaxNumberOfQuestions = overallMaxNumberOfQuestions;
        this.request = request;
    }

    /**
     * Checks if provided request is valid and generates error message if it is not
     *
     * @return true if valid false otherwise
     */
    public boolean isValid() {
        //order of method execution is important
        return checkNumberOfQuestionsIsAnInt()
                && checkNumberOfQuestions()
                && checkCategory();
    }

    /**
     * Checks if number of questions can be parsed as integer
     *
     * @return true if it is and false otherwise
     */
    private boolean checkNumberOfQuestionsIsAnInt() {
        try {
            numberOfQuestions = Integer.parseInt(request.getNumberOfQuestions());
            return true;
        } catch (NumberFormatException e) {
            errorMessage = WRONG_FORMAT_MESSAGE;
            return false;
        }
    }

    /**
     * Checks if number odf questions does not exceed overall number of questions;
     * rewrites error message regardless of result
     *
     * @return true if number of questions does not exceed max allowed false otherwise
     */
    private boolean checkNumberOfQuestions() {
         errorMessage = String.format(OVERALL_NUMBER, overallMaxNumberOfQuestions);
        return numberOfQuestions > 0 && numberOfQuestions <= overallMaxNumberOfQuestions;
    }

    /**
     * Checks if overall questions in category and difficulty is sufficient;
     *
     * @return true if there are overall sufficient questions in category and difficulty is not specified, or category is null, else checks checkDifficulty
     */
    private boolean checkCategory() {
        if (request.getCategory() == null) {
            return true;
        }
        loadMap();
        //creates message to show in case of request is not valid
        createMessage();
        return numberOfQuestions <= allAvailableQuestions && checkDifficulty(); // difficulty check is invoked here, since it only should be invoked if category is not null
    }

    /**
     * Checks if number of questions are sufficient for chosen difficulty;
     * If difficulty is null returns true;
     * Method should only be invoked if category is not null;
     * also fills error message if false
     *
     * @return true if there is sufficient questions for this difficulty and category
     */
    private boolean checkDifficulty() {
        return request.getDifficulty() == null
                || countQuestionsByDifficulty.get(request.getDifficulty()) >= numberOfQuestions;
    }

    /**
     * Loads map of questions with difficulties, and stores sum of all questions in this category
     */
    private void loadMap() {
        try {
            countQuestionsByDifficulty = new AvailableQuestionsRepository(request.getCategory().getId())
                    .takeData()
                    .countQuestionsByDifficulty();

            allAvailableQuestions = countQuestionsByDifficulty
                    .values()
                    .stream()
                    .reduce(Integer::sum)
                    .orElse(0);
        } catch (IOException e){
            errorMessage = e.getMessage();
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates error message.
     * Should be used only if category has been chosen
     */
    private void createMessage() {
        StringBuilder sb = new StringBuilder();
        for (Difficulty d : Difficulty.values()) {
            sb.append(d.name())
                    .append(" questions: ")
                    .append(countQuestionsByDifficulty.get(d))
                    .append("\n");
        }
        errorMessage = String.format(DIFFICULTY_MESSAGE,
                numberOfQuestions,
                request.getCategory().getName(),
                request.getDifficulty() == null ? "any" : request.getDifficulty().name(),
                allAvailableQuestions,
                sb
        );
    }

    /**
     * Gets error message. Note! message might not be null even if request is valid
     *
     * @return generated error message. Can be null
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
