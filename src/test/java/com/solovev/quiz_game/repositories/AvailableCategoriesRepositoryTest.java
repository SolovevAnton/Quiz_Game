package com.solovev.quiz_game.repositories;

import com.solovev.quiz_game.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

class AvailableCategoriesRepositoryTest {

    @Test
    public void takeDataTest() throws IOException {
        Assertions.assertAll(AvailableCategoriesRepository::new);
            Repository<Collection<Category>> categories = new AvailableCategoriesRepository();
            Assertions.assertFalse(categories.takeData().isEmpty());
    }
}