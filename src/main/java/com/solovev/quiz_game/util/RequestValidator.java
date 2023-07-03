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
public class RequestValidator {

    //all possible error massages
    public static final String OVERALL_NUMBER = "Question number can be from 1 to %d";
    public static final String DIFFICULTY_MESSAGE = """
            Exceeding number of available questions.
            You request %d questions in category %s and %s difficulty.
            But there are total of %d questions in category:
            %s""";
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
        return checkCategory();
    }

    /**
     * Checks if overall questions in category and difficulty is sufficient;
     *
     * @return true if there are overall sufficient questions in category and difficulty is not specified, or category is null, else checks checkDifficulty
     */
    private boolean checkCategory() throws IOException {
        if (request.getCategory() == null) {
            return true;
        }

        loadMap();
        //creates message to show in case of request is not valid
        createMessage();

        return numberOfQuestions <= allAvailableQuestions && checkDifficulty();
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
    private void loadMap() throws IOException {
        countQuestionsByDifficulty = new AvailableQuestionsRepository(request.getCategory().getId())
                .takeData()
                .countQuestionsByDifficulty();

        allAvailableQuestions = countQuestionsByDifficulty
                .values()
                .stream()
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Creates error message.
     * Should be used only if category has been chosen
     */
    private void createMessage() {
        StringBuilder sb = new StringBuilder();
        for (Difficulty d : Difficulty.values()) {
            sb.append(d.name().toLowerCase())
                    .append(" questions: ")
                    .append(countQuestionsByDifficulty.get(d))
                    .append("\n");
        }
        errorMessage = String.format(DIFFICULTY_MESSAGE,
                numberOfQuestions,
                request.getCategory().getName(),
                request.getDifficulty() == null ? "any" : request.getDifficulty().name().toLowerCase(),
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
