package com.solovev.quiz_game.model;

import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    @Test
    public void encryption(){
        Quiz toEncrypt = new Quiz(Quiz.ResponseCode.SUCCESS,
                Set.of(
                        new Question(new Category("Entertainment: Video Games"),
                                QuestionType.MULTIPLE,
                                Difficulty.MEDIUM,
                                "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                                "Halo 3: Recon",
                                Set.of("Halo 3: Helljumpers", "Halo 3: Phantom", "Halo 3: Guerilla")),
                        new Question(new Category("Entertainment: Video Games"),
                                QuestionType.MULTIPLE,
                                Difficulty.HARD,
                                "In the original DOOM (1993) which of the following is NOT a cheat code?",
                                "IDCLIP",
                                Set.of("IDFA", "IDDQD", "IDSPISPOPD"))));
        toEncrypt.encryptOrDecrypt();
        assertNotEquals(toTest,toEncrypt);

        toEncrypt.encryptOrDecrypt();
        assertEquals(toTest,toEncrypt);

    }

    @Test
    public void testEquals() {

        Quiz other = new Quiz(Quiz.ResponseCode.SUCCESS,
                Set.of(
                        new Question(new Category("Entertainment: Video Games"),
                                QuestionType.MULTIPLE,
                                Difficulty.MEDIUM,
                                "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                                "Halo 3: Recon",
                                Set.of("Halo 3: Helljumpers", "Halo 3: Phantom", "Halo 3: Guerilla")),
                        new Question(new Category("Entertainment: Video Games"),
                                QuestionType.MULTIPLE,
                                Difficulty.HARD,
                                "In the original DOOM (1993) which of the following is NOT a cheat code?",
                                "IDCLIP",
                                Set.of("IDFA", "IDDQD", "IDSPISPOPD"))));
        assertEquals(toTest,other);

    }
    private Quiz toTest = new Quiz(Quiz.ResponseCode.SUCCESS,
            Set.of(
                    new Question(new Category("Entertainment: Video Games"),
                            QuestionType.MULTIPLE,
                            Difficulty.MEDIUM,
                            "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                            "Halo 3: Recon",
                            Set.of("Halo 3: Helljumpers", "Halo 3: Phantom", "Halo 3: Guerilla")),
                    new Question(new Category("Entertainment: Video Games"),
                            QuestionType.MULTIPLE,
                            Difficulty.HARD,
                            "In the original DOOM (1993) which of the following is NOT a cheat code?",
                            "IDCLIP",
                            Set.of("IDFA", "IDDQD", "IDSPISPOPD"))));

}