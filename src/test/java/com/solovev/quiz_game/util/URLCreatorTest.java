package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Request;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class URLCreatorTest {


    @Test
    public void testGetURL() throws MalformedURLException {
        String numberOfQuestions = "10";
        Category category = new Category(9);
        Difficulty difficulty = Difficulty.EASY;
        QuestionType type = QuestionType.MULTIPLE;


        assertEquals(new URL("https://opentdb.com/api.php?amount=10"),
                new URLCreator(new Request(numberOfQuestions)).getURL());
        assertEquals(new URL("https://opentdb.com/api.php?amount=50"),
                new URLCreator(new Request("50")).getURL());

        assertEquals(new URL("https://opentdb.com/api.php?amount=10&category=9"),
                new URLCreator(new Request(numberOfQuestions,category)).getURL());
        assertEquals(new URL("https://opentdb.com/api.php?amount=10&category=9&difficulty=easy"),
                new URLCreator(new Request(numberOfQuestions,category,difficulty)).getURL());
        assertEquals(new URL("https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=multiple"),
                new URLCreator(new Request(numberOfQuestions,category,difficulty,type)).getURL());
    }
}