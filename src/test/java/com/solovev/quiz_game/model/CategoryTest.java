package com.solovev.quiz_game.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void defineIdTest() {
        int idToFind = 0;
        Set<Category> toSearch = Set.of(
                new Category(100,"Test"),
                new Category(idToFind,"other Category")
        );
        Category presentedCategory = new Category("other Category");
        Category notPresentedCategory = new Category("not presented");

        assertTrue(presentedCategory.defineId(toSearch));
        assertEquals(idToFind,presentedCategory.getId());

        assertFalse(notPresentedCategory.defineId(toSearch));
        assertEquals(-1,notPresentedCategory.getId());
    }
}