package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.QuestionsAvailableInCategory;
import com.solovev.quiz_game.util.URLDataGetter;
import com.solovev.quiz_game.util.URLs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to form request and download number of available questions in certain category
 */
public class AvailableQuestionsRepository implements Repository<QuestionsAvailableInCategory> {
    private QuestionsAvailableInCategory availableQuestions;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AvailableQuestionsRepository() {
    }

    /**
     * Makes api request and stores number of available questions in certain category;
     * Url format: https://opentdb.com/api_count.php?category=CATEGORY_ID_HERE;
     * @param categoryId id of a category to test, cannot be less than 0
     * @throws MalformedURLException if url content is empty  - generally happens when categoryId does not exist
      */
    public AvailableQuestionsRepository(int categoryId) throws IOException {
        URL url = new URL(URLs.CATEGORY_QUESTIONS_COUNT.getValue() + categoryId);

        availableQuestions = objectMapper.readValue(URLDataGetter.getDataFromURL(url),
                QuestionsAvailableInCategory.class);
    }
    @Override
    public QuestionsAvailableInCategory takeData() {
        return availableQuestions;
    }
}
