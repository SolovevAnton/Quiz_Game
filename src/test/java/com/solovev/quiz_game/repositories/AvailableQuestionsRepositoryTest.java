package com.solovev.quiz_game.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

class AvailableQuestionsRepositoryTest {
    private int categoryIdToTest = 9;
    private int invalidCategoryIdToTest = -1;

    @Test
    public void testForRealCategory() throws IOException {
        Assertions.assertNotNull(new AvailableQuestionsRepository(categoryIdToTest).takeData());
    }

    @Test
    public void testForWrongCategory() {
        Assertions.assertThrows(MalformedURLException.class,
                () -> new AvailableQuestionsRepository(invalidCategoryIdToTest).takeData());
    }
}