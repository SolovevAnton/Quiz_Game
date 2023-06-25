package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

class AvailableCategoriesRepositoryTest {
    @Test
    public void categoryDeserializationTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String categoryString = "{\"id\": 1, \"name\": \"Example Category\"}";
        Category value = new Category(1,"Example Category");
        Assert.assertEquals(value,mapper.readValue(categoryString, Category.class));
    }

    @Test
    public void takeDataTest() {
        Assertions.assertAll(AvailableCategoriesRepository::new);
        try {
            Repository<Collection<Category>> categories = new AvailableCategoriesRepository();
            Assertions.assertFalse(categories.takeData().isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}