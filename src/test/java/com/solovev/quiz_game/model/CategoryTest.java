package com.solovev.quiz_game.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void defineId() {
        int idToFind = 0;
        Set<Category> toSearch = Set.of(
                new Category(100,"Test"),
                new Category(idToFind,"other Category")
        );
        Category presentedCategory = new Category("other Category");
        Category notPresentedCategory = new Category("not presented");

        Assert.assertTrue(presentedCategory.defineId(toSearch));
        Assert.assertEquals(idToFind,presentedCategory.getId());

        Assert.assertFalse(notPresentedCategory.defineId(toSearch));
        Assert.assertEquals(-1,notPresentedCategory.getId());
    }
}