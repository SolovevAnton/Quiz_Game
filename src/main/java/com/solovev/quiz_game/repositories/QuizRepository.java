package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.URLDataGetter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

/**
 * repository to get single quiz from file
 */
public class QuizRepository implements Repository<Quiz> {
    private final Quiz quiz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * CSV or Json file to get quiz data
     *
     * @param file        to take the quiz from
     * @param isEncrypted shows if the source is encrypted or not, if true uses decryption
     */
    public QuizRepository(File file, boolean isEncrypted) throws IOException {
        this.quiz = objectMapper.readValue(file, Quiz.class);

        if (isEncrypted) {
            encryptOrDecryptQuiz();
        }
       // quiz.decodeHTML();
    }

    /**
     * Creates quiz based on the url
     *
     * @param url to get quiz from
     * @throws IOException if exception occurs
     */
    public QuizRepository(URL url) throws IOException {
        byte[] quizResponse = URLDataGetter.getDataFromURL(url);
        this.quiz = objectMapper.readValue(quizResponse, Quiz.class);

        //decodes HTML
        quiz.decodeHTML();
    }

    /**
     * Saves this repo data to the file
     *
     * @param path      to save data to
     * @param toEncrypt if true encrypts this quiz false otherwise
     */
    public void save(Path path, boolean toEncrypt) throws IOException {
        if (toEncrypt) {
            encryptOrDecryptQuiz();
            objectMapper.writeValue(path.toFile(), quiz);
            encryptOrDecryptQuiz(); // this is done to make original repo untouched
        } else {
            objectMapper.writeValue(path.toFile(), quiz);
        }
    }

    /**
     * Encrypts or decrypts (if used on encrypted quiz) quiz
     */
    private void encryptOrDecryptQuiz() {
        quiz.encryptOrDecrypt();
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
