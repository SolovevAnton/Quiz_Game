package com.solovev.quiz_game.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CategoryDeserializationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void categoryFullDeserializationTest() throws JsonProcessingException {
        String categoryString = "{\"id\": 1, \"name\": \"Example Category\"}";
        Category value = new Category(1,"Example Category");
            Assert.assertEquals(value,mapper.readValue(categoryString, Category.class));
    }
    @Test
    public void categoryNameOnlyDeserializationTest() throws JsonProcessingException {
        String categoryString = "{\"name\": \"no id Category\"}";
        Category value = new Category(-1,"no id Category");
        Assert.assertEquals(value,mapper.readValue(categoryString, Category.class));
    }
}
