package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Quiz;

import java.io.File;
import java.io.IOException;

/**
 * repository to get single quiz from file
 */
public class FileRepository implements Repository<Quiz> {
    private Quiz quiz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * CSV or Json file to get quiz data
     *
     * @param file to take the quiz from
     */
    public FileRepository(File file) throws IOException {
        this.quiz = objectMapper.readValue(file, Quiz.class);
    }

    @Override
    public Quiz takeData() {
        return quiz;
    }

    @Override
    public void save(File pathToFile) {
        //todo implement method
    }

    @Override
    public String toString() {
        return "FileRepository{" +
                "quiz=" + quiz +
                '}';
    }
}
