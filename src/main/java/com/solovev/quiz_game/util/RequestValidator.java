package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.repositories.AvailableQuestionsRepository;

import java.io.IOException;
import java.util.Map;

/**
 * Class used to validate the request before forming url to quiz API. Mainly focuses on the questions insufficiencies
 * NOTE: The boolean/multiple question insufficiencies is not checked! Only topic and difficulty ones
 */
public class RequestValidator {

    //all possible error massages
    public static final String OVERALL_NUMBER = "Question number can be from 1 to %d";
    public static final String CATEGORY_MESSAGE = "In category %s max is %d questions";
    public static final String DIFFICULTY_MESSAGE = "In category %s and difficulty %s max is %d questions\n Category has following available questions:\n%s";
    private String errorMessage;
    private final int overallMaxNumberOfQuestions;
    private final Request request;
    private final int numberOfQuestions;
    private Map<Difficulty, Integer> countQuestionsByDifficulty;
    private int allAvailableQuestions;


    public RequestValidator(int overallMaxNumberOfQuestions, Request request) {
        this.overallMaxNumberOfQuestions = overallMaxNumberOfQuestions;
        this.request = request;
        this.numberOfQuestions = request.getNumberOfQuestions();
    }

    /**
     * Checks if provided request is valid and generates error message if it is not
     *
     * @return true if valid false otherwise
     */
    public boolean isValid() throws IOException {

        if (numberOfQuestions < 1 || numberOfQuestions > overallMaxNumberOfQuestions) {
            errorMessage = String.format(OVERALL_NUMBER, overallMaxNumberOfQuestions);
            return false;
        }
        return true;
    }

    /**
     * Checks if number of questions are sufficient for chosen difficulty;
     * Assumed difficulty is not null
     *
     * @return
     */
    private boolean checkDifficulty() {
        return true;
    }

    /**
     * Checks if overall questions in category is sufficient;
     * The method assumes category is not null; so it must be checked outside
     *
     * @return true if there are overall sufficient questions in category
     */
    private boolean categoryCheck() throws IOException {
        //checks if all questions
        return countQuestionsByDifficulty
                .values()
                .stream()
                .reduce(Integer::sum)
                .orElse(0) > request.getNumberOfQuestions();
    }

    /**
     * Loads map of questions with difficulties
     */
    private void loadMap() throws IOException {
        countQuestionsByDifficulty = new AvailableQuestionsRepository(request.getCategory().getId())
                .takeData()
                .countQuestionsByDifficulty();
    }

    /**
     * Builds a sting from map;
     * Method must be invoked after build map
     *
     * @return info about available questions
     */
    private String availableQuestions() {
        StringBuilder sb = new StringBuilder("Total count: " + allAvailableQuestions);
        for (Difficulty d : Difficulty.values()) {
            sb.append("\nDifficulty: ")
                    .append(d.name().toLowerCase())
                    .append(" available questions: ")
                    .append(countQuestionsByDifficulty.get(d));
        }

        return sb.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
