package com.solovev.quiz_game.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDeserializationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void categoryFullDeserializationTest() throws JsonProcessingException {
        String categoryString = "{\"id\": 1, \"name\": \"Example Category\"}";
        Category value = new Category(1,"Example Category");
            assertEquals(value,mapper.readValue(categoryString, Category.class));
    }
    @Test
    public void categoryNameOnlyDeserializationTest() throws JsonProcessingException {
        String categoryString = "{\"name\": \"no id Category\"}";
        Category value = new Category(-1,"no id Category");
        assertEquals(value,mapper.readValue(categoryString, Category.class));
    }
    @Test
    public void categoryIdOnlyDeserializationTest() throws JsonProcessingException {
        String categoryString = "{\"id\": 10}";
        Category value = new Category(10,null);
        assertEquals(value,mapper.readValue(categoryString, Category.class));
    }
}
