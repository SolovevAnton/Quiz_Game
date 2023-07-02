package com.solovev.quiz_game.util;

import com.solovev.quiz_game.controllers.LoadingForm;
import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.repositories.AvailableCategoriesRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        assertNull(errorMessage(defaultRequest));


        assertTrue(errorMessage(inValidMaxQuestion)
                .matches(RequestValidator.OVERALL_NUMBER.replaceAll("%.","\\\\w*")));

        assertTrue(errorMessage(inValidMinQuestion)
                .matches(RequestValidator.OVERALL_NUMBER.replaceAll("%.","\\\\w*")));

        assertTrue(errorMessage(inValidCategory).matches(RequestValidator.CATEGORY_MESSAGE.replaceAll("%.","\\\\w*")));
        assertTrue(errorMessage(inValidDifficulty).matches(RequestValidator.DIFFICULTY_MESSAGE.replaceAll("%.","\\\\w*")));
    }

    @RepeatedTest(10)
    public void randomTests(){
        //todo add tests
        System.out.println(randomRequest());
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
    private Request validOnlyCategory = new Request(defaultNumberOfQuestions,new Category(9) );
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
        int difSeed = rand.nextInt(-1,difficulties.length);
        Difficulty difficulty = difSeed < 0 ? null : difficulties[difSeed];

        return new Request(numberOfQuestions, category,difficulty);
    }
    private Boolean check(Request request) throws IOException {
        return new RequestValidator(maxNumberOfQuestions, request).isValid();
    }

    /**
     * Creates and return message for validator of the reqest
     * @return error msg
     */
    private String errorMessage(Request request) throws IOException {
        RequestValidator validator = new RequestValidator(maxNumberOfQuestions,request);
        validator.isValid();
        return validator.getErrorMessage();
    }

}