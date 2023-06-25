package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.solovev.quiz_game.model.Quiz;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * repository to get single quiz from file
 */
public class FileRepository implements Repository<Quiz>{
    private Quiz quiz = new Quiz();
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * CSV or Json file to get quiz data
     * @param file
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
                ", objectMapper=" + objectMapper +
                '}';
    }
}
