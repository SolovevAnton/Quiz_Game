package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestQuizRepo {


    @Test
    public void testQuizResponse1() throws IOException {
        assertAll(() -> new QuizRepository(fileWithResponse1.toFile(),false));
        Quiz emptyFromFile = new QuizRepository(fileWithResponse1.toFile(),false).takeData();
        assertEquals(new Quiz(Quiz.ResponseCode.NO_RESULTS, new HashSet<>()), emptyFromFile);

        // quiz with wrong parameters URL
        URL urlWithExceedQuestions = new URL("https://opentdb.com/api.php?amount=2000&category=24&difficulty=easy&type=boolean");
        assertAll(() -> new QuizRepository(urlWithExceedQuestions));
        Quiz emptyFromUrl = new QuizRepository(urlWithExceedQuestions).takeData();
        assertEquals(new Quiz(Quiz.ResponseCode.NO_RESULTS, new HashSet<>()), emptyFromUrl);

    }

    @Test
    public void testURL() throws IOException {
        int numberOfQuestions = 10;
        URL urlDefault = new URL("https://opentdb.com/api.php?amount=" + numberOfQuestions);
        assertAll(() -> new QuizRepository(urlDefault));
        Quiz defaultQuiz = new QuizRepository(urlDefault).takeData();

        assertEquals(Quiz.ResponseCode.SUCCESS, defaultQuiz.getResponseCode());
        assertEquals(numberOfQuestions, defaultQuiz.size());
    }

    @Test
    public void testQuestionDeserializationQuestion() throws JsonProcessingException {
        String toDeserialize = """
                    {
                      "category": "Entertainment: Video Games",
                      "type": "multiple",
                      "difficulty": "medium",
                      "question": "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                      "correct_answer": "Halo 3: Recon",
                      "incorrect_answers": [
                        "Halo 3: Helljumpers",
                        "Halo 3: Phantom",
                        "Halo 3: Guerilla"
                      ]
                    }\
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        Question toTest = new Question(new Category("Entertainment: Video Games"),
                QuestionType.MULTIPLE,
                Difficulty.MEDIUM,
                "When Halo 3: ODST was unveiled in 2008, it had a different title. What was the game formally called?",
                "Halo 3: Recon",
                Set.of("Halo 3: Helljumpers", "Halo 3: Phantom", "Halo 3: Guerilla"));

        assertEquals(toTest, objectMapper.readValue(toDeserialize, Question.class));
    }

    @Test
    public void encryptionTest() throws IOException {
        QuizRepository repoInitial = new QuizRepository(fileFullQuiz.toFile(),false);
        QuizRepository repo = new QuizRepository(fileFullQuiz.toFile(),false);

        repo.save(fileFullQuizEncrypted.toFile(),true);

        assertEquals(repoInitial.takeData(),repo.takeData());

        repo = new QuizRepository(fileFullQuizEncrypted.toFile(),false);
        assertNotEquals(repoInitial.takeData(),repo.takeData());


        repo = new QuizRepository(fileFullQuizEncrypted.toFile(),true);
        assertEquals(repoInitial.takeData(),repo.takeData());
    }
    @Test
    public void testQuizDeserialization() {
        assertAll(() -> new QuizRepository(fileFullQuiz.toFile(),false));
    }
    @Test
    public void readFromFileTest() throws IOException {
        QuizRepository repoInitial = new QuizRepository(fileFullQuiz.toFile(),false);
        QuizRepository repo = new QuizRepository(fileFullQuiz.toFile(),false);

        assertEquals(repoInitial.takeData(),repo.takeData());
    }
    @Test
    public void readFromHTMLFile() throws IOException {
        QuizRepository repoInitial = new QuizRepository(fileHTML.toFile(),false);
        QuizRepository repo = new QuizRepository(fileHTML.toFile(),false);

/*        Set<Question> initial = new HashSet<>(repoInitial.takeData().getQuestions());
        Set<Question> other = new HashSet<>(repo.takeData().getQuestions());
        System.out.println(initial.equals(other));
*//*        for(Question q : initial){
            System.out.println( "in repo: " + repo.takeData().getQuestions().contains(q) + " Question: " + q);
        }*/
        assertEquals(repoInitial.takeData(),repo.takeData());
    }
    @Test
    public void saveAndReadTest() throws IOException {
        QuizRepository repoInitial = new QuizRepository(fileFullQuiz.toFile(),false);
        repoInitial.save(fileFullQuizSaved.toFile(),false);

        QuizRepository repo = new QuizRepository(fileFullQuizSaved.toFile(),false);

        assertEquals(repoInitial.takeData(),repo.takeData());
    }
    @Test
    public void testHTMDecoding() throws IOException {
        QuizRepository repo = new QuizRepository(fileHTML.toFile(),false);
        Quiz quiz = new Quiz(Quiz.ResponseCode.SUCCESS,
                Set.of(
                        new Question(new Category("Entertainment: Television"),
                               QuestionType.BOOLEAN,
                               Difficulty.MEDIUM,
                               "An episode of &quot;The Simpsons&quot; is dedicated to Moe Szyslak&#039;s bar rag.",
                                "True",
                                Set.of("False")),
                        new Question(new Category("Entertainment: Japanese Anime & Manga"),
                                QuestionType.MULTIPLE,
                                Difficulty.HARD,
                                "In the anime Initial D, how does Takumi Fujiwara describe a drift?",
                                "'. . . the front tires slide so the car won't face the inside'",
                                Set.of(
                                        "'. . . the wheels lose traction, making the car slide sideways'",
                                        "'. . . you turn a lot'",
                                        "'. . . the car oversteers through a curve, causing it to turn faster'"
                                )                                )
                ));
        assertEquals(quiz,repo.takeData());

    }
    private final Path fileWithResponse1 = Path.of("src", "test", "resources", "response1.json");
    private final Path fileFullQuiz = Path.of("src", "test", "resources", "normalQuizz.json");
    private final Path fileFullQuizEncrypted = Path.of("src", "test", "resources", "encryptedNormalQuiz.json");
    private final Path fileFullQuizSaved = Path.of("src", "test", "resources", "savedNormalQuiz.json");
    private final Path fileHTML = Path.of("src","test/resources/htmlQuestions.json");
}
