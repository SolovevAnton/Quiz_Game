package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
    private final ObjectMapper csvMapper = new CsvMapper();

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
        ObjectMapper mapper = isCVSFile(file) ? csvMapper : objectMapper;
        this.quiz = mapper.
                readValue(file, Quiz.class);

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
        ObjectWriter mapper = isCVSFile(file)
                ? csvMapper.writerFor(Quiz.class).with(new CsvMapper().schemaFor(Quiz.class))
                : objectMapper.writer();
        if (toEncrypt) {
            encryptOrDecryptQuiz();
            mapper.writeValue(file, quiz);
            encryptOrDecryptQuiz(); // this is done to make original repo untouched
        } else {
            mapper.writeValue(file, quiz);
        }
    }

    /**
     * Encrypts or decrypts (if used on encrypted quiz) quiz
     */
    private void encryptOrDecryptQuiz() {
        quiz.encryptOrDecrypt();
    }

    /**
     * Check if file is CSV
     *
     * @return true if CVS false otherwise
     */
    private boolean isCVSFile(File file) {
        return file.getName().matches(".*\\.csv");
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
