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

        Question sameCodedQuestion = new Question();
        sameCodedQuestion.setQuestion("An episode of &quot;The Simpsons&quot; is dedicated to Moe Szyslak&#039;s bar rag.");
        sameCodedQuestion.setCorrectAnswer("true");
        sameCodedQuestion.setIncorrectAnswers(Set.of(
                "false"
        ));
        assertEquals(codedQuestion,sameCodedQuestion);

        sameCodedQuestion.decodeHTML();
        codedQuestion.decodeHTML();

        Question decodedQuestion = new Question();
        decodedQuestion.setQuestion("An episode of \"The Simpsons\" is dedicated to Moe Szyslak's bar rag.");
        decodedQuestion.setCorrectAnswer("true");
        decodedQuestion.setIncorrectAnswers(Set.of(
                "false"
        ));

        assertEquals(decodedQuestion,codedQuestion);
        assertEquals(decodedQuestion,sameCodedQuestion);
    }

    @Test
    public void encryptAndDecryptMultiple() {
        Question encrypt = new Question(new Category("Entertainment: Video Games"),
                QuestionType.MULTIPLE,
                Difficulty.MEDIUM,
                "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                "Halo 3: Recon",
                Set.of(
                        "Halo 3: Helljumpers",
                        "Halo 3: Phantom",
                        "Halo 3: Guerilla"
                )
        );
        encrypt.encryptOrDecrypt();
        assertNotEquals(toEncryptMultiple,encrypt);
        assertNotEquals(toEncryptMultiple.getCorrectAnswer(),encrypt.getCorrectAnswer());
        assertNotEquals(toEncryptMultiple.getIncorrectAnswers(),encrypt.getIncorrectAnswers());

        encrypt.encryptOrDecrypt();
        assertEquals(toEncryptMultiple,encrypt);
    }
    @Test
    public void encryptBoolean() {
        Question encrypt = new Question(new Category("Entertainment: Music"),
                QuestionType.BOOLEAN,
                Difficulty.EASY,
                "Lead Singer Rivers Cuomo of American rock band Weezer attended Harvard.",
                "True",
                Set.of(
                        "false"
                )
        );
        encrypt.encryptOrDecrypt();
        assertNotEquals(toEncryptBoolean,encrypt);
        assertNotEquals(toEncryptBoolean.getCorrectAnswer(),encrypt.getCorrectAnswer());
        assertNotEquals(toEncryptBoolean.getIncorrectAnswers(),encrypt.getIncorrectAnswers());

        encrypt.encryptOrDecrypt();
        assertEquals(toEncryptBoolean,encrypt);
    }

    private final Question toEncryptMultiple = new Question(new Category("Entertainment: Video Games"),
            QuestionType.MULTIPLE,
            Difficulty.MEDIUM,
            "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
            "Halo 3: Recon",
            Set.of(
                    "Halo 3: Helljumpers",
                    "Halo 3: Phantom",
                    "Halo 3: Guerilla"
            )
    );
    private final Question toEncryptBoolean = new Question(new Category("Entertainment: Music"),
            QuestionType.BOOLEAN,
            Difficulty.EASY,
            "Lead Singer Rivers Cuomo of American rock band Weezer attended Harvard.",
            "True",
            Set.of(
                    "false"
            )
    );
}
