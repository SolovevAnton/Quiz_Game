package com.solovev.quiz_game.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.enums.Difficulty;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionsAvailableInCategoryTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String jsonResponse = "{\n" +
            "  \"category_id\": 9,\n" +
            "  \"category_question_count\": {\n" +
            "    \"total_question_count\": 305,\n" +
            "    \"total_easy_question_count\": 122,\n" +
            "    \"total_medium_question_count\": 123,\n" +
            "    \"total_hard_question_count\": 60\n" +
            "  }\n" +
            "}";
    ;
    private QuestionsAvailableInCategory question = new QuestionsAvailableInCategory();

    { //creating instance
        question.setCategoryOnlyId(new Category(9));
        QuestionsAvailableInCategory.CategoryTotalQuestionsCount questions = question.new CategoryTotalQuestionsCount();
        questions.setTotalEasyQuestionCount(122);
        questions.setTotalMediumQuestionCount(123);
        questions.setTotalHardQuestionCount(60);

        question.setTotalCount(questions);
    }

    @Test
    public void deserializationTest() throws JsonProcessingException {
        assertEquals(question, objectMapper.readValue(jsonResponse, QuestionsAvailableInCategory.class));
    }

    @Test
    public void countQuestionsByDifficultyTest() {
        Map<Difficulty, Integer> resultMap = Map.of(
                Difficulty.EASY, question.getTotalCount().getTotalEasyQuestionCount(),
                Difficulty.MEDIUM,question.getTotalCount().getTotalMediumQuestionCount(),
                Difficulty.HARD,question.getTotalCount().getTotalHardQuestionCount()
        );
        assertEquals(resultMap,question.countQuestionsByDifficulty());
    }

}