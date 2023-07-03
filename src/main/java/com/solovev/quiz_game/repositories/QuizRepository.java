package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.URLDataGetter;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * repository to get single quiz from file
 */
public class QuizRepository implements Repository<Quiz> {
    private Quiz quiz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * CSV or Json file to get quiz data
     *
     * @param file to take the quiz from
     */
    public QuizRepository(File file) throws IOException {
        this.quiz = objectMapper.readValue(file, Quiz.class);
    }

    /**
     * Creates quiz based on the url
     * @param url to get quiz from
     * @throws IOException if exception occurs
     */
    public QuizRepository(URL url) throws IOException {
        byte[] quizResponse = URLDataGetter.getDataFromURL(url);
        this.quiz = objectMapper.readValue(quizResponse,Quiz.class);
    }

    @Override
    public Quiz takeData() {
        return quiz;
    }


    @Override
    public String toString() {
        return "FileRepository{" +
                "quiz=" + quiz +
                '}';
    }
}
