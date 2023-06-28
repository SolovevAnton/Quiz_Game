package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestFileRepo {
    private final Path fileWithResponse1 =  Path.of("src","test","resources","response1.json");
    private final Path fileFullQuiz =  Path.of("src","test","resources","normalQuizz.json");

    @Test
    public void testQuizResponse1() throws IOException {
        Assertions.assertAll(() -> new FileRepository(fileWithResponse1.toFile()));
        Quiz empty = new FileRepository(fileWithResponse1.toFile()).takeData();
        assertEquals(new Quiz(Quiz.ResponseCode.NO_RESULTS,new HashSet<>()),empty);
    }

    @Test
    public void testQuestionDeserializationQuestion() throws JsonProcessingException {
        String toDeserialize ="    {\n" +
                "      \"category\": \"Entertainment: Video Games\",\n" +
                "      \"type\": \"multiple\",\n" +
                "      \"difficulty\": \"medium\",\n" +
                "      \"question\": \"When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?\",\n" +
                "      \"correct_answer\": \"Halo 3: Recon\",\n" +
                "      \"incorrect_answers\": [\n" +
                "        \"Halo 3: Helljumpers\",\n" +
                "        \"Halo 3: Phantom\",\n" +
                "        \"Halo 3: Guerilla\"\n" +
                "      ]\n" +
                "    }";
        ObjectMapper objectMapper = new ObjectMapper();
        Question toTest = new Question(new Category("Entertainment: Video Games"),
                Type.MULTIPLE,
                Difficulty.MEDIUM,
                "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                "Halo 3: Recon",
                Set.of("Halo 3: Helljumpers", "Halo 3: Phantom","Halo 3: Guerilla"));

        assertEquals(toTest,objectMapper.readValue(toDeserialize, Question.class));
    }
    @Test
    public void testQuizDeserialization() throws IOException {
       Assertions.assertAll(() ->new FileRepository(fileFullQuiz.toFile()));
    }
}
