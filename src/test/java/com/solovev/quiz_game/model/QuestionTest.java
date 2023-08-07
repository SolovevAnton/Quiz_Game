package com.solovev.quiz_game.model;

import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void decodeHTMLMultiple() {
        Question codedQuestion = new Question();
        codedQuestion.setCorrectAnswer("&#039;. . . the front tires slide so the car won&#039;t face the inside&#039;");
        codedQuestion.setIncorrectAnswers(Set.of(
                "&#039;. . . the wheels lose traction, making the car slide sideways&#039;",
                "&#039;. . . the car oversteers through a curve, causing it to turn faster&#039;",
                "&#039;. . . you turn a lot&#039;"
        ));
        codedQuestion.decodeHTML();

        Question decodedQuestion = new Question();
        decodedQuestion.setCorrectAnswer("'. . . the front tires slide so the car won't face the inside'");
        decodedQuestion.setIncorrectAnswers(Set.of(
                "'. . . the wheels lose traction, making the car slide sideways'",
                "'. . . you turn a lot'",
                "'. . . the car oversteers through a curve, causing it to turn faster'"
        ));

        assertEquals(decodedQuestion,codedQuestion);
    }
    @Test
    public void decodeHTMLBoolean() {
        Question codedQuestion = new Question();
        codedQuestion.setQuestion("An episode of &quot;The Simpsons&quot; is dedicated to Moe Szyslak&#039;s bar rag.");
        codedQuestion.setCorrectAnswer("true");
        codedQuestion.setIncorrectAnswers(Set.of(
                "false"
        ));
        codedQuestion.decodeHTML();

        Question decodedQuestion = new Question();
        decodedQuestion.setQuestion("An episode of \"The Simpsons\" is dedicated to Moe Szyslak's bar rag.");
        decodedQuestion.setCorrectAnswer("true");
        decodedQuestion.setIncorrectAnswers(Set.of(
                "false"
        ));

        assertEquals(decodedQuestion,codedQuestion);
    }
}
