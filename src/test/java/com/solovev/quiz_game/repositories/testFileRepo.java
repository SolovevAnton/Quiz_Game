package com.solovev.quiz_game.repositories;

import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class testFileRepo {
    private Path fileWithResponse1 =  Path.of("src","test","resources","response1.json");

    @Test
    public void testQuizResponse1() throws IOException {
        Assertions.assertAll(() -> new FileRepository(fileWithResponse1.toFile()));
        Quiz empty = new FileRepository(fileWithResponse1.toFile()).takeData();
        assertEquals(new Quiz(Quiz.ResponseCode.NO_RESULTS,new HashSet<>()),empty);
    }

    @Test
    public void testQuestionDeserialization(){
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


    }
}
