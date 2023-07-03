package com.solovev.quiz_game.util;

import com.solovev.quiz_game.controllers.LoadingForm;
import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.repositories.AvailableCategoriesRepository;
import com.solovev.quiz_game.repositories.QuizRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note Type is not tested, since cannot be tested with API request
 */
class RequestValidatorTest {

    @Test
    public void isValidKnownData() throws IOException {
        assertTrue(check(defaultRequest));
        assertTrue(check(maxQuestions));
        assertTrue(check(validOnlyCategory));
        assertTrue(check(validFullMedium));

        assertFalse(check(inValidMinQuestion));
        assertFalse(check(inValidMaxQuestion));
        assertFalse(check(inValidCategory));
        assertFalse(check(inValidDifficulty));
    }

    @Test
    public void messageTest() throws IOException {

        assertTrue(errorMessage(inValidMaxQuestion)
                .matches(RequestValidator.OVERALL_NUMBER.replaceAll("%.", "\\\\w*") + "(?s).*"));

        assertTrue(errorMessage(inValidMinQuestion)
                .matches(RequestValidator.OVERALL_NUMBER.replaceAll("%.", "\\\\w*") + "(?s).*"));

        assertTrue(errorMessage(inValidCategory)
                        .matches(RequestValidator.DIFFICULTY_MESSAGE.replaceAll("%.", "\\\\w*") + "(?s).*"),
                "Regex: " + RequestValidator.DIFFICULTY_MESSAGE.replaceAll("%.", "\\\\w*")
                        + "\nMessage: " + errorMessage(inValidCategory));

        assertTrue(errorMessage(inValidDifficulty)
                        .matches(RequestValidator.DIFFICULTY_MESSAGE.replaceAll("%.", "\\\\w*") + "(?s).*"),
                "Regex: " + RequestValidator.DIFFICULTY_MESSAGE.replaceAll("%.", "\\\\w*")
                        + "\nMessage: " + errorMessage(inValidDifficulty));
    }

    @RepeatedTest(500)
    public void randomTests() throws IOException {
        Request randomReq = randomRequest();
        URL createdUrl = new URLCreator(randomReq).getURL();

        Quiz quizFromRequest = new QuizRepository(createdUrl).takeData();
        boolean isValidRequest = quizFromRequest.getResponseCode() == Quiz.ResponseCode.SUCCESS
                && quizFromRequest.size() == randomReq.getNumberOfQuestions(); // url will response with amount  > 50, but will return only 50 questions

        assertEquals(isValidRequest, new RequestValidator(maxNumberOfQuestions, randomReq).isValid(),
                "Request: " + randomReq + " URL: " + createdUrl);
    }

    private int defaultNumberOfQuestions = new LoadingForm().getDefaultQuestionsNumber();
    private int maxNumberOfQuestions = new LoadingForm().getMaxQuestionsNumber();
    private List<Category> availableCategories;

    {
        try {
            availableCategories = new ArrayList<>(new AvailableCategoriesRepository().takeData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //test cases
    private Request defaultRequest = new Request(defaultNumberOfQuestions);
    private Request maxQuestions = new Request(maxNumberOfQuestions);
    private Request validOnlyCategory = new Request(defaultNumberOfQuestions, new Category(9));
    private Request validFullMedium = new Request(defaultNumberOfQuestions, new Category(9), Difficulty.MEDIUM);
    private Request inValidMinQuestion = new Request(0);
    private Request inValidMaxQuestion = new Request(maxNumberOfQuestions + 1);
    private Request inValidCategory = new Request(35, new Category(13));
    private Request inValidDifficulty = new Request(9, new Category(13), Difficulty.EASY);

    /**
     * Generates random request from available categories
     *
     * @return request
     */
    private Request randomRequest() {
        Random rand = new Random();
        int numberOfQuestions = rand.nextInt(-2, maxNumberOfQuestions + 1) + 1;
        Category category = availableCategories.get(rand.nextInt(availableCategories.size()));

        Difficulty[] difficulties = Difficulty.values();
        int difSeed = rand.nextInt(-1, difficulties.length);
        Difficulty difficulty = difSeed < 0 ? null : difficulties[difSeed];

        return new Request(numberOfQuestions, category, difficulty);
    }

    private Boolean check(Request request) throws IOException {
        return new RequestValidator(maxNumberOfQuestions, request).isValid();
    }

    /**
     * Creates and return message for validator of the reqest
     *
     * @return error msg
     */
    private String errorMessage(Request request) throws IOException {
        RequestValidator validator = new RequestValidator(maxNumberOfQuestions, request);
        validator.isValid();
        return validator.getErrorMessage();
    }

}