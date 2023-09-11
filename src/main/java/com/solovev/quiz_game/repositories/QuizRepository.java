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
    private final Quiz quiz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizRepository(Quiz quiz) {
        this.quiz = quiz;
    }

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
        quiz.decodeHTML();
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
     * @param file      to save data to
     * @param toEncrypt if true encrypts this quiz false otherwise
     */
    public void save(File file, boolean toEncrypt) throws IOException {
        if (toEncrypt) {
            encryptOrDecryptQuiz();
            objectMapper.writeValue(file, quiz);
            encryptOrDecryptQuiz(); // this is done to make original repo untouched
        } else {
            objectMapper.writeValue(file, quiz);
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
